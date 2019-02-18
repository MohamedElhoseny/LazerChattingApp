package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserServices extends Remote
{
	enum SocialType {FACEBOOK, TWITTER, GMAIL};
	
	/* server side */
	User logIn(String phone, String password) throws RemoteException;
	/* Server side */
	void logOut(User user) throws RemoteException;
	/* Server side */
	boolean register(User user) throws RemoteException;
	/* Server side */
	boolean forgetPassword(String email) throws RemoteException;
	/* Server side */
	boolean updateProfile(User user) throws RemoteException;
	/* Server side */
	User socialLogin(SocialType type) throws RemoteException;
	/* Server side 'notify all client handshaking and also notify server to exit
	 * must loop for all client to notify and then call exit on server
	 * to update my status */
	void exit(User user) throws RemoteException;
}
