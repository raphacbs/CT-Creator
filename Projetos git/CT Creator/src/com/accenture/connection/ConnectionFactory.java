/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.connection;

import java.sql.Connection;

/**
 *
 * @author automacao
 */
public class ConnectionFactory {
    private EnumConnection typeConnection;

    public ConnectionFactory(EnumConnection typeConnection) {
        this.typeConnection = typeConnection;
    }
    
    
    public Connection getConnection(){
        switch(typeConnection){
            case MSSQL:
                return SQLServerConnection.getInstance().getConnection();
                
            case MYSQL:
                return null;
                
            case ORACLE:
                return null;
                
            default:
                return null;
        }
    }
    
    
}
