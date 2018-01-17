package com.chu.readinglist.config;

import com.chu.readinglist.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chu.readinglist.dao.ReaderDAO;

/**
 * 登录安全认证配置类
 *
 * @author zhurongzeng
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderService readerService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/readinglist/**").hasRole("READER") // 按顺序生效
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")// 设置登录表单的路径
                .defaultSuccessUrl("/index").failureUrl("/login?error=true").permitAll()
                .and()
                .rememberMe()// 开启cookie保存用户数据
                .tokenValiditySeconds(60 * 60 * 24 * 7)// 设置cookie有效期
                .key("springboot")
                .and()
                .logout()// 默认注销行为为logout，可以通过下面的方式来修改
                //.logoutUrl("/custom-logout")
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面
                .permitAll();
        //关闭csrf 防止循环定向
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(readerService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/js/**", "/css/**", "/img/**", "/images/**", "/assets/**", "/demo*/**", "/**/favicon.ico");
    }
}
