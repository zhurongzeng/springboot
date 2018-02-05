package com.chu.service.impl;

import com.chu.dao.MenuDAO;
import com.chu.entity.Menu;
import com.chu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDAO menuDAO;

    @Override
    public Menu getMenu(String id) {
        return menuDAO.findOne(id);
    }

    @Override
    public List<Menu> list(int limit, int offset, Menu menu) {
        PageRequest pageRequest = new PageRequest(offset / limit, limit, Sort.Direction.DESC, "updateDate");
        Page page = menuDAO.findAll(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return getPredicate(root, cb, menu);
            }
        }, pageRequest);
        List<Menu> list = page.getContent();
        return page.getContent();
    }

    @Override
    public long count(Menu menu) {
        return menuDAO.count(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return getPredicate(root, cb, menu);
            }
        });
    }

    @Override
    public Menu save(Menu menu) {
        return menuDAO.save(menu);
    }

    @Override
    public long delete(List<String> ids) {
        List<Menu> menuList = new ArrayList<>();
        for (String id : ids) {
            Menu menu = new Menu();
            menu.setId(id);
            menuList.add(menu);
        }
        menuDAO.delete(menuList);
        return ids.size();
    }

    public Predicate getPredicate(Root<Menu> root, CriteriaBuilder cb, Menu menu) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(menu.getName())) {
            list.add(cb.like(root.get("name").as(String.class), "%" + menu.getName() + "%"));
        }

//        if (StringUtils.isNotEmpty(menu.getParentId())) {
//            list.add(cb.equal(root.get("parentId").as(String.class), menu.getParentId()));
//        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }
}
