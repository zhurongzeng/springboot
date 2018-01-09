package com.chu.readinglist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chu.readinglist.po.Book;

public interface ReadingListDAO extends JpaRepository<Book, Long> {
	List<Book> findByReader(String reader);
}
