package backend.overhere.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Collections;
import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 허용할 도메인
        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 모든 HTTP 메서드 허용
        configuration.setAllowCredentials(true); // 인증 정보 포함 허용
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        configuration.setMaxAge(3600L); // 캐시 지속 시간

        // 노출할 헤더 설정 (Authorization, Set-Cookie 등)
        // 프론트에서 접근할 수 있도록 허용할 응답 헤더
        configuration.setExposedHeaders(List.of("Set-Cookie", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
