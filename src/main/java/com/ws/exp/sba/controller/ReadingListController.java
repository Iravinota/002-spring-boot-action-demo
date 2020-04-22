package com.ws.exp.sba.controller;

import com.ws.exp.sba.model.Book;
import com.ws.exp.sba.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * ReadingListController
 *
 * @author Eric at 2020-04-17_16:29
 */
@Controller
@RequestMapping("/readingList")
@ConfigurationProperties(prefix = "amazon") // spring boot已经自动添加了@EnableConfigurationProperties注解
// 添加@ConfigurationProperties注解后IDEA会提示错误，没关系，不用管，IDEA版本问题。参考https://stackoverflow.com/questions/42839126/configurationproperties-spring-boot-configuration-annotation-processor-not-foun
public class ReadingListController {
    private String associateId;
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readerBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = bookService.findByReader(reader);
        if (readingList != null && !readingList.isEmpty()) {
            model.addAttribute("books", readingList);
            model.addAttribute("amazonID", associateId);
        } else {
            model.addAttribute("books", new ArrayList<>());
        }
        return "readingList";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        bookService.save(book);
        return "redirect:/readingList/{reader}";
    }
}
