package com.chu.po;

import com.chu.common.po.BasePO;
import com.chu.common.po.GeneratedUID;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据字典实体类
 *
 * @author zhurongzeng
 * @create 2018-01-31 16:27
 **/
@Data
@Entity
@Table(name = "t_dictionary")
public class Dictionary extends BasePO {
    @Id
    @GeneratedUID
    private String id;
    private String code;
    private String name;
    private String type;
    private String parent_code;
    private String status;
    private String remark;
}
