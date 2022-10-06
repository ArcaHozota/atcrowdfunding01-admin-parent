package com.atdaiwa.crowd.service.api;

import java.util.List;

import com.atdaiwa.crowd.entity.Menu;

public interface MenuService {

    /**
     * 獲取菜單列表；
     *
     * @return List<Menu>
     */
    List<Menu> getAll();

    /**
     * 保存菜單信息；
     *
     * @param menu 菜單對象；
     */
    void save(Menu menu);

    /**
     * 更新菜單信息；
     *
     * @param menu 菜單對象；
     */
    void update(Menu menu);

    /**
     * 刪除菜單；
     *
     * @param id 菜單ID；
     */
    void remove(Integer id);
}
