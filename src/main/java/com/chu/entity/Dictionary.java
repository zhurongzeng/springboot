package com.chu.entity;

import com.chu.common.po.BasePO;
import com.chu.common.po.GeneratedUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String status;
    private String remark;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Dictionary parent;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Dictionary> children = new ArrayList<>();
}
