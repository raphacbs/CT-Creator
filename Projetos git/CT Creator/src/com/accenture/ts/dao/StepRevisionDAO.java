/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.Step;
import com.accenture.connection.ConnectionFactory;
import com.accenture.connection.EnumConnection;
import static com.accenture.connection.EnumConnection.MSSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author automacao
 */
public class StepRevisionDAO {
    private final static Logger logger = LogManager.getLogger(StepInstanceDAO.class.getName());
    private final EnumConnection BD = MSSQL;
    
    
    public List<Step> getByTestCaseBean(int idTestCaseBean, int revision) {
        List<Step> steps = new ArrayList<>();
        
      String SQL_SELECT_BY_ID =  "SELECT TOP 1000 [IdStepRevision]" +
                            "      ,sr.[Id]" +
                            "      ,[NomeStep]" +
                            "      ,[DescStep]" +
                            "      ,[ResultadoStep]" +
                            "      ,[Ordem]" +
                            "      ,[IdTesteCaseTSBean]" +
                            "      ,sr.[IdRevision]" +
                            "  FROM [CTCreatorDB].[dbo].[StepRevision] sr" +
                            "  INNER JOIN [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision] tcr on tcr.[IdTesteCaseTSBeanRevision] = sr.IdTesteCaseTSBean"
                                            + " where tcr.Id = ? and tcr.IdRevision = ? ORDER BY [Ordem] ASC";

        
        
        try {
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
            return null;
        }

    }
    
}