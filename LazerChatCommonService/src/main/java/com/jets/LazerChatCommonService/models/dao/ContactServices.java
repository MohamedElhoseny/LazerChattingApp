package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.GroupCategory;
import com.jets.LazerChatCommonService.models.entity.User;

public interface ContactServices
{
	
	/* Server side */
	boolean addContact(User newContact);
	/* Server side */
	boolean blockContact(User blockContact);
	/* Server side */
	void AddContactToGroup(User Contact, GroupCategory groupType);
}
