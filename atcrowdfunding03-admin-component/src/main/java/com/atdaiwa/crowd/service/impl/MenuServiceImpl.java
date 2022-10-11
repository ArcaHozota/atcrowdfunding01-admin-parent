package com.atdaiwa.crowd.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atdaiwa.crowd.dao.MenuMapper;
import com.atdaiwa.crowd.entity.Menu;
import com.atdaiwa.crowd.entity.MenuExample;
import com.atdaiwa.crowd.service.api.MenuService;

import javax.annotation.Resource;


/**
 * @author Administrator
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void save(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void remove(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
