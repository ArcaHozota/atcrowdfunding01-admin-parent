package com.atdaiwa.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atdaiwa.crowd.constant.CrowdConstants;
import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.entity.AdminExample;
import com.atdaiwa.crowd.entity.AdminExample.Criteria;
import com.atdaiwa.crowd.exception.AccountExistedException;
import com.atdaiwa.crowd.exception.AcctExistedForUpdateException;
import com.atdaiwa.crowd.exception.LoginFailedException;
import com.atdaiwa.crowd.dao.AdminMapper;
import com.atdaiwa.crowd.service.api.AdminService;
import com.atdaiwa.crowd.util.CrowdUtil;
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

	private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

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
		try {
			adminMapper.insert(admin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("異常全類名；" + e.getClass().getName());
			if (e instanceof DuplicateKeyException) {
				throw new AccountExistedException(CrowdConstants.MESSAGE_ACCOUNT_DUPLICATED);
			}
		}
	}

	@Override
	public List<Admin> getAll() {
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin getAdminByLoginAccount(String loginAccount, String userPassword) {
		// 1.根據登錄帳號查詢Admin對象；
		AdminExample adminExample = new AdminExample();
		Criteria criteria = adminExample.createCriteria();
		criteria.andLoginAccountEqualTo(loginAccount);
		List<Admin> list = adminMapper.selectByExample(adminExample);
		// 2.判斷Admin對象是否為null值；
		Admin admin = list.get(0);
		// 3.如果為空值，則拋出異常；
		if (list == null || list.size() == 0) {
			throw new LoginFailedException(CrowdConstants.MESSAGE_LOGIN_FAILED);
		} else if (list.size() > 1) {
			throw new LoginFailedException(CrowdConstants.MESSAGE_SYSTEM_ERROR_ACCOUNT_NOT_UNIQUE);
		} else if (admin == null) {
			throw new LoginFailedException(CrowdConstants.MESSAGE_LOGIN_FAILED);
		} else {
			// 4.如果不為空，則將數據庫密碼從Admin對象中取出；
			String password = admin.getUserPassword();
			// 5.將表單提交的明文密碼進行加密；
			String formPassword = CrowdUtil.toMD5(userPassword);
			// 6.比較密碼；
			if (!Objects.equals(password, formPassword)) {
				// 7.如果結果不一致，則拋出異常；
				throw new LoginFailedException(CrowdConstants.MESSAGE_LOGIN_FAILED);
			} else {
				// 8.如果一致則返回Admin對象；
				return admin;
			}
		}
	}

	@Override
	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
		// 1.調用PageHelper的靜態方法，開啟分頁功能；
		PageHelper.startPage(pageNum, pageSize);
		// 2.執行查詢，獲取數據；
		List<Admin> list = adminMapper.selectByKeywordPage(keyword);
		// 3.封裝到PageInfo對象中；
		return new PageInfo<>(list);
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
		// 1.執行更新操作；
		try {
			adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("異常全類名；" + e.getClass().getName());
			if (e instanceof DuplicateKeyException) {
				throw new AcctExistedForUpdateException(CrowdConstants.MESSAGE_ACCOUNT_DUPLICATED);
			}
		}
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
