package fr.eni.tp.filmotheque.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // Activer le débogage de sécurité
public class FilmothequeSecurityConfig {
    // Configuration de sécurité pour la filmothèque
    private static final String SELECT_USER = "SELECT email, password, 1 FROM membre WHERE email = ?";
    private static final String SELECT_ROLES = "SELECT email, r.role from MEMBRE m INNER JOIN  ROLES r ON r.IS_ADMIN = m.is_admin where email = ?";

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource)  {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(SELECT_USER);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(SELECT_ROLES);

        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/films").permitAll()
                    .requestMatchers(HttpMethod.GET, "/films/detail").permitAll()
                    .requestMatchers(HttpMethod.GET, "/films/creer").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/films/creer").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/avis").hasAnyRole("MEMBRE", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/avis/creer").hasAnyRole("MEMBRE", "ADMIN")
                    .requestMatchers("/contexte").hasRole("ADMIN")
                    .anyRequest().authenticated();
        });

        http.formLogin(form ->{
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/login/success", true);
        });

        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll()
        );

        return http.build();
    }
}
