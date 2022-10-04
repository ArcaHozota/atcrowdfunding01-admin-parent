package com.atdaiwa.crowd.service.api;

import java.util.List;

import com.atdaiwa.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {

	PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

	void saveRole(Role role);

	void editRole(Role role);
	
	void removeRole(List<Integer> roleIdList);

	List<Role> getAssignedRole(Integer adminId);

	List<Role> getUnassignedRole(Integer adminId);
}
