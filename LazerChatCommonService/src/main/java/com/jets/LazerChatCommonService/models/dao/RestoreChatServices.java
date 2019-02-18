package com.jets.LazerChatCommonService.models.dao;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.jets.LazerChatCommonService.models.entity.User;

public interface RestoreChatServices extends Remote
{
	/* Client side ?? server side as user will call it to retrieve backup ed chats */
	File loadSession(User myUser) throws RemoteException;
}
