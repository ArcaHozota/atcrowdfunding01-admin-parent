package com.atdaiwa.crowd.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atdaiwa.crowd.constant.CrowdConstants;
import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;

/**
 * 管理頁面控制器；
 * 
 * @author Administrator
 *
 */
@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PreAuthorize("hasAuthority('user:get')")
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, ModelMap modelMap) {
		// 1.獲取PageInfo對象；
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
		// 2.將PageInfo對象存入模型；
		modelMap.addAttribute(CrowdConstants.ATTR_NAME_PAGE_INFO, pageInfo);
		return "admin-page";
	}

	@PreAuthorize("hasAuthority('user:delete')")
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword) {
		// 1.執行刪除；
		adminService.remove(adminId);
		// 2.頁面跳轉；
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

	@PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
	@RequestMapping("/admin/save.html")
	public String save(Admin admin) {
		// 1.執行保存；
		adminService.saveAdmin(admin);
		// 2.頁面跳轉；
		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
	}

	@PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
		Admin admin = adminService.getAdminById(adminId);
		modelMap.addAttribute("admin", admin);
		return "admin-edit";
	}

	@PreAuthorize("hasAnyRole('社長/本店長','代表取締役社長')")
	@RequestMapping("/admin/update.html")
	public String update(Admin admin, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword) {
		// 1.執行更新；
		adminService.update(admin);
		// 2.頁面跳轉；
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}

//	@RequestMapping("/admin/do/logout.html")
//	public String doLogout(HttpSession session) {
//		// 1.強制session失效；
//		session.invalidate();
//		// 2.退出登錄，返回登錄畫面；
//		return "redirect:/admin/to/login/page.html";
//	}

//	@RequestMapping("/admin/do/login.html")
//	public String doLogin(@RequestParam("loginAccount") String loginAccount,
//			@RequestParam("userPassword") String userPassword, HttpSession session) {
//		// 1.調用Service方法進行登錄檢查；
//		Admin admin = adminService.getAdminByLoginAccount(loginAccount, userPassword);
//		// 2.將登錄成功返回的Admin對象傳入session域中；
//		session.setAttribute(CrowdConstants.ATTR_NAME_LOGIN_ADMIN, admin);
//		return "redirect:/admin/to/main/page.html";
//	}
}
