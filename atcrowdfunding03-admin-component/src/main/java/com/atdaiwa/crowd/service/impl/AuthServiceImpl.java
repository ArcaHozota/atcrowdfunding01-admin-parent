package com.atdaiwa.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atdaiwa.crowd.dao.AuthMapper;
import com.atdaiwa.crowd.entity.Auth;
import com.atdaiwa.crowd.entity.AuthExample;
import com.atdaiwa.crowd.service.api.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAll() {
		return authMapper.selectByExample(new AuthExample());
	}

	@Override
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
		return authMapper.selectAssignedAuthIdByRoleId(roleId);
	}

	@Override
	public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
		List<Integer> roleIdList = map.get("roleId");
		Integer roleId = roleIdList.get(0);
		authMapper.deleteOldRelationship(roleId);
		List<Integer> authIdList = map.get("authIdArray");
		if (authIdList != null && authIdList.size() > 0) {
			authMapper.insertNewRelationship(roleId,authIdList);
		}
	}

	@Override
	public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
		return authMapper.selectAssignedAuthNameByAdminId(adminId);
	}
}
