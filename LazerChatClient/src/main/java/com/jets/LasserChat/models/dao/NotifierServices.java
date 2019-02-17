package com.jets.LasserChat.models.dao;

import java.io.File;

import com.jets.LazerChatCommonService.models.entity.User;

/**
 * Description : called from the other friend side to update all his friends
 * 				(Looping through friend list and call notifyStatus)
 * */
public interface NotifierServices
{
	/* Notify user about a new user message, for notifying only */
	void notifyMessage(User fromUser);
	/* Notify user about a friend status invoke by his friend */
	void notifyStatus(User fromUserStatus);
	/* Notify user only about retrieval of a new file */
	void notifyFileRequest(User fromUser, File senderFile);
	/* Notify user only about receive a new friend request */
	void notifyFriendRequest(User fromUser);
}
