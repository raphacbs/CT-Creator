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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author automacao
 */
public class StepDAO {

    private final static Logger logger = LogManager.getLogger(StepDAO.class.getName());
    private final EnumConnection BD = MSSQL;

    public Step getById(int id) {
        Step step = new Step();
        String SQL_SELECT_BY_ID = "SELECT [Id], "
                + "[NomeStep],"
                + " [DescStep],"
                + " [ResultadoStep],"
                + " [Ordem], "
                + "[IdTesteCaseTSBean]"
                + " FROM [CTCreatorDB].[dbo].[Step] "
                + "WHERE Id = ? ";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                step.setId(rs.getInt("Id"));
                step.setDescStep(rs.getString("DescStep"));
                step.setNomeStep(rs.getString("NomeStep"));
                step.setResultadoStep(rs.getString("ResultadoStep"));
                step.setIdTesteCaseTSBean(rs.getInt("IdTesteCaseTSBean"));
                step.setOrdem(rs.getInt("Ordem"));

            }
            rs.close();
            ps.close();
            return step;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<Step> getByTestCaseBean(int idTestCaseBean) {
        List<Step> steps = new ArrayList<>();
        String SQL_SELECT_BY_ID = "SELECT [Id], "
                + "[NomeStep],"
                + " [DescStep],"
                + " [ResultadoStep],"
                + " [Ordem], "
                + "[IdTesteCaseTSBean]"
                + " FROM [CTCreatorDB].[dbo].[Step] "
                + "WHERE IdTesteCaseTSBean = ? ORDER BY [Ordem] ASC";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idTestCaseBean);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Step step = new Step();
                step.setId(rs.getInt("Id"));
                step.setDescStep(rs.getString("DescStep"));
                step.setNomeStep(rs.getString("NomeStep"));
                step.setResultadoStep(rs.getString("ResultadoStep"));
                step.setIdTesteCaseTSBean(rs.getInt("IdTesteCaseTSBean"));
                step.setOrdem(rs.getInt("Ordem"));

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

    
    
    public Step update(Step step) {
        String SQL_UPDATE_BY_ID = "UPDATE [CTCreatorDB].[dbo].[Step] SET "
                + "[NomeStep] = ? ,"
                + "[DescStep] = ?, "
                + "[ResultadoStep] = ?, "
                + "[Ordem] = ?, "
                + "[IdTesteCaseTSBean] = ? "
                + "WHERE Id = ?";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareCall(SQL_UPDATE_BY_ID);
            ps.setString(1, step.getNomeStep());
            ps.setString(2, step.getDescStep());
            ps.setString(3, step.getResultadoStep());
            ps.setInt(4, step.getOrdem());
            ps.setInt(5, step.getIdTesteCaseTSBean());
            ps.setInt(6, step.getId());

            int row = ps.executeUpdate();
            ps.close();
            if (row <= 1) {
                return step;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            return null;
        }

    }

    public Step insert(Step step) {
        String SQL_INSERT = "INSERT INTO [CTCreatorDB].[dbo].[Step]"
                + "([NomeStep], [DescStep], [ResultadoStep], [Ordem], [IdTesteCaseTSBean])"
                + "VALUES(?,?,?,?,?)";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, step.getNomeStep());
            ps.setString(2, step.getDescStep());
            ps.setString(3, step.getResultadoStep());
            ps.setInt(4, step.getOrdem());
            ps.setInt(5, step.getIdTesteCaseTSBean());

            int row = ps.executeUpdate();

            if (row <= 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {

                    int id = generatedKeys.getInt(1);
                    ps.close();
                    generatedKeys.close();
                    step.setId(id);
                }else{
                    return null;
                }
                return step;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            return null;
        }

    }

    public boolean delete(int Id) {
        String SQL_DELETE_BY_ID = "DELETE FROM [CTCreatorDB].[dbo].[Step] "
                + "WHERE Id = ?";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareCall(SQL_DELETE_BY_ID);
            ps.setInt(1, Id);

            int row = ps.executeUpdate();
            ps.close();
            if (row == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            return false;
        }

    }

}
