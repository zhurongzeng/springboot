package com.chu.service;

import com.alibaba.fastjson.JSONObject;
import com.chu.po.Dictionary;

import java.util.List;

/**
 * 数据字典业务处理接口
 */
public interface DictionaryService {
    Dictionary getDictionary(String id);

    List<Dictionary> list(int limit, int offset, JSONObject params);

    long count(JSONObject params);

    Dictionary save(Dictionary dictionary);

    long delete(List<String> ids);

    Dictionary getByDicCode(String code);
}
