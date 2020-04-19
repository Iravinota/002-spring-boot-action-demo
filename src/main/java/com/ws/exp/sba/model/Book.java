package com.ws.exp.sba.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Book
 *
 * @author Eric at 2020-04-17_16:10
 */
@Data
@Entity     // spring-boot-starter-data-jpa
public class Book {
    @Id // ID
    @GeneratedValue(strategy = GenerationType.AUTO) // 自动增加
    private Integer id;
    private String reader;
    private String isbn;
    private String title;
    private String author;
    private String description;
}
