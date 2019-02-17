package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

public interface UserServices
{
	enum SocialType {FACEBOOK, TWITTER, GMAIL};
	
	/* server side */
	User logIn(User user);
	/* Server side */
	void logOut(User user);
	/* Server side */
	boolean register(User user);
	/* Server side */
	boolean forgetPassword(String email);
	/* Server side */
	boolean updateProfile(User user);
	/* Server side */
	User socialLogin(SocialType type);
	/* Server side 'notify all client handshaking and also notify server to exit
	 * must loop for all client to notify and then call exit on server
	 * to update my status */
	void exit(User user);
}
