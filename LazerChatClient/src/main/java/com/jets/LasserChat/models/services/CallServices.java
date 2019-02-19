package com.jets.LasserChat.models.services;

import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;

public interface CallServices extends Remote
{
	void startVoiceCall(User toUser);
	void endVoiceCall(User toUser);
	void startVedioCall(User toUser);
	void endVedioCall(User toUser);
}
