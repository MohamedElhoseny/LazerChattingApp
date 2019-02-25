package com.jets.LasserChat.models.services;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

public interface BackupChatServices extends Remote
{
	/* Client side */
	void saveSession(List<Message> messages, File sessionXmlFile) throws RemoteException;
}
