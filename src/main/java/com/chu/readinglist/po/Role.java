package com.chu.readinglist.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
