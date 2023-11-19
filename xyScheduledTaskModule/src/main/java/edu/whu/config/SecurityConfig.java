package edu.whu.config;

import edu.whu.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Akihabara
 * @version 1.0
 * @description SecurityConfig: Spring Security相关配置, 添加对应的拦截器
 * @date 2023/9/16 18:51
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtRequestFilter filter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        try {
            return configuration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity.csrf().disable();
//            /**
//             * 注意: 不要配置.loginProcessingUrl()为自定义controller, 不如会被覆盖API接口
//             */
//            httpSecurity
//                    .formLogin().loginPage("/login.html").permitAll();
            httpSecurity
                    .authorizeRequests()
                    .antMatchers(
                            "/authenticate/**",
                            "favicon.ico",
                            "/doc.html",
                            "/webjars/**",
                            "/swagger-resources/**",
                            "/v2/api-docs/**",
                            "/captcha"
                    ).permitAll()
                    .anyRequest().permitAll()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


            httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
