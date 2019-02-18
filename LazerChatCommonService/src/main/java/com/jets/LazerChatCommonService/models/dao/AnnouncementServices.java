package com.jets.LazerChatCommonService.models.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AnnouncementServices extends Remote
{
	/* Client side*/
	void broadcastAnnouncement(String description) throws RemoteException;
}
