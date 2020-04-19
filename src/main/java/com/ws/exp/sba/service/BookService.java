package com.ws.exp.sba.service;

import com.ws.exp.sba.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 通过扩展JpaRepository接口，spring-boot自动实现18个方法
 *
 * @author Eric at 2020-04-17_16:20
 */
public interface BookService extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}
