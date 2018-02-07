package com.chu.service;

import com.chu.entity.Menu;
import com.chu.entity.User;

import java.util.List;

/**
 * 数据字典业务处理接口
 */
public interface MenuService {
    Menu getMenu(String id);

    List<Menu> list(Menu menu);

    List<Menu> list(int limit, int offset, Menu menu);

    long count(Menu menu);

    Menu save(Menu menu);

    long delete(List<String> ids);

    List<Menu> listMenus(List<Menu> menuList);

    List<Menu> getUserMenus(User user);
}
