package AFAC.GC.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/member/**").permitAll()
                        .anyRequest().authenticated()  // 이외의 모든 요청에 대해 인증을 요구
                )
                .formLogin(login -> login
                        .loginPage("/member/LoginMainPage")        // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 리다이렉션 페이지
                        .permitAll()               // 모든 사용자가 로그인 페이지에 접근 가능하도록 허용
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        // 로그아웃 성공 시 리다이렉션할 URL
                /*.httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(authenticationEntryPoint()));  // HTTP 기본 인증 활성화
*/
        return http.build();
    }

    @Bean
    public BasicAuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("admin realm");
        return entryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화 방식으로 BCrypt 사용
    }
}
