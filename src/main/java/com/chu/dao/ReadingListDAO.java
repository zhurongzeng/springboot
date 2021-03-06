package com.chu.dao;

import com.chu.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListDAO extends JpaRepository<Book, Long> {
	List<Book> findByReader(String reader);
}
