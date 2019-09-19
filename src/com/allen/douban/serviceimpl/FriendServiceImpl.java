package com.allen.douban.serviceimpl;

import com.allen.douban.bean.FriendBean;
import com.allen.douban.dao.FollowDao;
import com.allen.douban.entity.Follow;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.User;
import com.allen.douban.factory.DaoFactroy;
import com.allen.douban.factory.TransactionProxyFactory;
import com.allen.douban.service.FriendService;

import java.sql.SQLException;
import java.util.List;

public class FriendServiceImpl implements FriendService {

    private FollowDao followDao = (FollowDao) DaoFactroy.getInstance().getDao("FollowDao");
    @Override
    public Msg findAllFirendId(int userId) {
        return new Msg(true,followDao.queryUserAllFriendId(userId));
    }
    @Override
    public Msg findAllFollowerId(int userId) {
        return new Msg(true,followDao.queryUserAllFollowerId(userId));
    }

    @Override
    public Msg follow(int fromUserId, int toUserId) {
        boolean isSuccess = true;
        try {
            followDao.addFollow(fromUserId,toUserId);
        } catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        }
        if(isSuccess){
            return new Msg(isSuccess,"关注成功");
        }else{
            return new Msg(isSuccess,"关注失败");
        }
    }

    @Override
    public Msg cancelFollow(int fromUserId, int toUserId) {
        boolean isSuccess = true;
        try {
            followDao.cancelFollow(fromUserId,toUserId);
        } catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        }
        if(isSuccess){
            return new Msg(isSuccess,"取消关注成功");
        }else{
            return new Msg(isSuccess,"取消关注失败");
        }
    }

    @Override
    public Msg isFriend(int fromUserId, int toUserId) {
        boolean isFriend = followDao.isFollowed(fromUserId,toUserId);
        return new Msg(isFriend,"是");
    }
}
