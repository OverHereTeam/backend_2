package backend.overhere.controller;

import backend.overhere.common.ResponseStatus;
import backend.overhere.dto.ResponseDto;
import backend.overhere.dto.SignUpRequestDto;
import backend.overhere.dto.SignUpResponseDto;
import backend.overhere.domain.RefreshToken;
import backend.overhere.domain.User;
import backend.overhere.exception.DuplicateEmailException;
import backend.overhere.exception.LoginFormException;
import backend.overhere.service.api.JoinService;
import backend.overhere.service.auth.RefreshTokenService;
import backend.overhere.service.api.UserService;
import backend.overhere.util.JwtUtil;
import backend.overhere.util.Util;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name="회원관리 API", description = "회원가입, 로그아웃, Access Token 재발급 관련 API입니다. ")
@Slf4j
public class JoinController {
    private final JoinService joinService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final Util util;

    @Operation(summary = "회원가입 API",description = "회원가입 API입니다.")
    @PostMapping("/join")
    public ResponseEntity<SignUpResponseDto> join(@RequestBody @Validated SignUpRequestDto request){
        String email = request.getEmail();
        User user = userService.findByEmailAndProvider(email,"LOCAL");
        if(user!=null){
            throw new DuplicateEmailException("Email Duplicate");
        }
        joinService.join(request);
        SignUpResponseDto response = new SignUpResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "로그인 테스트 API",description = "로그인 테스트 API입니다. 로그인 성공 시 query를 리턴합니다.")
    @GetMapping("/query")
    public String query(){
        return "query";
    }

    //AccessToken은 프론트쪽에서 지워버리고 RefreshToken만 받아 DB에서 삭제
    @Operation(summary = "관광지 에러 재시도 초기화",description = "하드코딩으로 저장 실패한 요소를 다시 시도합니다. 재빌드 하세요.")
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        //Refresh Token 뽑기
        String refreshToken = jwtUtil.getRefreshToken(request);
        Long id = jwtUtil.getId(refreshToken);
        // 1단계: 해당 refresh 값이 DB에 있는지 확인
        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByRefresh(refreshToken);
        System.out.println("================="+!refreshTokenOpt.get().getExpired());
        if (refreshTokenOpt.isPresent() && refreshToken!=null && !refreshTokenOpt.get().getExpired()) {

            // 2단계: 값이 있다면 삭제
            refreshTokenService.updateExpiredTokens(id);
            //return ResponseEntity.ok("Refresh token Expired successfully");
            return ResponseDto.settingResponse(HttpStatus.OK,ResponseStatus.LOGOUT_SUCCESS);
        } else {
            // 값이 없으면 삭제할 것이 없다는 응답
            //return ResponseEntity.status(404).body("Refresh token not found");
            throw new LoginFormException("Invalid Refresh Token");
        }
    }

    @Operation(summary = "Access Token 재발급 API",description = "유효한 Refresh Token을 받고 Access Token을 재발급합니다.")
    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request,HttpServletResponse response){
        String refresh= jwtUtil.getRefreshToken(request);

        if(refresh==null){
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh Token is null");
            throw new JwtException("Refresh Null");
        }

        jwtUtil.isExpired(refresh);


        Long id = jwtUtil.getId(refresh);
        // DB에서 Refresh Token 조회
        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findMatchingRefreshToken(id, refresh);
        Optional<User> userOpt = userService.findById(id);

        if (refreshTokenOpt.isEmpty() || userOpt.isEmpty()) {
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token not found");
            throw new JwtException("Invalid Refresh Token");
        }


        RefreshToken refreshTokenEntity = refreshTokenOpt.get();
        String newRefreshToken = jwtUtil.createRefreshJwt(id); // 새 Refresh Token 생성


        User user = userOpt.get();
        String newAccessToken = jwtUtil.createJwt(id, user.getRole(),user.getEmail());

        refreshTokenEntity.setExpired(true); // 기존 refreshToken Expire 필드 값 true로 변경
        refreshTokenService.save(newRefreshToken,id); // 새로운 refreshToken DB에 저장

        response.setHeader("Authorization","Bearer "+ newAccessToken);
        response.addCookie(util.createCookie("Refresh",newRefreshToken));
        return ResponseDto.settingResponse(HttpStatus.CREATED, ResponseStatus.TOKEN_CREATED);

    }
}
