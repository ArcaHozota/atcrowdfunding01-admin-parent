package com.atdaiwa.crowd.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atdaiwa.crowd.entity.Auth;
import com.atdaiwa.crowd.entity.Role;
import com.atdaiwa.crowd.service.api.AdminService;
import com.atdaiwa.crowd.service.api.AuthService;
import com.atdaiwa.crowd.service.api.RoleService;
import com.atdaiwa.crowd.util.ResultEntity;

/**
 * 權限分配功能控制器；
 * 
 * @author Administrator
 *
 */
@Controller
public class AssignmentController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthService authService;

	@RequestMapping("/assign/get/all/auth.json")
	@ResponseBody
	public ResultEntity<List<Auth>> getAllAuth() {
		List<Auth> authList = authService.getAll();
		return ResultEntity.successWithData(authList);
	}

	@RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
	@ResponseBody
	public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
		List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
		return ResultEntity.successWithData(authIdList);
	}

	@RequestMapping("/assign/to/assignment/role/page.html")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
		// 1.查詢已分配角色；
		List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
		// 2.查詢未分配角色；
		List<Role> unassignedRoleList = roleService.getUnassignedRole(adminId);
		// 3.存入模型；
		modelMap.addAttribute("assignedRoleList", assignedRoleList);
		modelMap.addAttribute("unassignedRoleList", unassignedRoleList);
		return "assign-role";
	}

	@RequestMapping("/assign/do/role/assign.html")
	public String saveAdminRole(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword,
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
		adminService.saveAdminRole(adminId, roleIdList);
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

	@RequestMapping("/assign/do/role/assign/auth.json")
	@ResponseBody
	public ResultEntity<List<Integer>> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map) {
		authService.saveRoleAuthRelationship(map);
		return ResultEntity.successWithoutData();
	}
}
