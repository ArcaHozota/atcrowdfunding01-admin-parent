package com.atdaiwa.crowd.service.api;

import java.util.List;
import java.util.Map;

import com.atdaiwa.crowd.entity.Auth;

public interface AuthService {

	/**
	 * 獲取所有權限
	 * @return List<Auth>
	 */
	List<Auth> getAll();

	List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

	void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}
