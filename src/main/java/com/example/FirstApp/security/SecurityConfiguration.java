package com.example.FirstApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("himani")
                .password("himani")
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("nidhi")
                .password("nidhi")
                .authorities("ROLE_ADMIN","ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(user1,user2) ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
        return security
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/demo").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) //shows pop
                .formLogin(Customizer.withDefaults()) //show login page
                .build();
    }
}
