package com.jets.LasserChatServer.models.dao.daoImp;

import java.sql.*;
import java.util.ArrayList;
import com.jets.LasserChatServer.models.dao.daoInterfaces.DatabaseInterface;

public class MysqlDB implements DatabaseInterface
{
    //Attributes
	
	/* ANY CONFIGURATION MUST BE IN XML FILE */
    private static MysqlDB instance;
    private String dbName = "swf2_db";
    private String dbURL = "jdbc:mysql://localhost:3306/" + dbName;
    private String dbUsername = "root";
    private String dbPassword = "12131234";
    
    private Connection connection = null;
    private String query = null;
    private ArrayList<Object[]> resultList = null;


    //Constructor
    private MysqlDB() {
        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getters and Setters
    public static MysqlDB getInstance() {
        //Singleton Class
        if (instance == null) {
            instance = new MysqlDB();
        }

        return instance;
    }

    //Methods
    @Override
    public void insert(String table, String attributes, String values) {
        query = "INSERT INTO " + table + "(" + attributes + ") VALUES(" + values + ")";

        //for checking
        System.out.println(query);

        executeQuery(query, false);
    }
    @Override
    public void delete(String table, int objectId) {
        query = "DELETE FROM " + table + " WHERE ID = " + objectId;

        //for checking
        System.out.println(query);

        executeQuery(query, false);
    }
    @Override
    public void update(String table, String options, int objectId, String whereStatement) {
        query = "UPDATE " + table + " SET " + options + " WHERE ID = " + objectId;

        if(!whereStatement.equals("")) {
            query += " AND " + whereStatement;
        }

        //for checking
        System.out.println(query);

        executeQuery(query, false);
    }
    
    @Override
    public void update(String table, String attr, String values , String Where){
        query = "Update "+table+" SET "+attr+ " = "+values+" Where "+Where;
        System.out.println(query);
        executeQuery(query,false);
    }

    @Override
    public ArrayList<Object[]> select(String table) {
        query = prepareQuery(table, "*", "");

        //for checking
        System.out.println(query);

        executeQuery(query, true);

        return resultList;
    }
    @Override
    public ArrayList<Object[]> select(String table, String options) {
        query = prepareQuery(table, options, "");

        //for checking
        System.out.println(query);

        executeQuery(query, true);

        return resultList;
    }
    @Override
    public ArrayList<Object[]> select(String table, String options, String whereStatement) {
        query = prepareQuery(table, options, whereStatement);

        //for checking
        System.out.println(query);

        executeQuery(query, true);

        return resultList;
    }
    @Override
    public ArrayList<Object[]> select(String table, String options ,int userID ){
        table += " INNER JOIN User_notification on Notification.ID = User_notification.notification_ID WHERE User_notification.User_ID = "+userID;
        query = prepareQuery(table,options,"");
        executeQuery(query, true);
        return resultList;
    }

    //Helper Methods
    private String prepareQuery(String table,String options, String whereStatement) {
        query = "SELECT " + options + " FROM " + table;

        if (!whereStatement.equals("")) {
            query += " WHERE " + whereStatement;
        }

        return query;
    }

    private void executeQuery(String query, Boolean dataRetrieval)
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (dataRetrieval) {
                //only for select statements
                preparedStatement.executeQuery();
                ResultSet results = preparedStatement.getResultSet();
                //extracting objectArrays from ResultSet
                extractResults(results);
                results.close();
            }
            else {
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private void extractResults(ResultSet resultSet) throws SQLException
    {
        resultList = new ArrayList<>();
        while(resultSet.next()) {
            int columns = resultSet.getMetaData().getColumnCount();
            Object[] array = new Object[columns];
            for(int i=0; i < columns; i++){
                array[i] = resultSet.getObject(i+1);
            }
            resultList.add(array);
        }
    }
}