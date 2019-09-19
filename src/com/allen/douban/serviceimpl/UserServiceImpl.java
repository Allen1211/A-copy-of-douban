package com.allen.douban.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import com.allen.douban.bean.UserBean;
import com.allen.douban.dao.FollowDao;
import com.allen.douban.entity.Follow;
import com.allen.douban.factory.DaoFactroy;
import com.allen.douban.dao.UserDao;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.User;
import com.allen.douban.service.UserService;
import com.allen.douban.util.ImageIOUtil;
import com.allen.douban.util.SecurityUtil;


public class UserServiceImpl implements UserService {

	private UserDao userDao = (UserDao) DaoFactroy.getInstance().getDao("UserDao");
	private FollowDao followDao = (FollowDao) DaoFactroy.getInstance().getDao("FollowDao");
	@Override
	public User findUserById(int userId) {
		return userDao.queryUserById(userId);
	}

	@Override
	public String getUserNickname(int userId) {
		User user = userDao.queryUserById(userId);
		if (user == null) {
			return null;
		}
		return user.getNickname();
	}

	@Override
	public Msg login(String userName, String password) {
		// 判空
		if (userName == null || userName.length() == 0) {
			return new Msg(false,"用户名不能为空", null);
		}
		if (password == null || password.length() == 0) {
			return new Msg(false,"密码不能为空", null);
		}
		User user = userDao.queryUserByName(userName);
		if (user == null) {
			return new Msg(false,"用户名不存在", null);
		}	
		String pwdStr = SecurityUtil.encodeMD5(password);
		if (!pwdStr.equals(user.getUserPassword())) {
			return new Msg(false,"密码错误", null);
		}
		/**
		 * 查询该用户的权限
		 */
		int userId = user.getUserId();
		List<Map<String, Object>> access = userDao.queryUserAccessById(userId, null);
		List<String> accessURLS = new ArrayList<>();
		for (int i = 0; i < access.size(); i++) {
			String[] urls = ((String) access.get(i).get("accessUrls")).split(",");
			for (String url : urls) {
				accessURLS.add(url);
			}
		}
		// 创建Bean对象
		UserBean userBean = new UserBean(user, accessURLS);
		return new Msg(true,"登录成功", userBean);
	}

	public Msg regist(String userName, String password, String confirmPassword, String email, String nickname) {
		/**
		 * 判空
		 */
		if (userName == null || userName.length() == 0) {
			return new Msg(false,"用户名不能为空", null);
		}
		if (password == null || password.length() == 0) {
			return new Msg(false,"密码不能为空", null);
		}
		if (confirmPassword == null || confirmPassword.length() == 0) {
			return new Msg(false,"确认密码不能为空", null);
		}
		if (email == null || email.length() == 0) {
			return new Msg(false,"邮箱不能为空", null);
		}
		if (nickname == null || nickname.length() == 0) {
			return new Msg(false,"昵称不能为空", null);
		}
		// 密码确认
		if (!password.equals(confirmPassword)) {
			return new Msg(false,"两次输入密码不一致", null);
		}
		/**
		 * 正则格式判断
		 */
		Pattern p1 = Pattern.compile("^\\w{6,20}$"); // 用户名只能是3-10位的字母、数字或下划线
		Pattern p2 = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$"); // 密码必须为6-12位，大小写字母和数字的组合(必须包含)
		Pattern p3 = Pattern.compile("^\\w+@{1}\\w+\\.{1}\\w+$"); // 邮箱格式
		Pattern p4 = Pattern.compile("^.*{1,10}$");
		if (!p1.matcher(userName).matches()) {
			return new Msg(false,"请输入合法的用户名", null);
		}
		if (!p2.matcher(password).matches()) {
			return new Msg(false,"请输入合法的密码", null);
		}
		if (!p3.matcher(email).matches()) {
			return new Msg(false,"请输入合法的邮箱", null);
		}
		if (!p4.matcher(nickname).matches()) {
			return new Msg(false,"请输入合法的昵称", null);
		}
		if (userDao.queryUserByName(userName) != null) {
			return new Msg(false,"用户名已被注册", null);
		}
		/**
		 * 注册
		 */
		User user = new User();
		user.setUserName(userName);
		user.setUserPassword(SecurityUtil.encodeMD5(password));
		user.setUserEmail(email);
		user.setNickname(nickname);
		Msg msg = userDao.addUser(user);
		// 数据库插入错误
		if (!msg.isSuccess()) {
			return new Msg(false,"数据插入错误", null);
		}
		return new Msg(true,"注册成功", null);
	}

	@Override
	public Msg editUserInfo(User user, String img, String nickname, String email, String desc) {
		if (email == null || email.length() == 0) {
			return new Msg(false,"邮箱不能为空", null);
		}
		if (nickname == null || nickname.length() == 0) {
			return new Msg(false,"昵称不能为空", null);
		}
		if (desc == null || desc.length() == 0) {
			return new Msg(false,"个人简介不能为空", null);
		}
		Pattern p3 = Pattern.compile("^\\w+@{1}\\w+\\.{1}\\w+$"); // 邮箱格式
		Pattern p4 = Pattern.compile("^.*{1,10}$");
		if (!p3.matcher(email).matches()) {
			return new Msg(false,"请输入合法的邮箱", null);
		}
		if (!p4.matcher(nickname).matches()) {
			return new Msg(false,"请输入合法的昵称", null);
		}
		if (!img.equals("")) {
			String path = "e:\\Java\\Project\\douban\\WebContent\\img\\user\\" + user.getUserId() + "\\head.jpg";
			Boolean isImageSave = ImageIOUtil.base64ToImage(img, path);
			if (!isImageSave) {
				return new Msg(false,"图片上传失败", null);
			}
			user.setUserImage(path);
		}
		user.setNickname(nickname);
		user.setUserEmail(email);
		user.setUserDesc(desc);
		Msg msg = userDao.editUser(user);
		if (msg.isSuccess()) {
			return new Msg(true,"更新成功", user);
		} else {
			return new Msg(false,"用户数据更新失败", null);
		}
	}



}
