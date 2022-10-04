package com.atdaiwa.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atdaiwa.crowd.dao.AdminMapper;
import com.atdaiwa.crowd.dao.RoleMapper;
import com.atdaiwa.crowd.entity.Admin;
import com.atdaiwa.crowd.entity.Role;

import com.atdaiwa.crowd.service.api.AdminService;

@ContextConfiguration(locations = { "classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CrowdTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleMapper roleMapper;

	@Test
	public void testRoles() {
		for (int i = 0; i < 233; i++) {
			roleMapper.insert(new Role(null, "Role10" + i));
		}
	}

	@Test
	public void testData() {
		for (int i = 0; i < 225; i++) {
			adminMapper.insert(new Admin(null, "cct" + i, "psd007" + i, "ccat" + i, "cct" + i + "@gmail.com", null));
		}
	}

	@Test
	public void testTx() {
		Admin admin = new Admin(null, "Jerry", "1234", "Jerry Magderlle", "jerry@qq.com", null);
		adminService.saveAdmin(admin);
	}

	@Test
	public void testLog() {
		// 1.獲取日誌對象；
		Logger logger = LoggerFactory.getLogger(CrowdTest.class);
		// 2.根據不同對象級別打印日誌；
		logger.debug("This is the debug.");
		logger.info("This is info.");
		logger.warn("This is warning.");
		logger.error("This is error.");
	}

	@Test
	public void testInsertAdmin() {
		Admin admin = new Admin(null, "Timmy", "123456", "little timmy", "timmy@gmail.com", null);
		int number = adminMapper.insert(admin);
		System.out.println("The effected rows:" + number);
	}

	@Test
	public void test() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}
}
