package com.test.database;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.database.dao.ActionsDao;

@Service("databaseFacade")
public class DatabaseFacade {
	// @Autowired // ����� ������������ �� ��������������, � ������ ������ �� Class, Qualified
	@Resource(name="actionsDao") // ����� ������������ �� ������� 
	private ActionsDao actionsDao;

	public ActionsDao getActionsDao() {
		return actionsDao;
	}

	public void setActionsDao(ActionsDao actionsDao) {
		this.actionsDao = actionsDao;
	}
	
}
