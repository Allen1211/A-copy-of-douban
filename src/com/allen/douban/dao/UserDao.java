package com.allen.douban.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.allen.douban.bean.PageBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.User;
import com.allen.douban.util.DbUtil;

public class UserDao extends BaseDao {


	public Msg addUser(User user) {
		String sql = "INSERT INTO user(user_name,user_password,user_email,nickname,"
				+ "user_status,user_is_admin,user_image,user_desc,user_regist_time) " + "Values(?,?,?,?,?,?,?,?,?)";
		List<Object> params = new ArrayList<>();
		params.add(user.getUserName());
		params.add(user.getUserPassword());
		params.add(user.getUserEmail());
		params.add(user.getNickname());
		params.add(user.getUserStatus());
		params.add(user.getUserIsAdmin());
		params.add(user.getUserImage());
		params.add(user.getUserDesc());
		params.add(user.getUserRegistTime());
		try {
			update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return new Msg("用户数据插入错误", false);
		}
		return new Msg("用户数据插入成功", true);
	}

	public Msg editUser(User user) {
		String sql = "UPDATE user SET nickname=?,user_email=?,user_image=?,user_desc=? WHERE user_id=?";
		List<Object> params = new ArrayList<>();
		params.add(user.getNickname());
		params.add(user.getUserEmail());
		params.add(user.getUserImage());
		params.add(user.getUserDesc());
		params.add(user.getUserId());
		try {
			update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			return new Msg("用户数据更改错误", false);
		}
		return new Msg("用户数据修改成功", true);
	}

	public List<Map<String, Object>> queryUserAccessById(int userId, PageBean pageBean) {
		String sql = "SELECT access_urls FROM view_access WHERE user_id=?";
		return queryMutipleMap(sql, getParams(userId), pageBean);
	}

	public User queryUserByName(String userName) {
		String sql = "SELECT DISTINCT * FROM view_user WHERE user_name =?";
		User user = querySingle(sql, getParams(userName), User.class);
		if (user == null) {
			return null;
		}
		return user;
	}

	public User queryUserById(int userId) {
		String sql = "SELECT DISTINCT * FROM view_user WHERE user_id =?";
		User user = querySingle(sql, getParams(userId), User.class);
		if (user == null) {
			return null;
		}
		return user;
	}


}
