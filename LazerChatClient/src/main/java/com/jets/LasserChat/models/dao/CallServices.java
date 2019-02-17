package com.jets.LasserChat.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

public interface CallServices
{
	void startVoiceCall(User toUser);
	void endVoiceCall(User toUser);
	void startVedioCall(User toUser);
	void endVedioCall(User toUser);
}
