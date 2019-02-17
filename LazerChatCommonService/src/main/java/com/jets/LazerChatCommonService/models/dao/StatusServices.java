package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

public interface StatusServices
{	/*server side */
	/**
	 * Server update my status in db and then 
	 * Client has responsibility to use Notifier interface to call notifyStatus
	 * to update his statue to friends
	 * */
	void setUserStatus(User myUser);
}
