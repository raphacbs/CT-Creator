/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.connection.ConnectionFactory;
import static com.accenture.connection.EnumConnection.MSSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author automacao
 */
public class RevisionDAO {

    private final static Logger logger = Logger.getLogger(RevisionDAO.class);

    public RevisionDAO() {
    }

    public int insert(Object obj) throws SQLException {
        ConnectionFactory cf = null;
        PreparedStatement ps = null;
        try {
            String databasename = SVNPropertiesVOBean.getInstance().getDatabaseNameBD();
            String INSERT_REVISION = "INSERT INTO "+databasename+".[dbo].[Revision] ([CreatedAt], [EntityType]) VALUES (?,?)";
            cf = new ConnectionFactory(MSSQL);
            
            
            ps = cf.getConnection().prepareStatement(INSERT_REVISION, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
            ps.setString(2, obj.getClass().getName());
            cf.getConnection().setAutoCommit(false);
            int row = ps.executeUpdate();
            cf.getConnection().commit();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int revision = generatedKeys.getInt(1);                
                generatedKeys.close();
                return revision;
            }

            if (row <= 0) {
                logger.error("Erro ao criar uma nova revisão. Para o objeto " + obj.getClass().getName());

                return 0;
            }

        } catch (Exception ex) {
            cf.getConnection().rollback();
            ex.printStackTrace();
            logger.error(ex);
        } finally {          
            ps.close();
            cf.getConnection().close();
        }

        return 0;
    }
    
    

}
