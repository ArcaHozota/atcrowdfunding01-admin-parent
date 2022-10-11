package com.atdaiwa.crowd.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atdaiwa.crowd.entity.Menu;
import com.atdaiwa.crowd.service.api.MenuService;
import com.atdaiwa.crowd.util.ResultEntity;

import javax.annotation.Resource;

/**
 * 菜單頁面控制器；
 *
 * @author Administrator
 */
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    @PreAuthorize("hasAnyRole('支店長','社長/本店長','代表取締役社長')")
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree() {
        // 1.查詢全部的Menu對象；
        List<Menu> menuList = menuService.getAll();
        // 2.聲明一個變量用來儲存找到的根節點；
        Menu root = null;
        // 3.創建Map對象用來儲存id和Menu對象的對應關係便於查找父節點；
        Map<Integer, Menu> menuMap = new HashMap<>();
        // 4.遍歷menuList填充menuMap對象；
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
        // 5.再次遍歷menuList對象，查找根節點，組裝父子節點；
        for (Menu menu : menuList) {
            // 6.獲取當前對象的pid屬性值；
            Integer pid = menu.getPid();
            // 7.檢查pid是否為null；
            if (pid == null) {
                // 8.把此對象賦予root；
                root = menu;
                // 9.停止本次循環；
                continue;
            }
            // 10.如果pid不為null，則說明有父節點，找到父節點就可以進行組裝，建立父子關係；
            Menu fatherMenu = menuMap.get(pid);
            // 11.將當前節點存入父節點的children集合；
            fatherMenu.getChildren().add(menu);
        }
        // 13.將組裝好的樹形結構返回；
        return ResultEntity.successWithData(root);
    }

    @PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
    @RequestMapping("/menu/save.json")
    public ResultEntity<Menu> saveMenu(Menu menu) {
        menuService.save(menu);
        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
    @RequestMapping("/menu/update.json")
    public ResultEntity<Menu> updateMenu(Menu menu) {
        menuService.update(menu);
        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasRole('代表取締役社長')")
    @RequestMapping("/menu/remove.json")
    public ResultEntity<Menu> removeMenu(@RequestParam("id") Integer id) {
        menuService.remove(id);
        return ResultEntity.successWithoutData();
    }
}
