package com.chu.entity;

import com.chu.common.po.BasePO;
import com.chu.common.po.GeneratedUID;
import lombok.Data;

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
public class Menu extends BasePO {
    @Id
    @GeneratedUID
    private String id;
    private String name;
    //    private String parentId;
    private int levelNum;
    private String icon;
    private String path;
    private String status;
    private int orderNum;
    private String remark;
    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<Menu> subMenus = new ArrayList<>();
}
