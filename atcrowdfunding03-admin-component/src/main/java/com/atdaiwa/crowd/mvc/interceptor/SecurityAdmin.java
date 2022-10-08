package com.atdaiwa.crowd.mvc.interceptor;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.atdaiwa.crowd.entity.Admin;

/**
 * 獲取原始的Admin對象，繼承SPCTUser類；
 * 
 * @author Administrator
 *
 */
public class SecurityAdmin extends User {

	private static final long serialVersionUID = 1L;

	private Admin motoAdmin;

	public SecurityAdmin(Admin motoAdmin, List<GrantedAuthority> authorities) {
		super(motoAdmin.getLoginAccount(), motoAdmin.getUserPassword(), authorities);
		this.motoAdmin = motoAdmin;
	}

	public Admin getMotoAdmin() {
		return motoAdmin;
	}
}
