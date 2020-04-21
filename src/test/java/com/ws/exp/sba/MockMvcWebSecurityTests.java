package com.ws.exp.sba;

import com.ws.exp.sba.model.Reader;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * MockMvcWebSecurityTests
 *
 * @author Eric at 2020-04-21_10:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class MockMvcWebSecurityTests {
    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .apply(springSecurity())    // 配置安全测试
                .build();
    }

    // 测试未登陆时的页面跳转
    @Test
    public void homePage_unauthenticatedUser() throws Exception {
        mockMvc.perform(get("/readingList/asdf"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    // 加载安全上下文，其中包含一个UserDetails，使用了给定的用户名、密码、授权
    @Test
    @WithMockUser(username = "asdf", password = "password", roles = "USER")
    public void homePage_authenticatedUser() throws Exception {
        mockMvc.perform(get("/readingList/asdf"))
                .andExpect(status().isOk());
    }

    // 还不能用，会失败
    @Test
    @WithUserDetails(value = "test")
    public void homePage_authenticatedUserDetail() throws Exception {
        Reader expectedReader = new Reader();
        expectedReader.setUsername("test");
        expectedReader.setFullname("testtest");
        expectedReader.setPassword("{noop}pppp");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
                .andExpect(model().attribute("books", hasSize(0)));
    }
}
