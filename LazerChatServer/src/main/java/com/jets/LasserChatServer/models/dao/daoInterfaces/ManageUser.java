package com.jets.LasserChatServer.models.dao.daoInterfaces;


import com.jets.LazerChatCommonService.models.entity.User;

public interface ManageUser
{
	void registerUser(User newUser);
	void updateUser(User updatedUser);
}
