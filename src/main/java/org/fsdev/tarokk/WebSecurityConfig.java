package org.fsdev.tarokk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // login
                .authorizeRequests()
                .antMatchers("/login/*")
                .permitAll()

                .and()

                // normal
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()

        ;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = Arrays.asList("hoba", "vinczeg", "attila", "kristof").stream()
                .map(username ->
                        User.withDefaultPasswordEncoder()
                                .username(username)
                                .password("")
                                .roles("USER")
                                .build())
                .collect(Collectors.toList());

        return new InMemoryUserDetailsManager(users);
    }
}