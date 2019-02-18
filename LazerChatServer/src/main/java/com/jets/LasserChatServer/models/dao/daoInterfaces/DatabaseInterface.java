package com.jets.LasserChatServer.models.dao.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseInterface
{
	void insert(String table, String attributes, String values) throws SQLException;

	void delete(String table, int objectId) throws SQLException;

	void update(String table, String options, int objectId, String whereStatement) throws SQLException;

	void update(String table, String attr, String values, String Where) throws SQLException;

	ArrayList<Object[]> select(String table) throws SQLException;

	ArrayList<Object[]> select(String table, String options) throws SQLException;

	ArrayList<Object[]> select(String table, String options, String whereStatement) throws SQLException;
}
