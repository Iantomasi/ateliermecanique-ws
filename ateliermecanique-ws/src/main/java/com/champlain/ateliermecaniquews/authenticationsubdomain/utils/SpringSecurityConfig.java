//package com.champlain.ateliermecaniquews.authenticationsubdomain.utils;
//
//
//import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig {
//    @Autowired
//    private UserService userService;
//
//
//
//    @Autowired
//    private AuthenticationSuccessHandler successHandler;

//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }
//
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//
//

//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/registration/**").permitAll()
//                        .requestMatchers("/api/v1/customers").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                        .defaultSuccessUrl("/", true)
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .permitAll()
//                );
//
//        return http.build();
    //}


