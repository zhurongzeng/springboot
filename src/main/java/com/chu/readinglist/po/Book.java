package com.chu.readinglist.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Book {
	@Id
	private String id;
	private String reader;
	private String isbn;
	private String title;
	private String author;
	private String description;
}
