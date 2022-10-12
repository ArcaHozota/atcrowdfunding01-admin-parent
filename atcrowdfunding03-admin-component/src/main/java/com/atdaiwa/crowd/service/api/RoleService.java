package com.atdaiwa.crowd.service.api;

import java.util.List;

import com.atdaiwa.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {

    /**
     * 獲取角色分頁信息；
     *
     * @param pageNum  頁碼；
     * @param pageSize 分頁大小；
     * @param keyword  關鍵字；
     * @return PageInfo<Role>
     */
    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 保存角色信息；
     *
     * @param role 角色對象；
     */
    void saveRole(Role role);

    /**
     * 更新角色信息；
     *
     * @param role 角色對象；
     */
    void editRole(Role role);

    /**
     * 刪除角色信息；
     *
     * @param roleIdList 角色ID列表；
     */
    void removeRole(List<Integer> roleIdList);

    /**
     * 獲取已分配的角色信息；
     *
     * @param adminId 角色ID；
     * @return List<Role>
     */
    List<Role> getAssignedRole(Integer adminId);

    /**
     * 獲取未分配的角色信息；
     *
     * @param adminId 角色ID；
     * @return List<Role>
     */
    List<Role> getUnassignedRole(Integer adminId);
}
