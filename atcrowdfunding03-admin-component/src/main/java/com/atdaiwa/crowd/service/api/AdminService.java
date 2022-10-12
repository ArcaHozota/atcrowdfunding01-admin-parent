package com.atdaiwa.crowd.service.api;

import java.util.List;

import com.atdaiwa.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 */
public interface AdminService {

    /**
     * 儲存用戶信息；
     *
     * @param admin 用戶對象；
     */
    void saveAdmin(Admin admin);

    /**
     * 獲取所有用戶信息；
     *
     * @return List<Admin>
     */
    List<Admin> getAll();

    /**
     * 根據登錄賬戶查找用戶信息；
     *
     * @param loginAccount 用戶賬號；
     * @return Admin對象
     */
    Admin getAdminByLoginAccount(String loginAccount);

    /**
     * 獲取已完成分頁用戶信息
     *
     * @param keyword 檢索關鍵字；
     * @param pageNum 頁碼；
     * @param pageSize 分頁大小；
     * @return PageInfo<Admin>
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 刪除用戶信息；
     *
     * @param adminIdList 用戶ID列表；
     */
    void remove(List<Integer> adminIdList);

    /**
     * 通過id查找用戶信息；
     *
     * @param adminId 用戶ID；
     * @return Admin對象
     */
    Admin getAdminById(Integer adminId);

    /**
     * 更新用戶信息；
     *
     * @param admin 用戶對象；
     */
    void update(Admin admin);

    /**
     * 儲存用戶角色信息；
     *
     * @param adminId 用戶ID；
     * @param roleIdList 用戶角色列表；
     */
    void saveAdminRole(Integer adminId, List<Integer> roleIdList);
}
