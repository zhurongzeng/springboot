package com.chu.common.po;

import com.chu.common.utils.ContextHolderEx;
import com.chu.common.utils.UUIDUtil;
import com.chu.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * 实体公共信息
 *
 * @author
 * @create 2018-01-30 17:36
 **/
@Data
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BasePO implements Serializable {

    private static final long serialVersionUID = -158291998395462981L;

    @JsonIgnore
    @Column(nullable = false, length = 50, updatable = false)
    private String createUser;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createDate;

    @JsonIgnore
    @Column(nullable = false, length = 50)
    private String updateUser;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(nullable = false)
    private Date updateDate;

    public BasePO() {
        generateUID();
    }

    @PrePersist
    public void PrePersist() {
        User user = ContextHolderEx.getUserInfo();
        setCreateUser(user.getUsername());
        setUpdateUser(user.getUsername());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdateUser(ContextHolderEx.getUserInfo().getUsername());
    }

    private void generateUID() {
        Class<?> entityType = this.getClass();
        Field[] columns = entityType.getDeclaredFields();
        try {
            for (Field column : columns) {
                if (column.isAnnotationPresent(GeneratedUID.class)) {
                    column.setAccessible(true);
                    column.set(this, UUIDUtil.getUid());
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Exception when generateUID");
        }
    }
}
