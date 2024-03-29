package com.br.lavaja.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.br.lavaja.security.JWTAuthenticationFilter;
import com.br.lavaja.security.JWTAuthorizationFilter;
import com.br.lavaja.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    public static final String[] PUBLIC_MATCHERS = {
             "/api/v1/lavacar",
             "/api/v1/servico/ativos",
             "/api/v1/servico/**",
             "/api/v1/lavacar/todos",
             "/login",
             "/api/v1/lavacar/{id}",
             "/api/v1/donocarro",
             "/api/v1/contratarservico/token/**"            
    };

    public static final String[] PUBLIC_MATCHERS_POST = {
             "/api/v1/lavacar",
             "/api/v1/donocarro",
             "/login",
             "api/v1/donocarro/tokenfirebase",
             "api/v1/contratarservico/donocarro",
             "/notificacoes/enviar"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers("/api/lava-car/**").hasAuthority("ROLE_LAVACAR")
                .antMatchers("/api/dono-carro/**").hasAuthority("ROLE_DONOCARRO")
                .anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter((Filter) new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
