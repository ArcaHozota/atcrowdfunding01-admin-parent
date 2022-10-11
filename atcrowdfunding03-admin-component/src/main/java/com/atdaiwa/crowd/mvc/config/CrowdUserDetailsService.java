package com.atdaiwa.crowd.mvc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.entity.Role;
import com.atdaiwa.crowd.mvc.interceptor.SecurityAdmin;
import com.atdaiwa.crowd.service.api.AdminService;
import com.atdaiwa.crowd.service.api.AuthService;
import com.atdaiwa.crowd.service.api.RoleService;

import javax.annotation.Resource;

@Component
public class CrowdUserDetailsService implements UserDetailsService {

	@Resource
	private AdminService adminService;

	@Resource
	private AuthService authService;

	@Resource
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminService.getAdminByLoginAccount(username);
		Integer adminId = admin.getId();
		List<Role> assignedRoles = roleService.getAssignedRole(adminId);
		List<String> authList = authService.getAssignedAuthNameByAdminId(adminId);
		List<GrantedAuthority> list = new ArrayList<>();
		for (Role role : assignedRoles) {
			String roleName = "ROLE_" + role.getName();
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
			list.add(authority);
		}
		for (String authName : authList) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authName);
			list.add(authority);
		}
		SecurityAdmin spctAdmin = new SecurityAdmin(admin, list);
		return spctAdmin;
	}
}
