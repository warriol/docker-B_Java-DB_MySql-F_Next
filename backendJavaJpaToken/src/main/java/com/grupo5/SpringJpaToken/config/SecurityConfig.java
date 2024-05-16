package com.grupo5.SpringJpaToken.config;


import com.grupo5.SpringJpaToken.filter.JwtAuthenticationFilter;
import com.grupo5.SpringJpaToken.service.IUsuarioService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final IUsuarioService iUsuarioService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomLogoutHandler logoutHandler;


    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomLogoutHandler logoutHandler, IUsuarioService iUsuarioService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
        this.iUsuarioService= iUsuarioService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues(); // Permitir todas las solicitudes CORS
        corsConfig.addAllowedMethod(HttpMethod.PUT);

        return http
                .cors(cors -> cors.configurationSource(request -> corsConfig)) // Configurar CORS

                .csrf(AbstractHttpConfigurer::disable)//Deshabilitar csrf ya que no se va a usar
                .authorizeHttpRequests(//Asignamos Autorizaciones sesiones
                        req->req.requestMatchers("/login/**","/register/**","/verifyToken/**",
                                        "/registerTokenGoogle/**","/vincularU/**","/doc/**",
                                        "/v3/**","/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                        "/curso/**", "/asignatura/**", "/metadata/**")
                                .permitAll()//Son endpint publicos basicamente
                                .requestMatchers("/admin_only/**").hasAuthority("ADMIN")
                                .requestMatchers("/usuario/**").hasAnyAuthority("ADMIN","USUARIO")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(iUsuarioService)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)//Filtro para autenticar con token
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
