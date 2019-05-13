/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import java.util.List;
import com.accenture.bean.SystemBean;
import com.accenture.connection.ConnectionFactory;
import static com.accenture.connection.EnumConnection.MSSQL;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 *
 * @author automacao
 */
public class SystemDAO {
     private final static Logger logger = Logger.getLogger(SystemDAO.class);
     
    private static String SQL_SELECT_ALL = "";
     
    private static String SQL_SELECT_BY_NAME = "";

    public SystemDAO() {
        try{
            SQL_SELECT_ALL = "SELECT [Id],[Descricao] FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[System]  ORDER BY [Descricao] ASC";
            SQL_SELECT_BY_NAME = "SELECT [Id],[Descricao] FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[System] WHERE Descricao = ?  ORDER BY [Descricao] ASC";
         Properties props = new Properties();
            props.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(props);
        }catch(Exception ex){
            ex.printStackTrace();
            logger.error("Erro ao selecionar system "+ex.getMessage(),ex);
            System.err.println(ex.getMessage());
        }
    }
    
    public List<SystemBean> getAll(){
        ConnectionFactory cf = new ConnectionFactory(MSSQL);
        try{
            Statement s = cf.getConnection().createStatement();
            ResultSet rs = s.executeQuery(SQL_SELECT_ALL);
            
            List<SystemBean> systems = new ArrayList<SystemBean>();
                        
            while(rs.next()){
                SystemBean system = new SystemBean();
                
                system.setId(rs.getInt("Id"));
                system.setDescricao(rs.getString("Descricao"));
                
                systems.add(system);
                
            }
            
            return systems;
            
        }catch(Exception ex){
            ex.printStackTrace();
            logger.error("Erro ao executar a query "+SQL_SELECT_ALL+" "+ex.getMessage(),ex);
        }
        return null;
    }
    
    public SystemBean getByName(String system) {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);
        try {
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_BY_NAME);
            ps.setString(1, system);
            ResultSet rs = ps.executeQuery();

            List<SystemBean> systems = new ArrayList<SystemBean>();
            SystemBean sys = new SystemBean();
            while (rs.next()) {

                sys.setId(rs.getInt("Id"));
                sys.setDescricao(rs.getString("Descricao"));

            }

            return sys;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao executar a query "+SQL_SELECT_BY_NAME+" "+ex.getMessage(),ex);
        }
        return null;
    }
}
