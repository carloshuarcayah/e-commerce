package pe.com.birdcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //http.authorizeHttpRequests((request) -> request.anyRequest().denyAll());
        //http.authorizeHttpRequests((request) -> request.anyRequest().permitAll());
        //Just
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((request) -> request.requestMatchers("/api/users/**","/api/orders/user/**").authenticated()
                .requestMatchers("/api/products/**","/api/categories/**","/error","/h2-console/**").permitAll()
                .anyRequest().authenticated());

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
