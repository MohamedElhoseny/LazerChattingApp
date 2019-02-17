package com.jets.LasserChat.models.entity;

import java.util.List;

import com.jets.LazerChatCommonService.models.entity.User;

public class Session
{
	private int uuId; //for search
	private List<User> availableUsers;
	private List<Message> sessionMessages;
	
	public Session()
	{
		//generate uuId
	}
	public Session(int uuId, List<User> availableUsers, List<Message> sessionMessages)
	{
		super();
		this.uuId = uuId;
		this.availableUsers = availableUsers;
		this.sessionMessages = sessionMessages;
	}
	public int getUuId()
	{
		return uuId;
	}
	public void setUuId(int uuId)
	{
		this.uuId = uuId;
	}
	public List<User> getAvailableUsers()
	{
		return availableUsers;
	}
	public void setAvailableUsers(List<User> availableUsers)
	{
		this.availableUsers = availableUsers;
	}
	public List<Message> getSessionMessages()
	{
		return sessionMessages;
	}
	public void setSessionMessages(List<Message> sessionMessages)
	{
		this.sessionMessages = sessionMessages;
	}

}
