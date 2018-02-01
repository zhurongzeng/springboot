package com.chu.po;

import com.chu.common.po.BasePO;
import com.chu.common.po.GeneratedUID;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_role")
public class Role extends BasePO {
    @Id
    @GeneratedUID
    private String id;
    private String name;
}
