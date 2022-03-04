package com.selenium.connectors;

import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.Reporter;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnector {

    Connection c = null;
    Statement stmt = null;

    public DataBaseConnector() {
        openConnection();
    }

    public void openConnection()  {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(JsonConnector.getConfig("dbConnectionStringStaging"),
                            JsonConnector.getConfig("dbUserName"),
                            JsonConnector.getConfig("dbPassword"));
            System.out.println("Connection established successfully");
        }
       catch (Exception e){
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
       }
    }

    public String getSingleStringResult(String query){
        String id = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( query);
            while ( rs.next() ) {
             id = rs.getString("id");
             System.out.println( "ID = " + id );
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Query executed successfully");
        return id;
    }

    public ArrayList getListStringResultSingleColumn(String query,String columnName){
        ArrayList<String> values = new ArrayList<>();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( query);
            while ( rs.next() ) {
                values.add(rs.getString(columnName));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Query executed successfully");
        return values;
    }

    public void closeConnection()  {
        try {
            stmt.close();
            c.close();
        }catch (SQLException e){
            Reporter.log("SQL exception is thrown "+e.getMessage());
            Assert.assertTrue(false,"Failed due to sql exception "+e.getMessage());
        }
    }

}
