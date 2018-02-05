package com.chu.entity;

import com.chu.common.po.BasePO;
import com.chu.common.po.GeneratedUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "t_user")
public class User extends BasePO implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedUID
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String certType;
    private String certId;
    private String birthday;
    private String gender;
    private String phone;
    private String mailAddress;
    private String address;
    private String type;
    private String level;
    private String qqNo;
    private String wxNo;
    private String remark;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<Role> roles = this.getRoles();
        for (Role role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
