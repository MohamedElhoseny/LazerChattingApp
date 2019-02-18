package com.jets.LasserChat.models.dao;

import java.io.File;
import java.rmi.Remote;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.jets.LazerChatCommonService.models.entity.User;

public interface FileServices extends Remote
{
	/* The same concept of sending messages */
	void receiveFile(User fromUser, File senderFile,
					 RemoteInputStream ristream, String name, String extension);
}
