package com.jets.LazerChatCommonService.models.entity;

import java.io.Serializable;

public class User implements Serializable
{
	//Attributes
	private int id;
	private String phone;
	private String password;
	private String name;
	private String email;
	private byte[] picture;
	private int gender;
	private String country;
	private String date;
	private String bio;
	private String status;
	
	//Constructors
	public User()
	{
		System.out.println("Constructing user object ..");
	}
	public User(int id, String phone, String password, String name, String email, byte[] picture, int gender,
			String country, String date, String bio, String status)
	{
		super();
		this.id = id;
		this.phone = phone;
		this.password = password;
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.gender = gender;
		this.country = country;
		this.date = date;
		this.bio = bio;
		this.status = status;
	}

	//Getter & Setter
	public int getId()
	{
		return id;
	}	
	public void setId(int id)
	{
		this.id = id;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public byte[] getPicture()
	{
		return picture;
	}
	public void setPicture(byte[] picture)
	{
		this.picture = picture;
	}
	public int getGender()
	{
		return gender;
	}
	public void setGender(int gender)
	{
		this.gender = gender;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getBio()
	{
		return bio;
	}
	public void setBio(String bio)
	{
		this.bio = bio;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	
}
