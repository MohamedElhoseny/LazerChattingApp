package com.jets.LasserChat.models.services;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.jets.LazerChatCommonService.models.entity.User;

/**
 * Description : called from the other friend side to update all his friends
 * (Looping through friend list and call notifyStatus)
 */
public interface NotifierServices extends Remote {
	/* Notify user about a new user message, for notifying only */
	void notifyMessage(User fromUser) throws RemoteException;

	/* Notify user about a friend status invoke by his friend */
	void notifyStatus(User fromUserStatus) throws RemoteException;

	/* Notify user only about retrieval of a new file */
	void notifyFileRequest(User fromUser, File senderFile) throws RemoteException;
}