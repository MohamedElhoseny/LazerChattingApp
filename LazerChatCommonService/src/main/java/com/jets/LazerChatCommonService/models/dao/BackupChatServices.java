package com.jets.LazerChatCommonService.models.dao;

import java.io.File;

import com.jets.LazerChatCommonService.models.entity.User;

public interface BackupChatServices
{
	/* Server side */
	void saveSession(User myUser, File sessionXmlFile);
}
