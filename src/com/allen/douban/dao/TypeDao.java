package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.allen.douban.entity.Type;

public class TypeDao extends BaseDao {
	public List<Type> queryAllType() {
		String sql = "SELECT * FROM type ORDER BY type_id";
		return queryMutiple(sql, null, null, Type.class);
	}
	
	public List<Type> queryTypeByArticleId(int articleId){
		String sql = "SELECT type.type_id,type_name FROM type "
				+ "JOIN article_type ON article_type.type_id=type.type_id WHERE article_type.article_id=? ORDER BY type.type_id";
		List<Object> params = new ArrayList<>();
		params.add(articleId);
		return queryMutiple(sql, params, null, Type.class);
	} 
	
	public void addArticleTypeMapping(int[] typeId,int articleId) throws Exception{
		String sql = "INSERT INTO article_type(article_id,type_id) VALUES(?,?)";
		
		List<Object> params = new ArrayList<>();
		for (int i : typeId) {
			params.clear();
			params.add(articleId);
			params.add(i);
			try {
				update(sql,params);
			} catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public void deleteArticleTypeMapping(int articleId) throws SQLException {
		String sql = "DELETE FROM article_type WHERE article_id=?";
		update(sql,getParams(articleId));
		
	}
	
	
}
