package com.allen.douban.service;

import com.allen.douban.entity.Msg;

public interface ActionService {
	Msg doLike(int targetId,int userId,int likeType);
	
	Msg doCollect(int articleId,int userId);
	
	Msg doForward(int articleId,int userId);
	
	boolean isLiked(int targetId,int userId,int likeType);
		
	int getLikeCount(int targetId,int likeType);
}
