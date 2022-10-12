package com.atdaiwa.crowd.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atdaiwa.crowd.constant.CrowdConstants;
import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.exception.NotLoginException;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1.從請求域中獲取session對象；
		HttpSession session = request.getSession();
		// 2.嘗試從session域中獲取Admin對象；
		Admin admin = (Admin) session.getAttribute(CrowdConstants.ATTR_NAME_LOGIN_ADMIN);
		// 3.判斷取得的Admin對象是否為空；
		if (admin == null) {
			// 4.拋出異常；
			throw new NotLoginException(CrowdConstants.MESSAGE_ACCESS_PROHIBITED);
		} else {
			// 5.放行；
			return true;
		}
	}
}
