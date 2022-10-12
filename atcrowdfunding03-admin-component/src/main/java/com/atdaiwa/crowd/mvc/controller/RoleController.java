package com.atdaiwa.crowd.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import com.atdaiwa.crowd.constant.CrowdConstants;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atdaiwa.crowd.entity.Role;
import com.atdaiwa.crowd.service.api.RoleService;
import com.atdaiwa.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 角色分配頁面控制器；
 *
 * @author Administrator
 */
@RestController
public class RoleController {

	private static RoleService staticRoleService;

	@Resource
	private RoleService roleService;

	@PostConstruct
	private void initStaticService() {
		staticRoleService = roleService;
	}

	private static boolean hasDuplicates(@NonNull Role role) {
		List<Role> rolesList = staticRoleService.getAll();
		List<String> roleList = new ArrayList<>();
		rolesList.forEach(roles -> roleList.add(roles.getName()));
		return roleList.contains(role.getName());
	}

	@PreAuthorize("hasAuthority('role:get')")
	@RequestMapping("/role/get/page/info.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		// 1.調用Service方法獲取分頁數據；
		PageInfo<Role> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);
		// 2.成功則返回JSON數據，失敗則會通過框架的異常處理機制；
		return ResultEntity.successWithData(pageInfo);
	}

	@PreAuthorize("hasAuthority('role:add')")
	@RequestMapping("/role/save.json")
	public ResultEntity<String> saveRole(@NonNull Role role) {
		if ("".equals(role.getName())) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_STRING_INVALID);
		} else if (hasDuplicates(role)) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_CHARACTER_DUPLICATED);
		}
		roleService.saveRole(role);
		return ResultEntity.successWithoutData();
	}

	@PreAuthorize("hasAuthority('role:delete')")
	@RequestMapping("/role/update.json")
	public ResultEntity<String> editRole(Role role) {
		if (hasDuplicates(role)) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_CHARACTER_DUPLICATED);
		}
		roleService.editRole(role);
		return ResultEntity.successWithoutData();
	}

	@PreAuthorize("hasAuthority('role:delete')")
	@RequestMapping("/role/remove/by/role/id/array.json")
	public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {
		roleService.removeRole(roleIdList);
		return ResultEntity.successWithoutData();
	}
}
