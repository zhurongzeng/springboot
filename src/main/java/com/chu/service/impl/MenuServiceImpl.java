package com.chu.service.impl;

import com.chu.dao.MenuDAO;
import com.chu.entity.Menu;
import com.chu.entity.Role;
import com.chu.entity.User;
import com.chu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.*;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDAO menuDAO;

    @Override
    public Menu getMenu(String id) {
        return menuDAO.findOne(id);
    }

    @Override
    public List<Menu> list(Menu menu) {
        return menuDAO.findAll((root, query, cb) -> getPredicate(root, cb, menu));
    }

    @Override
    public List<Menu> list(int limit, int offset, Menu menu) {
        PageRequest pageRequest = new PageRequest(offset / limit, limit, Sort.Direction.DESC, "updateDate");
        Page<Menu> page = menuDAO.findAll((root, query, cb) -> getPredicate(root, cb, menu), pageRequest);
        return page.getContent();
    }

    @Override
    public long count(Menu menu) {
        return menuDAO.count((root, query, cb) -> getPredicate(root, cb, menu));
    }

    @Override
    public Menu save(Menu menu) {
        return menuDAO.save(menu);
    }

    @Override
    public long delete(List<String> ids) {
        List<Menu> menuList = new ArrayList<>();
        for (String id : ids) {
            Menu menu = new Menu();
            menu.setId(id);
            menuList.add(menu);
        }
        menuDAO.delete(menuList);
        return ids.size();
    }

    /**
     * 生成查询条件
     *
     * @param root
     * @param cb
     * @param menu
     * @return
     */
    private Predicate getPredicate(Root<Menu> root, CriteriaBuilder cb, @NotNull Menu menu) {
        List<Predicate> list = new ArrayList<>();

        if (StringUtils.isNotBlank(menu.getName())) {
            list.add(cb.like(root.get("name").as(String.class), "%" + menu.getName() + "%"));
        }

        if (StringUtils.isNotBlank(menu.getParent().getId())) {
            list.add(cb.equal(root.get("parentId").as(String.class), menu.getParent().getId()));
        }

        list.add(cb.equal(root.get("status").as(String.class), "on"));

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    /**
     * 查询用户菜单
     *
     * @param user
     * @return
     */
    @Override
    public List<Menu> getUserMenus(User user) {
        HashSet<Menu> menuSet = new HashSet<>();
        // 先将角色中的所有子菜单查询出来
        List<Menu> childList = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            List<Menu> roleMenus = role.getMenus();
            for (Menu menu : roleMenus) {
                if (menuSet.add(menu)) {
                    childList.add(menu);
                }
            }
        }
        // 再查询出每个子菜单的所有父菜单
        for (Menu menu : childList) {
            getParentMenus(menu, menuSet);
        }
        return new ArrayList<>(menuSet);
    }

    /**
     * 递归获取当前菜单的父菜单
     *
     * @param menu
     * @param parentSet
     */
    private void getParentMenus(Menu menu, HashSet<Menu> parentSet) {
        Menu parent = menu.getParent();
        if (parent != null) {
            getParentMenus(parent, parentSet);
            parentSet.add(parent);
        }
    }

    /**
     * 生成树形用户菜单
     *
     * @param allMenus
     * @return
     */
    @Override
    public List<Menu> listMenus(List<Menu> allMenus) {
        List<Menu> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (Menu menu : allMenus) {
            // 一级菜单没有parentId
            if (menu.getParent() == null) {
                menuList.add(menu);
            }
        }
        // 为一级菜单设置子菜单，getChildren是递归调用的
        for (Menu menu : menuList) {
            menu.setChildren(getChildMenus(menu.getId(), allMenus));
        }
        // 对菜单进行排序
        menuList.sort(Comparator.comparingInt(Menu::getOrderNum));
        return menuList;
    }

    /**
     * 递归调用，查询子菜单
     *
     * @param menuId   父菜单ID
     * @param allMenus 菜单列表
     * @return 菜单树
     */
    private List<Menu> getChildMenus(String menuId, List<Menu> allMenus) {
        // 子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : allMenus) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParent() != null) {
                if (menuId.equals(menu.getParent().getId())) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Menu child : childList) {
            // 没有url子菜单还有子菜单
            if (StringUtils.isBlank(child.getUrl())) {
                // 递归查询子菜单
                child.setChildren(getChildMenus(child.getId(), allMenus));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        // 对菜单进行排序
        childList.sort(Comparator.comparingInt(Menu::getOrderNum));
        return childList;
    }
}
