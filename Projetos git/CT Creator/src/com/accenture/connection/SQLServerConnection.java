/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.connection;

import com.accenture.bean.SVNPropertiesVOBean;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author automacao
 */
public class SQLServerConnection implements IConnection{

    private static String connectionUrl = "jdbc:sqlserver://<server>:<port>;databaseName=AdventureWorks;user=<user>;password=<password>";
    private static SQLServerConnection instance;
    private SVNPropertiesVOBean config;
    private static Connection con;
    
    private SQLServerConnection(){
        try {
            config = new SVNPropertiesVOBean();
        } catch (IOException ex) {
            Logger.getLogger(SQLServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static synchronized SQLServerConnection getInstance(){
        if(instance == null){
            instance = new SQLServerConnection();
        }
        return instance;
    }
    
    
    @Override
    public Connection getConnection() {
       try
	{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	}
	catch(java.lang.ClassNotFoundException e)
	{
            System.err.print("ClassNotFoundException: ");
	    System.err.println(e.getMessage());
	}
       
       SQLServerDataSource ds = new SQLServerDataSource();
       ds.setServerName(config.getHostBD());
       ds.setPortNumber(Integer.parseInt(config.getPortBD())); 
       ds.setDatabaseName(config.getDatabaseNameBD());
       ds.setUser(config.getUserBD());
       ds.setPassword(config.getPasswordBD());
       
       try{
           if(con == null || con.isClosed())
                con = ds.getConnection();
           
         return con;
       }catch(SQLException e){
           try { 
               throw new Exception("Não possível conectar ao banco de dados, favor verificar a sua conexão");
           } catch (Exception ex) {
               Logger.getLogger(SQLServerConnection.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
      return null;
    }
        
 }
    

