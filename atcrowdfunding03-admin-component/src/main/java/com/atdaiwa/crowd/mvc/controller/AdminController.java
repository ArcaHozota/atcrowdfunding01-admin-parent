package com.atdaiwa.crowd.mvc.controller;

import com.atdaiwa.crowd.util.ResultEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;

import java.util.List;

import javax.annotation.Resource;

/**
 * 管理頁面控制器；
 *
 * @author Administrator
 */
@RestController
public class AdminController {

    @Resource
    private AdminService adminService;

    @PreAuthorize("hasAuthority('user:get')")
    @RequestMapping("/admin/get/page/info.json")
    public ResultEntity<PageInfo<Admin>> getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        // 1.調用Service方法獲取分頁數據；
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        // 2.成功則返回JSON數據，失敗則會通過框架的異常處理機制；
        return ResultEntity.successWithData(pageInfo);
    }

    @PreAuthorize("hasAuthority('user:delete')")
    @RequestMapping("/admin/remove/by/role/id/array.json")
    public ResultEntity<String> removeByAdminIdArray(@RequestBody List<Integer> adminIdList) {
        adminService.remove(adminIdList);
        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
    @RequestMapping("/admin/update.json")
    public ResultEntity<String> update(Admin admin) {
        // 1.執行更新；
        adminService.update(admin);
        // 2.頁面跳轉；
        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
    @RequestMapping("/admin/save.json")
    public ResultEntity<String> save(Admin admin) {
        // 1.執行更新；
        adminService.saveAdmin(admin);
        // 2.頁面跳轉；
        return ResultEntity.successWithoutData();
    }

//    @PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
//    @RequestMapping("/admin/to/edit/page.html")
//    public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
//        Admin admin = adminService.getAdminById(adminId);
//        modelMap.addAttribute("admin", admin);
//        return "admin-edit";
//    }

//    @PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
//    @RequestMapping("/admin/update.html")
//    public String update(Admin admin, @RequestParam("pageNum") Integer pageNum,
//                         @RequestParam("keyword") String keyword) {
//        // 1.執行更新；
//        adminService.update(admin);
//        // 2.頁面跳轉；
//        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
//    }
}
