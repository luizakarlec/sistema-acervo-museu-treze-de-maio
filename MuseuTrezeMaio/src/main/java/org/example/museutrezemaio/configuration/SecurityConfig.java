package org.example.museutrezemaio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/login", "/pesquisa", "/jornal/detalhes/**", "/revista/detalhes/**", "/livro/detalhes/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/admin").hasAnyAuthority("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers("/usuario/**").hasAuthority("ADMIN")
                        // Qualquer outra rota não listada acima vai exigir autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        // Configura o Spring para aceitar a rota de logout tranquilamente
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService,
                                                  BCryptPasswordEncoder encoder) {
        // Passamos o userDetailsService diretamente dentro dos parênteses do construtor
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        // O passwordEncoder continua sendo setado pelo metodo normal
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
