package com.chu.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ReturnMsg {
    private String retCode;
    private String retMsg;
    private Object retData;
}
