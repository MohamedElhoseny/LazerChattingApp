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
	private String gender;
	private String country;
	private String date;
	private String bio;
	private Integer status;
	
	//Constructors
	public User()
	{
		System.out.println("Constructing user object ..");
	}
	public User(int id, String phone, String password, String name, String email, byte[] picture, String gender,
			String country, String date, String bio, Integer status)
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
	public String getGender()
	{
		return gender;
	}
	public void setGender(String gender)
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
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}

	@Override
	public boolean equals(Object obj)
	{
		User anotherUser = (User) obj;
		if (anotherUser != null)
			return anotherUser.getId() == anotherUser.getId();
		else
			return false;
	}

//	@Override
//	public boolean equals(Object obj) {
//		return super.equals(obj);
//	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "User [ id = "+id+", name = "+name+" ]";
	}
}
