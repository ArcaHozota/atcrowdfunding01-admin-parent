package com.atdaiwa.crowd.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.annotation.Resource;

/**
 * 權限分配功能控制器；
 * 
 * @author Administrator
 *
 */
@Controller
public class AssignmentController {

	@Resource
	private AdminService adminService;

	@Resource
	private RoleService roleService;

	@Resource
	private AuthService authService;

	@PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
	@RequestMapping("/assign/get/all/auth.json")
	@ResponseBody
	public ResultEntity<List<Auth>> getAllAuth() {
		List<Auth> authList = authService.getAll();
		return ResultEntity.successWithData(authList);
	}

	@PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
	@RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
	@ResponseBody
	public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
		List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
		return ResultEntity.successWithData(authIdList);
	}

	@PreAuthorize("hasRole('代表取締役社長')")
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

	@PreAuthorize("hasRole('代表取締役社長')")
	@RequestMapping("/assign/do/role/assign.html")
	public String saveAdminRole(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword,
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
		adminService.saveAdminRole(adminId, roleIdList);
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

	@PreAuthorize("hasRole('代表取締役社長')")
	@RequestMapping("/assign/do/role/assign/auth.json")
	@ResponseBody
	public ResultEntity<List<Integer>> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map) {
		authService.saveRoleAuthRelationship(map);
		return ResultEntity.successWithoutData();
	}
}
