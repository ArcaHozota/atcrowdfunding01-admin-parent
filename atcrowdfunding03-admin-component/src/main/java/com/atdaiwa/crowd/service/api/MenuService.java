package com.atdaiwa.crowd.service.api;

import java.util.List;

import com.atdaiwa.crowd.entity.Menu;

public interface MenuService {

	List<Menu> getAll();

	void save(Menu menu);

	void update(Menu menu);

	void remove(Integer id);
}
