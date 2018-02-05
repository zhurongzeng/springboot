package com.chu.dao;

import com.chu.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuDAO extends PagingAndSortingRepository<Menu, String>, JpaSpecificationExecutor {
    Menu findByCode(String code);
}
