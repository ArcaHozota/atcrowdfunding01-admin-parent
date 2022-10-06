package com.atdaiwa.crowd.service.api;

import java.util.List;
import java.util.Map;

import com.atdaiwa.crowd.entity.Auth;

public interface AuthService {

    /**
     * 獲取所有權限信息；
     *
     * @return List<Auth>
     */
    List<Auth> getAll();

    /**
     * 通過角色查找已被賦予的權限信息；
     *
     * @param roleId 角色ID；
     * @return List<Integer>
     */
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    /**
     * 儲存角色權限關係信息；
     *
     * @param map 集合；
     */
    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}
