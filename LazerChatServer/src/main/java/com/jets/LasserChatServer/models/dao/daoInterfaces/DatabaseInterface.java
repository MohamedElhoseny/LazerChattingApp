package com.jets.LasserChatServer.models.dao.daoInterfaces;

import java.util.ArrayList;

public interface DatabaseInterface
{
	void insert(String table, String attributes, String values);

	void delete(String table, int objectId);

	void update(String table, String options, int objectId, String whereStatement);

	void update(String table, String attr, String values, String Where);

	ArrayList<Object[]> select(String table);

	ArrayList<Object[]> select(String table, String options);

	ArrayList<Object[]> select(String table, String options, String whereStatement);

	ArrayList<Object[]> select(String table, String options, int userID);
}
