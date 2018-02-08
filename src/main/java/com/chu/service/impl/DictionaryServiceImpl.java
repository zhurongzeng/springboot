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
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
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
        Page<Dictionary> page = dictionaryDAO.findAll((root, query, cb) -> getPredicate(root, cb, dictionary), pageRequest);
        return page.getContent();
    }

    @Override
    public long count(Dictionary dictionary) {
        return dictionaryDAO.count((root, query, cb) -> getPredicate(root, cb, dictionary));
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
    public List<Dictionary> list(Dictionary dictionary) {
        return dictionaryDAO.findAll(((root, query, cb) -> getPredicate(root, cb, dictionary)));
    }

    /**
     * 生成查询条件
     *
     * @param root
     * @param cb
     * @param dictionary
     * @return
     */
    private Predicate getPredicate(Root<Dictionary> root, CriteriaBuilder cb, Dictionary dictionary) {
        List<Predicate> list = new ArrayList<>();

        if (StringUtils.isNotBlank(dictionary.getName())) {
            list.add(cb.like(root.get("name").as(String.class), "%" + dictionary.getName() + "%"));
        }

        if (StringUtils.isNotBlank(dictionary.getCode())) {
            list.add(cb.like(root.get("code").as(String.class), "%" + dictionary.getCode() + "%"));
        }

        if (StringUtils.isNotBlank(dictionary.getType())) {
            list.add(cb.equal(root.get("type").as(String.class), dictionary.getType()));
        }

        list.add(cb.equal(root.get("status").as(String.class), "on"));

        if (dictionary.getParent() != null) {
            String parentId = dictionary.getParent().getId();
            if (StringUtils.isNotBlank(parentId)) {
                if ("null".equals(parentId)) {
                    list.add(cb.isNull(root.get("parent").as(Dictionary.class)));
                } else {
                    list.add(cb.equal(root.join("parent").get("id").as(String.class), dictionary.getParent().getId()));
                }
            }
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }
}
