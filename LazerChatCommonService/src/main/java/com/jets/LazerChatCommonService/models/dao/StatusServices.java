package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatusServices extends Remote
{	/*server side */
	/**
	 * Server update my status in db and then 
	 * Client has responsibility to use Notifier interface to call notifyStatus
	 * to update his statue to friends
	 * */
	boolean setUserStatus(User myUser) throws RemoteException;
}
