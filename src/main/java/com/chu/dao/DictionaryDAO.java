package com.chu.dao;

import com.chu.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DictionaryDAO extends PagingAndSortingRepository<Dictionary, String>, JpaSpecificationExecutor<Dictionary> {
    Dictionary findByCode(String code);
}
