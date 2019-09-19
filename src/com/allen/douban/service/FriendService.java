package com.allen.douban.service;

import com.allen.douban.entity.Msg;

public interface FriendService {
    Msg findAllFirendId(int userId);
    Msg findAllFollowerId(int userId);
    Msg follow(int fromUserId, int toUserId);
    Msg cancelFollow(int fromUserId, int toUserId);
    Msg isFriend(int fromUserId, int toUserId);
}
