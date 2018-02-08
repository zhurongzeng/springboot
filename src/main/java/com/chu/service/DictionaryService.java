package com.chu.service;

import com.chu.entity.Dictionary;

import java.util.List;

/**
 * 数据字典业务处理接口
 */
public interface DictionaryService {
    Dictionary getDictionary(String id);

    List<Dictionary> list(int limit, int offset, Dictionary dictionary);

    long count(Dictionary dictionary);

    Dictionary save(Dictionary dictionary);

    long delete(List<String> ids);

    List<Dictionary> list(Dictionary dictionary);
}
