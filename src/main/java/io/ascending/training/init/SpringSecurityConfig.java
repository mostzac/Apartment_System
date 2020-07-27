package io.ascending.training.init;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // configure authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Admin")
                .password("123")
                .roles("Admin")
                .and()
                .withUser("RunuuuAccount")
                .password("0")
                .roles("MongoUser");
    }

    // configure authorization


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**").hasAnyRole("Admin", "MongoUser")
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncode() {
        // config no encode to password, raw string password
        return NoOpPasswordEncoder.getInstance();
    }

}
