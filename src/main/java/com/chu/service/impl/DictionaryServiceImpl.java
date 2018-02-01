package com.chu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chu.dao.DictionaryDAO;
import com.chu.po.Dictionary;
import com.chu.po.User;
import com.chu.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public List<Dictionary> list(int limit, int offset, JSONObject params) {
        PageRequest pageRequest = new PageRequest(offset / limit, limit, Sort.Direction.DESC, "code");
        List<Dictionary> dictionaryList = dictionaryDAO.findAll(pageRequest).getContent();
        return dictionaryList;
    }

    @Override
    public long count(JSONObject params) {
        return dictionaryDAO.count();
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
}
