package newbie.place_review.security.config;

import newbie.place_review.security.filter.CsrfCookieFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Profile("!dev")
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http.sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                         .maximumSessions(1)
                                         .maxSessionsPreventsLogin(true)
        );

        http.authorizeHttpRequests(request -> request.requestMatchers(PathRequest.toH2Console())
                                                     .permitAll()
                                                     .anyRequest()
                                                     .permitAll()
        );

        http.csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                                          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );

        http.httpBasic(Customizer.withDefaults());

        http.formLogin(flc -> flc.loginPage("/sign-in")
                                 .defaultSuccessUrl("/")
                                 .failureUrl("/sign-in?error")
        );

        http.logout(logout -> logout.logoutUrl("/sign-out")
                                    .logoutSuccessUrl("/sign-in")
                                    .invalidateHttpSession(true)
                                    .clearAuthentication(true)
                                    .deleteCookies("JSESSIONID")
        );

        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
