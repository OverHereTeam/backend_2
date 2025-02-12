package backend.overhere.configuration.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import backend.overhere.configuration.security.handler.LoginFailureHandler;
import backend.overhere.configuration.security.handler.LoginSuccessHandler;
import backend.overhere.configuration.security.handler.OauthLoginFailureHandler;
import backend.overhere.configuration.security.handler.OauthLoginSuccessHandler;
import backend.overhere.filter.JwtFilter;
import backend.overhere.filter.LoginFilter;
import backend.overhere.service.auth.CustomUserDetailService;
import backend.overhere.service.auth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final CustomUserDetailService loginService;
    private final Oauth2UserService oauth2UserService;
    private final ObjectMapper objectMapper;
    private final JwtFilter jwtFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CorsConfigurationSource corsConfigurationSource;

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final OauthLoginSuccessHandler oauthLoginSuccessHandler;
    private final OauthLoginFailureHandler oauthLoginFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors->cors.configurationSource(corsConfigurationSource));

        http
                .csrf((auth) -> auth.disable());

        //자체 로그인 URL 세팅 필요
        http
                .formLogin((auth) -> auth.disable());
        http
                .logout((auth)-> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login","/","/join","/refresh","/error","/refresh","/logout","/favicon.ico").permitAll()
                        .anyRequest().authenticated());

        http
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterAfter(jwtFilter, LoginFilter.class);

        http
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(info -> info
                                .userService(oauth2UserService))
                        .successHandler(oauthLoginSuccessHandler)
                        .failureHandler(oauthLoginFailureHandler)
                        .loginPage("/login")
                );

        http.exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint);


        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    public LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter(objectMapper, loginSuccessHandler, loginFailureHandler);
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }


    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(loginService);

        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
