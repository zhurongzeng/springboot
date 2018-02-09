package com.chu.controller;

import com.alibaba.fastjson.JSONObject;
import com.chu.common.utils.ReturnMessageUtil;
import com.chu.common.utils.UpdateUtil;
import com.chu.dto.ReturnMsg;
import com.chu.entity.Dictionary;
import com.chu.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典Controller类
 *
 * @author zhurongzeng
 */
@Slf4j
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 页面展示
     *
     * @param index
     * @return
     */
    @RequestMapping(value = "/view/{index}", method = RequestMethod.GET)
    public String view(@PathVariable("index") String index, String id, Model model) {
        if ("edit".equals(index)) {
                Dictionary dictionary = dictionaryService.getDictionary(id);
            model.addAttribute("dictionary", dictionary);
        } else if ("subAdd".equals(index)) {
            model.addAttribute("parentId", id);
        }
        return "/dictionary/" + index;
    }

    /**
     * 查询列表
     *
     * @param limit
     * @param offset
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(int limit, int offset, String paramJson) {
        JSONObject result = new JSONObject();
        Dictionary dictionary = JSONObject.parseObject(paramJson, Dictionary.class);
        List<Dictionary> dictionaryList = dictionaryService.list(limit, offset, dictionary);
        long count = dictionaryService.count(dictionary);
        result.put("total", count);
        result.put("rows", dictionaryList);
        return result;
    }

    /**
     * 新增
     *
     * @param dictionary
     * @return
     */
    @RequestMapping(value = "/service/add", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg add(Dictionary dictionary) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            retMsg = ReturnMessageUtil.returnMessage(dictionaryService.save(dictionary) != null, null);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("保存字典异常！\n", e);
        }
        return retMsg;
    }

    /**
     * 修改
     *
     * @param dictionary
     * @return
     */
    @RequestMapping(value = "/service/edit", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg edit(Dictionary dictionary) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            Dictionary targetDictionary = dictionaryService.getDictionary(dictionary.getId());
            UpdateUtil.copyNonNullProperties(dictionary, targetDictionary);
            retMsg = ReturnMessageUtil.returnMessage(dictionaryService.save(targetDictionary) != null, null);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("修改字典异常！\n", e);
        }
        return retMsg;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/service/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnMsg delete(@RequestBody List<String> ids) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            long count = dictionaryService.delete(ids);
            retMsg = ReturnMessageUtil.returnMessage(count > 0, count);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("删除字典异常！\n", e);
        }
        return retMsg;
    }

    /**
     * 校验
     *
     * @return
     */
    @RequestMapping(value = "/service/validate", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject validate(String id, String code, String type, String parentId) {
        JSONObject result = new JSONObject();
        Dictionary dictionary = new Dictionary();
        dictionary.setCode(code);
        dictionary.setType(type);
        if (StringUtils.isNotBlank(parentId)) {
            Dictionary parent = new Dictionary();
            parent.setId(parentId);
            dictionary.setParent(parent);
        }
        List<Dictionary> dicList = dictionaryService.list(dictionary);
        boolean valid = false;
        if (dicList != null && dicList.size() > 0) {
            // id不为空是修改，不是新增
            if (StringUtils.isNotBlank(id)) {
                for (Dictionary dic : dicList) {
                    valid = true;
                    // 如果存在不是自身的记录，不合法
                    if (!id.equals(dic.getId())) {
                        valid = false;
                        break;
                    }
                }
            }
        } else {
            valid = true;
        }
        result.put("valid", valid);
        return result;
    }
}
