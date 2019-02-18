package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.GroupCategory;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContactServices extends Remote
{
	
	/* Server side */
	boolean addContact(User newContact) throws RemoteException;
	/* Server side */
	boolean blockContact(User blockContact) throws RemoteException;
	/* Server side */
	void AddContactToGroup(User Contact, GroupCategory groupType) throws RemoteException;
}
