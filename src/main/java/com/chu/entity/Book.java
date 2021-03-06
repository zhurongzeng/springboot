package com.chu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_book")
public class Book {
    @Id
    @GeneratedValue
    private String id;
    private String reader;
    private String isbn;
    private String title;
    private String author;
    private String description;
}
