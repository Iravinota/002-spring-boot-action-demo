package com.ws.exp.sba.controller;

import com.ws.exp.sba.config.AmazonProperties;
import com.ws.exp.sba.model.Book;
import com.ws.exp.sba.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReadingListController {
    private BookService bookService;
    private AmazonProperties amazonProperties;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setAmazonProperties(AmazonProperties amazonProperties) {
        this.amazonProperties = amazonProperties;
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readerBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = bookService.findByReader(reader);
        if (readingList != null && !readingList.isEmpty()) {
            model.addAttribute("books", readingList);
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
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
