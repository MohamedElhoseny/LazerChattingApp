package com.jets.LazerChatCommonService.models.dao;

import java.io.File;

import com.jets.LazerChatCommonService.models.entity.User;

public interface RestoreChatServices
{
	/* Client side */
	File loadSession(User myUser);
}
