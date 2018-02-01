package com.chu.common.utils;

import com.chu.dto.ReturnMsg;

/**
 * 返回结果工具类
 *
 * @author
 * @create 2018-01-31 18:17
 **/

public class ReturnMessageUtil {
    public static ReturnMsg returnMessage(boolean isSuccess, Object object) {
        ReturnMsg retMsg = new ReturnMsg();
        if (isSuccess) {
            retMsg.setRetCode("0000");
            retMsg.setRetMsg("操作成功");
            retMsg.setRetData(object);
        } else {
            retMsg.setRetCode("0001");
            retMsg.setRetMsg("操作失败");
        }
        return retMsg;
    }
}
