package com.allen.douban.serviceimpl;

import java.sql.SQLException;
import java.util.Date;

import com.allen.douban.dao.CollectDao;
import com.allen.douban.factory.DaoFactroy;
import com.allen.douban.dao.ForwardDao;
import com.allen.douban.dao.LikeDao;
import com.allen.douban.entity.Msg;
import com.allen.douban.service.ActionService;

public class ActionServiceImpl implements ActionService {
	LikeDao likeDao = (LikeDao) DaoFactroy.getInstance().getDao("LikeDao");
	CollectDao collectDao = (CollectDao) DaoFactroy.getInstance().getDao("CollectDao");
	ForwardDao forwardDao = (ForwardDao) DaoFactroy.getInstance().getDao("ForwardDao");

	@Override
	public Msg doLike(int targetId, int userId, int likeType) {
		try {
			if (likeDao.isLiked(targetId, userId, likeType)) {
				likeDao.cancelLike(targetId, userId, likeType);
			} else {
				likeDao.addLike(targetId, userId, likeType, new Date());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Msg(false,"点赞失败");
		}
		return new Msg(true,"点赞成功");
	}

	@Override
	public Msg doCollect(int articleId, int userId) {
		try {
			if (collectDao.isCollected(articleId, userId)) {

			} else {
				collectDao.addCollect(articleId, userId, new Date());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Msg(false,"收藏失败");
		}
		return new Msg( true,"收藏成功");
	}

	@Override
	public Msg doForward(int articleId, int userId) {
		try {
			if (forwardDao.isForwarded(userId,articleId)) {
				
			} else {
				forwardDao.addForward(userId, articleId, new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Msg(false,"转发失败");
		}
		return new Msg(true,"转发成功" );
	}

	@Override
	public boolean isLiked(int targetId,int userId, int likeType) {
		return likeDao.isLiked(targetId, userId, likeType);
	}

	@Override
	public int getLikeCount(int targetId, int likeType) {
		return likeDao.getLikeCount(targetId, likeType);
	}

}
