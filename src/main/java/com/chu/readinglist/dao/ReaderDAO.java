package com.chu.readinglist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chu.readinglist.po.Reader;

public interface ReaderDAO extends JpaRepository<Reader, String> {
    Reader findByUsername(String username);
}
