package com.atdaiwa.crowd.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atdaiwa.crowd.entity.Role;
import com.atdaiwa.crowd.service.api.RoleService;
import com.atdaiwa.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

/**
 * 角色分配頁面控制器；
 * 
 * @author Administrator
 *
 */
@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping("/role/get/page/info.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		// 1.調用Service方法獲取分頁數據；
		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
		// 2.成功則返回JSON數據，失敗則會通過框架的異常處理機制；
		return ResultEntity.successWithData(pageInfo);
	}

	@RequestMapping("/role/save.json")
	public ResultEntity<String> saveRole(Role role) {
		roleService.saveRole(role);
		return ResultEntity.successWithoutData();
	}

	@RequestMapping("/role/update.json")
	public ResultEntity<String> editRole(Role role) {
		roleService.editRole(role);
		return ResultEntity.successWithoutData();
	}

	@RequestMapping("/role/remove/by/role/id/array.json")
	public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {
		roleService.removeRole(roleIdList);
		return ResultEntity.successWithoutData();
	}
}
