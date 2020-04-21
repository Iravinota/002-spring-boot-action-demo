package com.ws.exp.sba.config;

import com.ws.exp.sba.model.Reader;
import com.ws.exp.sba.service.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * SecurityConfig
 *
 * @author Eric at 2020-04-19_11:15
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderService readerService;

    /**
     * 对每个请求（URL）进行细粒度安全控制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()  // 启用默认的登录页
            .and()
            .csrf().disable()   // spring security默认启用CSRF保护，POST时必须验证token。 https://blog.csdn.net/t894690230/article/details/52404105
            .authorizeRequests()
                .antMatchers("/readingList/**").hasRole("USER")  // "ROLE_USER"
                .anyRequest().permitAll();
    }

    // MockMvcWebSecurityTests中的@WithUserDetails会用到
    @Bean
    public UserDetailsService userDetailsService() {
        return s -> {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            Reader reader = readerService.findById(s).orElse(null);
            log.info("reader: " + reader);
            if (reader == null) {
                throw new UsernameNotFoundException("username " + s + " not found");
            } else {
                // 设置username, password, ROLE_USER
                return new User(reader.getUsername(), reader.getPassword(), authorities);
            }
        };
    }

    /**
     * 通过重载，配置user-detail服务。用来进行用户认证（用户名、密码登陆验证）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义用户服务
        auth.userDetailsService(userDetailsService());
    }
}
