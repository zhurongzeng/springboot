package com.chu.controller;

import com.alibaba.fastjson.JSONObject;
import com.chu.common.utils.ReturnMessageUtil;
import com.chu.common.utils.UpdateUtil;
import com.chu.dto.ReturnMsg;
import com.chu.po.Dictionary;
import com.chu.po.User;
import com.chu.service.DictionaryService;
import com.chu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        }
        return "/dictionary/" + index;
    }

    /**
     * 查询列表
     *
     * @param limit
     * @param offset
     * @param queryParams
     * @return
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(int limit, int offset, String queryParams) {
        JSONObject result = new JSONObject();
        JSONObject params = JSONObject.parseObject(queryParams);
        List<Dictionary> dictionaryList = dictionaryService.list(limit, offset, params);
        long count = dictionaryService.count(params);
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
    @RequestMapping(value = "/service/delete", method = RequestMethod.POST)
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
     * @param code
     * @return
     */
    @RequestMapping(value = "/service/validate", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validate(@RequestParam String code) {
        JSONObject result = new JSONObject();
        Dictionary dictionary = dictionaryService.getByDicCode(code);
        if (dictionary != null) {
            result.put("valid", false);
        } else {
            result.put("valid", true);
        }
        return result;
    }
}
