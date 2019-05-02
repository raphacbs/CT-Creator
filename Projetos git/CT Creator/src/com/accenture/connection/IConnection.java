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
public interface IConnection {
    
    public Connection getConnection();
    
}
