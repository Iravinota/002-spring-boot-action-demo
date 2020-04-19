package com.ws.exp.sba.config;

import com.ws.exp.sba.model.Reader;
import com.ws.exp.sba.service.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
     * 通过重载，配置user-detail服务。用来进行用户认证（用户名、密码登陆验证）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 基于内存的用户存储
        // 由于spring5把默认的 NoOpPasswordEncoder 改成了DelegatingPasswordEncoder，所以需要在设置密码时，加上{noop}
        // https://mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/?__cf_chl_captcha_tk__=6c36ce02c69c33b6780a1a1da43886033b9a7785-1587295118-0-AeT25ebaW9E_sJctsW6-UMfJCCfzEigjW1nTyBeo2xpbAF2n9YdXSmCbR1cQPRCjZ9Yzq7bnGrlxwcJ45Om3HhOFZJocwH9QbW4906jKpmBZCWNCZqBR8fG2pX2mZShbLpghSyV2bRlBT4NTKX3YB2BqQCZj-dQ9RQaJhONvl-9_t6T5NAB_RPLVHmTczF312xcuSdTfg5XmKbSw9WxKX3Q8ELoVQqJSJ_Zi00cjQH4iDPq0K1CUzqoQgjmZ1hYPKeEYVAxm7cpzgNMLuhEsmjJZKMcp6EhUcCkBVb9uj-stdGW7SmQhuEvMoMsL5uuyxtM0Hw2gBuU2cAWiUO-na7ZMCGxecTBDXK7Dq_YWpJ1S6Ljmp99Ef5ZnmSasC-IWdAQJoNpryOsZMBdPr9DTo2wv3JyT5RG_FFrqCYoGCtzLitgP5Mn2JdxfwJBi6idtEEI3C8v0b3juX173IP114AjDq4lOSdePodCssXrsWLz7
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER") // 为USER自动添加ROLE_前缀
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }
}
