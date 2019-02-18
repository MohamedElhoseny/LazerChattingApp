package com.jets.LasserChat.models.dao;

import com.jets.LasserChat.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;

public interface MessageServices extends Remote
{
	/* Used to received Message to all user list in session all i need whose send 
	 * this message to update view as need */
	void receiveMessage(User fromUser, Message newMessage);
}
