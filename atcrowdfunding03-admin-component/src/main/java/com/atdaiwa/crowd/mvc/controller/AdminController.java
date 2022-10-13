package com.atdaiwa.crowd.mvc.controller;

import com.atdaiwa.crowd.constant.CrowdConstants;
import com.atdaiwa.crowd.util.ResultEntity;

import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * 管理頁面控制器；
 *
 * @author Administrator
 */
@RestController
public class AdminController {

	@Resource
	private AdminService adminService;

	private boolean hasDulpicates(@NonNull Admin admin) {
		List<Admin> adminList = adminService.getAll();
		List<String> acctLists = new ArrayList<>();
		adminList.forEach(admins -> acctLists.add(admins.getLoginAccount()));
		return acctLists.contains(admin.getLoginAccount());
	}

	@PreAuthorize("hasAuthority('user:get')")
	@RequestMapping("/admin/get/page/info.json")
	public ResultEntity<PageInfo<Admin>> getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
		// 1.調用Service方法獲取分頁數據；
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
		// 2.成功則返回JSON數據，失敗則會通過框架的異常處理機制；
		return ResultEntity.successWithData(pageInfo);
	}

	@PreAuthorize("hasAuthority('user:delete')")
	@RequestMapping("/admin/remove/by/role/id/array.json")
	public ResultEntity<String> removeByAdminIdArray(@RequestBody List<Integer> adminIdList) {
		adminService.remove(adminIdList);
		return ResultEntity.successWithoutData();
	}

	@PreAuthorize("hasAnyRole('会長','代表取締役社長')")
	@RequestMapping("/admin/save.json")
	public ResultEntity<String> save(@NonNull Admin admin) {
		if ("".equals(admin.getLoginAccount()) || "".equals(admin.getUserPassword())) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_STRING_INVALID);
		} else if (this.hasDulpicates(admin)) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_ACCOUNT_DUPLICATED);
		}
		// 1.執行保存；
		adminService.save(admin);
		// 2.頁面跳轉；
		return ResultEntity.successWithoutData();
	}

	@PreAuthorize("hasAnyRole('会長','代表取締役社長')")
	@RequestMapping("/admin/update.json")
	public ResultEntity<String> update(@NonNull Admin admin) {
		if ("".equals(admin.getLoginAccount()) || "".equals(admin.getUserPassword())) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_STRING_INVALID);
		} else if (this.hasDulpicates(admin)) {
			return ResultEntity.failed(CrowdConstants.MESSAGE_ACCOUNT_DUPLICATED);
		}
		// 1.執行更新；
		adminService.update(admin);
		// 2.頁面跳轉；
		return ResultEntity.successWithoutData();
	}
}
