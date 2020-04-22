package com.ws.exp.sba;

import com.ws.exp.sba.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * MockMvcWebTests
 *
 * @author Eric at 2020-04-20_15:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)    // 替代SpringApplicationConfiguration  https://blog.csdn.net/office5845/article/details/77749350
@WebAppConfiguration
public class MockMvcWebTests {
    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext)
                .build();
    }

    @Test
    public void homePage() throws Exception {
        mockMvc.perform(get("/readingList/asdf"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books",
                        is(empty())));
    }

    @Test
    public void postBook() throws Exception {
        // 执行POST请求
        mockMvc.perform(post("/readingList/asdf")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "BOOK TITLE")
                .param("author", "BOOK AUTHOR")
                .param("isbn", "12345678")
                .param("description", "DESCRIPTION")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList/asdf"));

        // 配置期望值
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setReader("asdf");
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("12345678");
        expectedBook.setDescription("DESCRIPTION");

        // 执行GET请求
        mockMvc.perform(get("/readingList/asdf"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))))
                .andExpect(model().attribute("amazonID", "habuma-20"))
        ;
    }
}
