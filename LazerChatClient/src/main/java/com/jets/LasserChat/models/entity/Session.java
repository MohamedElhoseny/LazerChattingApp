package com.jets.LasserChat.models.entity;

import java.util.ArrayList;
import java.util.List;

import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

public class Session
{
	private static int uuId = 0;
	private int id;
	private ArrayList<User> sessionUsers;
	private ArrayList<Message> sessionMessages;
	
	public Session()
	{
		//generate uuId
		uuId++;

		this.sessionMessages = new ArrayList<>();
		this.sessionUsers = new ArrayList<>();
		this.id = uuId;
	}
	public Session(int id, ArrayList<User> availableUsers, ArrayList<Message> sessionMessages)
	{
		super();
		this.id = id;
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
	public void setAvailableUsers(ArrayList<User> availableUsers)
	{
		this.sessionUsers = availableUsers;
	}
	public void setSessionMessages(ArrayList<Message> sessionMessages)
	{
		this.sessionMessages = sessionMessages;
	}
	public List<User> getAvailableUsers()
	{
		return sessionUsers;
	}
	public List<Message> getSessionMessages() {return sessionMessages;}
	public void addMessageToSession(Message newMessage)
	{
		this.sessionMessages.add(newMessage);
	}


	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/***************** Displaying Session Data ********************/ \n");
		stringBuilder.append("[SESSION] Number of messages in this session = ").append(sessionMessages.size()).append("\n");
		stringBuilder.append("[SESSION] People participated in this session : ").append(sessionUsers).append("\n");
		stringBuilder.append("[SESSION Messages] : ").append(sessionMessages).append("\n");
		for (Message m: sessionMessages)
			stringBuilder.append("Message send by [").append(m.getUser().getName()).append("] : ").append(m.getMessageString()).append("\n");
		stringBuilder.append("/***************** End of session Data ********************/");

		return stringBuilder.toString();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Session){
			Session session = (Session) obj;
			return session.getId() == this.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId();
	}
}
