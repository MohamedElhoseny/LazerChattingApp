package com.jets.LasserChat.models.services;

import com.jets.LazerChatCommonService.models.entity.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageServices extends Remote
{
	/* Used to received Message to all user list in session all i need whose send 
	 * this message to update view as need */
	void receive(Message receivedMessage) throws RemoteException;
}
