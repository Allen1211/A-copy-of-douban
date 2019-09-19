package com.allen.douban.serviceimpl;

import java.util.List;

import com.allen.douban.factory.DaoFactroy;
import com.allen.douban.dao.TypeDao;
import com.allen.douban.entity.Type;
import com.allen.douban.service.TypeService;

public class TypeServiceImpl implements TypeService{

	private TypeDao typeDao = (TypeDao) DaoFactroy.getInstance().getDao("TypeDao");
	
	@Override
	public List<Type> findAllType() {
		TypeDao typeDao = new TypeDao();
		return typeDao.queryAllType();
	}

	@Override
	public List<Type> findSelectedType(int articleId) {
		return typeDao.queryTypeByArticleId(articleId);
	}
	
}
