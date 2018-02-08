package com.chu.entity;

import com.chu.common.po.BasePO;
import com.chu.common.po.GeneratedUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单类
 *
 * @author
 * @create 2018-02-05 17:24
 **/
@Data
@Entity
@Table(name = "t_menu")
@ToString(exclude = {"children"})
@EqualsAndHashCode(exclude = {"parent"})
public class Menu extends BasePO {
    @Id
    @GeneratedUID
    private String id;
    private String name;
    private int levelNum;
    private String icon;
    private String url;
    private String status;
    private int orderNum;
    private String remark;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Menu parent;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Menu> children = new ArrayList<>();
}
