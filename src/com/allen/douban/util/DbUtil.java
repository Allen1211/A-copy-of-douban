package com.allen.douban.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 连接数据库的工具类
 * 
 */
public class DbUtil {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/douban?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	/**
	 * 加载jdbc驱动
	 */
	public static void loadDriver() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 取得对数据库的连接
	 * @return	
	 */
	public static Connection getConn() {
		loadDriver();
		Connection conn = threadLocal.get();
		try {
			if(conn == null) {
				conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
				threadLocal.set(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭打开的资源
	 * @param rs	查询结果集
	 * @param conn	连接
	 * @param pstmt	预编译sql语句
	 */
	public static void release(ResultSet rs, Connection conn, PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				//如果无法得到conn对象，说明事务处理已结束或者不在事务中，可以正常关闭
				if(threadLocal.get() == null) {
					conn.close();
				}else {
					//还在事务中，忽略掉，让事务去关闭连接
				}
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 事务开启
	 */
	public static void beginTransaction() {
		Connection conn = getConn();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 提交事务
	 */
	public static void commitTransaction() {
		Connection conn = threadLocal.get();
		try {
			conn.commit();
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadLocal.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction() {
		Connection conn = threadLocal.get();
		try {
			conn.rollback();
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadLocal.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 关闭连接池
	 */
	public static void closePool() {
		try {
			threadLocal.get().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadLocal.remove();
		
	}
	
}
