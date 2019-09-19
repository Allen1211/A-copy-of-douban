package com.allen.douban.dao;

import com.allen.douban.bean.FriendBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.util.DbUtil;
import com.allen.douban.util.TfUtil;
import com.mysql.jdbc.ResultSetMetaData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 83780
 *
 */
public class BaseDao {

	protected void update(String sql, List<Object> params) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update DB error");
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.release(null, conn, pstmt);
		}

	}

	protected <T> T querySingle(String sql, List<Object> params, Class<T> cls) {
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			//执行查询
			rs = pstmt.executeQuery();
			if(rs == null) {
				return null;
			}
			//获得查询结果的列属性（列字段名,列数等)
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			
			if (rs.next()) {
				//通过反射获取实体类的实例化对象
				T result = cls.newInstance();
				//对于每一列
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					//得到每一列的列名
					String colName = rsmd.getColumnName(i + 1);
					Object value = rs.getObject(colName);
					//把下划线命名转变为驼峰命名，并由此得到对于实体类的成员变量域,给每一个变量赋值
					Field field = cls.getDeclaredField(TfUtil.UnderlineToHump(colName));
					field.setAccessible(true);
					field.set(result, value);
				}
				return result;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("BaseDaoQuery: 数据库查询异常");
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			System.out.println("BaseDaoQuery: 无法找到entity属性异常");
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			System.out.println("BaseDaoQuery: 无成员访问权限");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("BaseDaoQuery: 未知异常");
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return null;
	}
	
	protected Map<String,Object> querySingleMap(String sql,List<Object> params){
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,Object> result = new HashMap<>();

		try {
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

			if (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					//将列名转变为驼峰命名，然后作为该列的key，value为该列的数据
					result.put(TfUtil.UnderlineToHump(rsmd.getColumnName(i)), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			System.out.println("BaseDaoQuery(mutiple):数据查询异常");
			e.printStackTrace();
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return result;
	}
	
	protected <T> List<T> queryMutiple(String sql, List<Object> params, PageBean pageBean, Class<T> cls) {
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> resultList = new ArrayList<>();		//将结果保存在一个List里
		String limitSQL = addLimitForSQL(sql, params, pageBean);
		try {
			pstmt = conn.prepareStatement(limitSQL);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			//执行查询
			rs = pstmt.executeQuery();
			//获得查询结果的列属性（列字段名,列数等)
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

			while (rs.next()) {
				//通过反射获取实体类的实例化对象
				T obj = cls.newInstance();
				//对于每一列
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					//得到每一列的列名
					String colName = rsmd.getColumnName(i + 1);
					Object value = rs.getObject(colName);
					//把下划线命名转变为驼峰命名，并由此得到对于实体类的成员变量域,给每一个变量赋值
					Field field = cls.getDeclaredField(TfUtil.UnderlineToHump(colName));
					field.setAccessible(true);
					field.set(obj, value);
				}
				resultList.add(obj);
			}
		} catch (SQLException e) {
			System.out.println("BaseDaoQuery: 数据库查询异常");
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			System.out.println("BaseDaoQuery: 无法找到entity属性异常");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("BaseDaoQuery: 类实例化异常");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("BaseDaoQuery: 未知异常");
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return resultList;
	}

	protected <T> List<T> queryColumn(String sql, List<Object> params, PageBean pageBean, Class<T> cls) {
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> resultList = new ArrayList<>();		//将结果保存在一个List里
		String limitSQL = addLimitForSQL(sql, params, pageBean);
		try {
			pstmt = conn.prepareStatement(limitSQL);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			//执行查询
			rs = pstmt.executeQuery();
			//获得查询结果的列属性（列字段名,列数等)
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

			String colName = rsmd.getColumnName(1);
			while (rs.next()) {
				T value = (T)(rs.getObject(colName));
				resultList.add(value);
			}
		} catch (SQLException e) {
			System.out.println("BaseDaoQuery: 数据库查询异常");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("BaseDaoQuery: 未知异常");
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return resultList;
	}
	
	public List<Map<String, Object>> queryMutipleMap(String sql, List<Object> params , PageBean pageBean) {
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> resultList = new ArrayList<>();	//结果的每一行对于List里面的每一个Map.
		String limitSQL = addLimitForSQL(sql, params, pageBean);
		try {
			pstmt = conn.prepareStatement(limitSQL);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					//将列名转变为驼峰命名，然后作为该列的key，value为该列的数据
					map.put(TfUtil.UnderlineToHump(rsmd.getColumnName(i)), rs.getObject(i));
				}
				resultList.add(map);
			}
		} catch (SQLException e) {
			System.out.println("BaseDaoQuery(mutiple):数据查询异常");
			e.printStackTrace();
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return resultList;
	}
	
	protected int queryCount(String sql , List<Object> params) {
//		String countSql = "SELECT COUNT(*) FROM (" + sql + ") a";
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return count;
	}
	
	/**
	 * 获得指定数据表的下一个自增值
	 * @param dataBaseName
	 * @param tableName
	 * @return
	 */
	protected int getAutoIncrement(String dataBaseName,String tableName) {
		String sql = "SELECT Auto_increment FROM information_schema.`TABLES` WHERE Table_Schema=? AND table_name=?";
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dataBaseName);
			pstmt.setString(2, tableName);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		return result;
	}
	
	protected List<Object> getParams(Object ...args){
		List<Object> params = new ArrayList<>();
		for (Object obj : args) {
			params.add(obj);
		}
		return params;
	}
	/**
	 * 为传入的SQL语句末尾拼接上 limit from,size.用于分页
	 * @param sql
	 */
	private String addLimitForSQL(String sql , List<Object> params, PageBean pageBean) {
		if(pageBean == null) {
			return sql;
		}
		String countSQL = "SELECT COUNT(*) FROM ("+sql+") a";
		Connection conn = DbUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int total = 0;
		try {
			pstmt = conn.prepareStatement(countSQL);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.release(rs, conn, pstmt);
		}
		pageBean.setTotalSize(total);
		int from = (pageBean.getCurrentPage() - 1) * pageBean.getRowPerPage() ;
		int size = pageBean.getRowPerPage();
		if(from + size > total) {
			size = total - from;
		}
		String limitSql = sql + " "  + "LIMIT " + from +"," + size;
		return limitSql;
	}
	
}
