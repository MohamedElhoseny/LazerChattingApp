package com.jets.LasserChat.models.entity;

import java.util.ArrayList;
import java.util.List;

import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

public class Session
{
	private static int uuId = 0;
	private int id;
	private List<User> sessionUsers;
	private List<Message> sessionMessages;
	
	public Session()
	{
		//generate uuId
		id = ++uuId;
		sessionMessages = new ArrayList<>();
		sessionUsers = new ArrayList<>();
	}
	public Session(int uuId, List<User> availableUsers, List<Message> sessionMessages)
	{
		super();
		this.uuId = uuId;
		this.sessionUsers = availableUsers;
		this.sessionMessages = sessionMessages;
	}


	public void addParticipant(User user){
		this.sessionUsers.add(user);
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public List<User> getAvailableUsers()
	{
		return sessionUsers;
	}
	public void setAvailableUsers(List<User> availableUsers)
	{
		this.sessionUsers = availableUsers;
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
