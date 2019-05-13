/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.Step;
import com.accenture.connection.ConnectionFactory;
import com.accenture.connection.EnumConnection;
import static com.accenture.connection.EnumConnection.MSSQL;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author automacao
 */
public class StepRevisionDAO {
    private final static Logger logger = LogManager.getLogger(StepInstanceDAO.class.getName());
    private final EnumConnection BD = MSSQL;

    public StepRevisionDAO() {
        try{
         Properties props = new Properties();
            props.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(props);
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
    
    
    
    
    public List<Step> getByTestCaseBean(int idTestCaseBean, int revision) {
        List<Step> steps = new ArrayList<>();
        try{
      String SQL_SELECT_BY_ID =  "SELECT TOP 1000 [IdStepRevision]" +
                            "      ,sr.[Id]" +
                            "      ,[NomeStep]" +
                            "      ,[DescStep]" +
                            "      ,[ResultadoStep]" +
                            "      ,[Ordem]" +
                            "      ,[IdTesteCaseTSBean]" +
                            "      ,sr.[IdRevision]" +
                            "  FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[StepRevision] sr" +
                            "  INNER JOIN "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[TesteCaseTSBeanRevision] tcr on tcr.[IdTesteCaseTSBeanRevision] = sr.IdTesteCaseTSBean"
                                            + " where tcr.Id = ? and tcr.IdRevision = ? ORDER BY [Ordem] ASC";

        
        
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idTestCaseBean);
            ps.setInt(2, revision);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Step step = new Step();
                step.setId(rs.getInt("Id"));
                step.setDescStep(rs.getString("DescStep"));
                step.setNomeStep(rs.getString("NomeStep"));
                step.setResultadoStep(rs.getString("ResultadoStep"));
                step.setIdTesteCaseTSBeaninstance(rs.getInt("IdTesteCaseTSBean"));
                step.setOrdem(rs.getInt("Ordem"));
                step.setIdRevision(rs.getInt("IdRevision"));
                
                steps.add(step);
            }
            rs.close();
            ps.close();
            return steps;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao selecionar step revision "+ex.getMessage(),ex);
            return null;
        }

    }
    
}
