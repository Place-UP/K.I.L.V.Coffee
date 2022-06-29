package com.api.placeup.config;

import com.api.placeup.security.jwt.JwtAuthFilter;
import com.api.placeup.security.jwt.JwtService;
import com.api.placeup.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/clients")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/api/clients/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.DELETE, "/api/clients/**")
                .hasAnyRole("CLIENT")

                .antMatchers(HttpMethod.PUT, "/api/clients/**")
                .hasAnyRole("CLIENT")

                .antMatchers("/api/attachment/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.POST, "/api/products/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.PUT, "/api/products/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.DELETE, "/api/products/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.GET, "/api/products/**")
                .hasAnyRole("SELLER", "CLIENT")

                .antMatchers(HttpMethod.POST, "/api/reservation/")
                .hasAnyRole("CLIENT")

                .antMatchers(HttpMethod.PATCH, "/api/reservation/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.GET, "/api/reservation/")
                .hasAnyRole("CLIENT", "SELLER")

                .antMatchers(HttpMethod.POST, "/api/sellers")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/api/sellers/**")
                .hasAnyRole("SELLER", "CLIENT")

                .antMatchers(HttpMethod.DELETE, "/api/sellers/**")
                .hasAnyRole("SELLER")

                .antMatchers(HttpMethod.PUT, "/api/sellers/**")
                .hasAnyRole("SELLER")

                .antMatchers("/api/users/**")
                .permitAll()

                .antMatchers("/sending-email")
                .permitAll()

                .antMatchers("swagger-ui.html#/")
                .permitAll()

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

}