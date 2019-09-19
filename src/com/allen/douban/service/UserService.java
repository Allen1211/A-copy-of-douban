package com.allen.douban.service;

import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.User;

public interface UserService {

	 User findUserById(int userId);
	
	 String getUserNickname(int userId);
	
	 Msg login(String userName, String password);

	 Msg regist(String userName, String password, String confirmPassword, String email, String nickname);
	
	 Msg editUserInfo(User user, String img,String nickname,String email,String desc);

}
