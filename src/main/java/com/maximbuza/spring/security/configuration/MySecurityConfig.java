package com.maximbuza.spring.security.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter { // класс является ответственный за security configuration

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

   auth.jdbcAuthentication().dataSource(dataSource); //знает что нужно брать инфу о юзерах из базы данных


        //        User.UserBuilder userBuilder =User.withDefaultPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder.username("max").password("max").roles("EMPLOYEE"))
//                .withUser(userBuilder.username("polya").password("polya").roles("HR"))
//                .withUser(userBuilder.username("and").password("and").roles("HR","MANAGER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // обозначаем ролям их методы
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE","HR","MANAHER")
                .antMatchers("/hr_info").hasRole("HR")
                .antMatchers("manager_info").hasRole("MANAGER")
                .and().formLogin().permitAll(); // логин пароль спрашивать у всех
    }
}
