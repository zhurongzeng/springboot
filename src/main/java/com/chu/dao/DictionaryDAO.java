package com.chu.dao;

import com.chu.po.Dictionary;
import com.chu.po.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DictionaryDAO extends PagingAndSortingRepository<Dictionary, String> {
    Dictionary findByCode(String code);
}
