package ru.shestakov.Library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.shestakov.Library.security.JWTAuthEntryPoint;
import ru.shestakov.Library.security.JWTAuthFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JWTAuthEntryPoint jwtAuthEntryPoint;

    private static final String[] WHITE_LIST_URL = {  "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**", "/authenticate" };

    public SecurityConfig(JWTAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exception)-> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(HttpMethod.PATCH).authenticated()
                                .requestMatchers(HttpMethod.GET).authenticated()
                                .requestMatchers(HttpMethod.POST).authenticated()
                                .requestMatchers(HttpMethod.PATCH).authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).httpBasic(withDefaults());
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JWTAuthFilter jwtAuthFilter(){
        return new JWTAuthFilter();
    }



}
