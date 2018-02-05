package com.chu.service.impl;

import com.chu.dao.DictionaryDAO;
import com.chu.entity.Dictionary;
import com.chu.service.DictionaryService;
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
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryDAO dictionaryDAO;

    @Override
    public Dictionary getDictionary(String id) {
        return dictionaryDAO.findOne(id);
    }

    @Override
    public List<Dictionary> list(int limit, int offset, Dictionary dictionary) {
        PageRequest pageRequest = new PageRequest(offset / limit, limit, Sort.Direction.DESC, "updateDate");
        Page page = dictionaryDAO.findAll(new Specification<Dictionary>() {
            @Override
            public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return getPredicate(root, cb, dictionary);
            }
        }, pageRequest);
        List<Dictionary> list = page.getContent();
        return page.getContent();
    }

    @Override
    public long count(Dictionary dictionary) {
        return dictionaryDAO.count(new Specification<Dictionary>() {
            @Override
            public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return getPredicate(root, cb, dictionary);
            }
        });
    }

    @Override
    public Dictionary save(Dictionary dictionary) {
        return dictionaryDAO.save(dictionary);
    }

    @Override
    public long delete(List<String> ids) {
        List<Dictionary> dictionaryList = new ArrayList<>();
        for (String id : ids) {
            Dictionary dictionary = new Dictionary();
            dictionary.setId(id);
            dictionaryList.add(dictionary);
        }
        dictionaryDAO.delete(dictionaryList);
        return ids.size();
    }

    @Override
    public Dictionary getByDicCode(String code) {
        return dictionaryDAO.findByCode(code);
    }

    public Predicate getPredicate(Root<Dictionary> root, CriteriaBuilder cb, Dictionary dictionary) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(dictionary.getName())) {
            list.add(cb.like(root.get("name").as(String.class), "%" + dictionary.getName() + "%"));
        }

        if (StringUtils.isNotEmpty(dictionary.getCode())) {
            list.add(cb.like(root.get("code").as(String.class), "%" + dictionary.getCode() + "%"));
        }

//        if (StringUtils.isNotEmpty(dictionary.getParentId())) {
//            list.add(cb.equal(root.get("parentId").as(String.class), dictionary.getParentId()));
//        }

        if (StringUtils.isNotEmpty(dictionary.getType())) {
            list.add(cb.equal(root.get("type").as(String.class), dictionary.getType()));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }
}
