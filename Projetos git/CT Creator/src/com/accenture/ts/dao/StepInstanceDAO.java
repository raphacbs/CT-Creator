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
import java.sql.Statement;
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
public class StepInstanceDAO {

    private final static Logger logger = LogManager.getLogger(StepInstanceDAO.class.getName());
    private final EnumConnection BD = MSSQL;

    public StepInstanceDAO() {
        try{
         Properties props = new Properties();
            props.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(props);
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
    
    

    public Step getById(int id) {
        Step step = new Step();
        try{
        String SQL_SELECT_BY_ID = "SELECT [Id], "
                + " [NomeStep],"
                + " [DescStep],"
                + " [ResultadoStep],"
                + " [Ordem], "
                + " [IdTesteCaseTSBeanInstance]"
                + " FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[StepInstance] "
                + " WHERE Id = ? ";
        
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                step.setId(rs.getInt("Id"));
                step.setDescStep(rs.getString("DescStep"));
                step.setNomeStep(rs.getString("NomeStep"));
                step.setResultadoStep(rs.getString("ResultadoStep"));
                step.setIdTesteCaseTSBeaninstance(rs.getInt("IdTesteCaseTSBeanInstance"));
                step.setOrdem(rs.getInt("Ordem"));

            }
            rs.close();
            ps.close();
            return step;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao selecionar step instance "+ex.getMessage(),ex);
            return null;
        }

    }

    public List<Step> getByTestCaseBean(int idTestCaseBean) {
        List<Step> steps = new ArrayList<>();
        try{
        String SQL_SELECT_BY_ID = "SELECT [Id], "
                + "[NomeStep],"
                + " [DescStep],"
                + " [ResultadoStep],"
                + " [Ordem], "
                + "[IdTesteCaseTSBeanInstance]"
                + " FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[StepInstance] "
                + "WHERE IdTesteCaseTSBeanInstance = ? ORDER BY [Ordem] ASC";
       
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
                step.setIdTesteCaseTSBeaninstance(rs.getInt("IdTesteCaseTSBeanInstance"));
                step.setOrdem(rs.getInt("Ordem"));
                
                steps.add(step);
            }
            rs.close();
            ps.close();
            return steps;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao selecionar step instance "+ex.getMessage(),ex);
            return null;
        }

    }

    
    
    public Step update(Step step) {
        try{
        String SQL_UPDATE_BY_ID = "UPDATE "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[StepInstance] SET "
                + "[NomeStep] = ? ,"
                + "[DescStep] = ?, "
                + "[ResultadoStep] = ?, "
                + "[Ordem] = ?, "
                + "[IdTesteCaseTSBeanInstance] = ? "
                + "WHERE Id = ?";
        
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
            logger.error("Erro ao atualizar step instance "+ex.getMessage(),ex);
            return null;
        }

    }

    public Step insert(Step step) {
        try{
        String SQL_INSERT = "INSERT INTO "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[StepInstance]"
                + "([NomeStep], [DescStep], [ResultadoStep], [Ordem], [IdTesteCaseTSBeanInstance])"
                + "VALUES(?,?,?,?,?)";
       
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, step.getNomeStep());
            ps.setString(2, step.getDescStep());
            ps.setString(3, step.getResultadoStep());
            ps.setInt(4, step.getOrdem());
            ps.setInt(5, step.getIdTesteCaseTSBeaninstance());

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
            logger.error("Erro ao inserir step instance "+ex.getMessage(),ex);
            return null;
        }

    }

    public boolean delete(int Id) {
        try{
        String SQL_DELETE_BY_ID = "DELETE FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[StepInstance] "
                + "WHERE Id = ?";
        
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
            logger.error("Erro ao deletar step instance "+ex.getMessage(),ex);
            return false;
        }

    }

}
