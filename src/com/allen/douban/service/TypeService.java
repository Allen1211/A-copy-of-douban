package com.allen.douban.service;

import java.util.List;

import com.allen.douban.entity.Type;

public interface TypeService {
	List<Type> findAllType();

	List<Type> findSelectedType(int articleId);
}	
