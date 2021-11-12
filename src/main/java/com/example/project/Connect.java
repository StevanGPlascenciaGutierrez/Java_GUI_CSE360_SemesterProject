package com.example.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Connect {
    static final String url = "jdbc:sqlite:db/CSE360PROJECT.db";
    Connection conn = null;

    public static Connection Connect(){//Sets connection to database
        try{
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection Established");
            return conn;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void Execute(String stmt){//Executes SQLite Statements
        try(Statement sql = conn.createStatement()){
            sql.execute(stmt);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
