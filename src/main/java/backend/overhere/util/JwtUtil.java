package backend.overhere.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;
    public static final int ACCESS_TOKEN_MINUTE = 60;
    public static final int REFRESH_TOKEN_HOURS = 72;


    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getId(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id", String.class);
    }

    public String getEmail(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("email", String.class);
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    //Access 토큰
    public String createJwt(String userId, String role, String email) {

        Claims claims = Jwts.claims();
        claims.put("id", userId);
        claims.put("email",email);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MINUTE * 60 * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //Refresh 토큰
    public String createRefreshJwt(String userId){
        Claims claims = Jwts.claims();
        claims.put("id", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_HOURS * 60 * 60 * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("Refresh")) {

                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
