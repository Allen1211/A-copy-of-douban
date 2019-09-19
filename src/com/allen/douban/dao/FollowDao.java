package com.allen.douban.dao;

import com.allen.douban.bean.FriendBean;
import com.allen.douban.entity.Follow;
import com.allen.douban.entity.User;
import com.allen.douban.factory.DaoFactroy;

import java.sql.SQLException;
import java.util.List;

public class FollowDao extends BaseDao{
    public List<FriendBean> queryUserAllFriendId(int userId){
        String sql = "SELECT user_id, nickname FROM `user` JOIN `follow` ON user_id=to_user_id WHERE from_user_id=?";
        return queryMutiple(sql, getParams(userId),null, FriendBean.class);
    }

    public List<FriendBean> queryUserAllFollowerId(int userId){
        String sql = "SELECT user_id, nickname FROM `user` JOIN `follow` ON user_id=from_user_id WHERE to_user_id=?";
        return queryMutiple(sql,getParams(userId),null,FriendBean.class);
    }

    public boolean isFollowed(int fromUserId, int toUserId){
        String sql = "SELECT COUNT(*) FROM `follow` WHERE from_user_id=? AND to_user_id=?";
        return queryCount(sql,getParams(fromUserId,toUserId))>0;
    }

    public void addFollow(int fromUserId, int toUserId) throws SQLException {
        String sql = "INSERT INTO follow(from_user_id, to_user_id) VALUE(?,?)";
        update(sql,getParams(fromUserId,toUserId));
    }
    public void cancelFollow(int fromUserId, int toUserId) throws SQLException {
        String sql = "DELETE FROM follow WHERE from_user_id=? AND to_user_id=?";
        update(sql,getParams(fromUserId,toUserId));
    }
//    public static void main(String[] args) {
//        FollowDao dao = (FollowDao) DaoFactroy.getInstance().getDao("FollowDao");
//        List<Integer> list = dao.queryUserAllFriendId(1);
//        for(Integer i:list){
//            System.out.println(i);
//        }
//    }
}
