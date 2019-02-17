package com.jets.LasserChat.models.dao;

import java.io.File;

import com.jets.LazerChatCommonService.models.entity.User;

public interface FileServices
{
	/* The same concept of sending messages */
	void receiveFile(User fromUser, File senderFile);
}
