package com.jets.LazerChatCommonService.models.dao;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.jets.LazerChatCommonService.models.entity.User;

public interface BackupChatServices extends Remote
{
	/* Server side */
	void saveSession(User myUser, File sessionXmlFile) throws RemoteException;
}
