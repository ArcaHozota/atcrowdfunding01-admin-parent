package com.atdaiwa.crowd.service.api;

import java.util.List;

import com.atdaiwa.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAccount(String loginAccount, String userPassword);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRole(Integer adminId, List<Integer> roleIdList);
}
