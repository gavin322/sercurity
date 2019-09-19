package com.example.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                .loginPage("/login.html")           // 设置登录页面
                .loginProcessingUrl("/user/login")  // 自定义的登录接口
                .successHandler(new ForwardAuthenticationSuccessHandler("/user/login?status=true"))
                .failureUrl("/user/login?status=false")
                .permitAll()
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login.html").permitAll()     // 设置所有人都可以访问登录页面
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //可以设置内存指定的登录的账号密码,指定角色
        //不加.passwordEncoder(new MyPasswordEncoder())
        //就不是以明文的方式进行匹配，会报错
        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
        //.passwordEncoder(new MyPasswordEncoder())。
        //这样，页面提交时候，密码以明文的方式进行匹配。
        String password = passwordEncoder.encode("123456");
        logger.info("password: {}", password);

        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder()).withUser("cxh").password("cxh").roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
