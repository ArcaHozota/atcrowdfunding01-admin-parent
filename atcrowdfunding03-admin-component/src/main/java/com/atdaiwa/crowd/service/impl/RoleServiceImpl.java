package com.atdaiwa.crowd.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atdaiwa.crowd.entity.Role;
import com.atdaiwa.crowd.entity.RoleExample;
import com.atdaiwa.crowd.entity.RoleExample.Criteria;
import com.atdaiwa.crowd.dao.RoleMapper;
import com.atdaiwa.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.開啟分頁功能；
        PageHelper.startPage(pageNum, pageSize);
        // 2.執行查詢；
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        // 3.封裝為PageInfo對象返回；
        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        // 執行插入；
        roleMapper.insert(role);
    }

    @Override
    public void editRole(Role role) {
        // 執行更新；
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        // 1.獲取Role的實例對象；
        RoleExample example = new RoleExample();
        // 2.生成查詢標準對象；
        Criteria criteria = example.createCriteria();
        // 3.插入刪除條件；
        criteria.andIdIn(roleIdList);
        // 4.執行刪除；
        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnassignedRole(Integer adminId) {
        return roleMapper.selectUnassignedRole(adminId);
    }
}
