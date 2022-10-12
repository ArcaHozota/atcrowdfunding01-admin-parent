package com.atdaiwa.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.entity.AdminExample;
import com.atdaiwa.crowd.entity.AdminExample.Criteria;
import com.atdaiwa.crowd.dao.AdminMapper;
import com.atdaiwa.crowd.service.api.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminMapper adminMapper;

	@Resource
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void saveAdmin(Admin admin) {
		// 1.密碼加密；
		String password = admin.getUserPassword();
		admin.setUserPassword(passwordEncoder.encode(password));
		// 2.生成創建時間；
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = dateFormat.format(date);
		admin.setCreateTime(createTime);
		// 3.執行保存操作；
		adminMapper.insert(admin);
	}

	@Override
	public List<Admin> getAll() {
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
		// 1.調用PageHelper的靜態方法，開啟分頁功能；
		PageHelper.startPage(pageNum, pageSize);
		// 2.執行查詢，獲取數據；
		List<Admin> adminList = adminMapper.selectByKeyword(keyword);
		// 3.封裝到PageInfo對象中；
		return new PageInfo<>(adminList);
	}

	@Override
	public void remove(List<Integer> adminIdList) {
		// 1.根據主鍵刪除指定值；
		AdminExample example = new AdminExample();
		// 2.生成查詢標準對象；
		Criteria criteria = example.createCriteria();
		// 3.插入刪除條件；
		criteria.andIdIn(adminIdList);
		// 4.執行刪除；
		adminMapper.deleteByExample(example);
	}

	@Override
	public Admin getAdminById(Integer adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public void update(Admin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public void saveAdminRole(Integer adminId, List<Integer> roleIdList) {
		// 1.根據adminId刪除舊的數據；
		adminMapper.deleteOldRelationship(adminId);
		// 2.保存新的關聯關係；
		if (roleIdList != null && roleIdList.size() > 0) {
			adminMapper.insertNewRelationship(adminId, roleIdList);
		}
	}

	@Override
	public Admin getAdminByLoginAccount(String loginAccount) {
		AdminExample example = new AdminExample();
		example.createCriteria().andLoginAccountEqualTo(loginAccount);
		return adminMapper.selectByExample(example).get(0);
	}
}
