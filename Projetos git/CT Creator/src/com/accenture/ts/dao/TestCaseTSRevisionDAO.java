/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SystemBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.connection.ConnectionFactory;
import static com.accenture.connection.EnumConnection.MSSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author automacao
 */
public class TestCaseTSRevisionDAO {
    private final static Logger logger = Logger.getLogger(TestCaseTSRevisionDAO.class);
    
    
    public TesteCaseTSBean getById(int id) {
        try {
           
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate],"
                    + "[IdRevision],"
                    + "[IdTestCaseType]"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision] "
                    + "WHERE [Id] = ?";

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_TC);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            TesteCaseTSBean tc = new TesteCaseTSBean();
            
            while (rs.next()) {
                
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("Fase"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));
                 tc.setIdRevision(rs.getInt("IdRevision"));
                tc.setIdTestCaseType(rs.getInt("IdTestCaseType"));

             
            }

            return tc;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }

    public List<TesteCaseTSBean> getBySystemBean(SystemBean system) {
        try {
            List<TesteCaseTSBean> caseTSBeans = new ArrayList<TesteCaseTSBean>();
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate],"
                       + "[IdRevision],"
                    + "[IdTestCaseType]"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision]"
                    + "WHERE [IdSystem] = ?";

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_TC);
            ps.setInt(1, system.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TesteCaseTSBean tc = new TesteCaseTSBean();
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("STIPRJ"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));
                   tc.setIdRevision(rs.getInt("IdRevision"));
                tc.setIdTestCaseType(rs.getInt("IdTestCaseType"));

                caseTSBeans.add(tc);
            }

            return caseTSBeans;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }
    }

    public List<TesteCaseTSBean> getByFields(String fields) {
        try {
            List<TesteCaseTSBean> caseTSBeans = new ArrayList<TesteCaseTSBean>();
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate],"
                     + "[modifyDate],"
                       + "[IdRevision],"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision]"
                    + "WHERE " + fields;

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            Statement statement = cf.getConnection().createStatement();

            ResultSet rs = statement.executeQuery(SQL_SELECT_TC);

            while (rs.next()) {
                TesteCaseTSBean tc = new TesteCaseTSBean();
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("STIPRJ"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));
                  tc.setIdRevision(rs.getInt("IdRevision"));
                tc.setIdTestCaseType(rs.getInt("IdTestCaseType"));

                caseTSBeans.add(tc);
            }

            return caseTSBeans;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }

    public TesteCaseTSBean insert(TesteCaseTSBean testCase) throws SQLException {
        
        
        ConnectionFactory cf = new ConnectionFactory(MSSQL);
        PreparedStatement ps = null;
        
        String SQL_INSERT_TC = "INSERT INTO [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision] ([TestPlan],"
                + "[STIPRJ],"
                + "[Fase],"
                + "[TestPhase],"
                + "[TestScriptName],"
                + "[TestScriptDescription],"
                + "[StepDescription],"
                + "[ExpectedResults],"
                + "[Product],"
                + "[DataPlanejada],"
                + "[NumeroCenario],"
                + "[NumeroCt],"
                + "[Complexidade],"
                + "[Automatizado],"
                + "[Cenario],"
                + "[Rework],"
                + "[Priority],"
                + "[Regression],"
                + "[Data],"
                + "[IdSystem],"
                + "[createdBy],"
                + "[modifiedBy],"
                + "[createDate],"
                + "[modifyDate],"
                + "[IdRevision],"
                + "[IdTestCaseType]"
                + ")"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
                       
            ps = cf.getConnection().prepareStatement(SQL_INSERT_TC, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, testCase.getTestPlan());
            ps.setString(2, testCase.getSTIPRJ());
            ps.setString(3, testCase.getFase());
            ps.setString(4, testCase.getTestPhase());
            ps.setString(5, testCase.getTestScriptName());
            ps.setString(6, testCase.getTestScriptDescription());
            ps.setString(7, testCase.getStepDescription());
            ps.setString(8, testCase.getExpectedResults());
            ps.setString(9, testCase.getProduct());
            ps.setTimestamp(10, new java.sql.Timestamp(testCase.getDataPlanejada().getTime()));
            ps.setString(11, testCase.getNumeroCenario());
            ps.setString(12, testCase.getNumeroCt());
            ps.setString(13, testCase.getComplexidade());
            ps.setBoolean(14, testCase.isAutomatizado());
            ps.setString(15, testCase.getCenario());
            ps.setBoolean(16, testCase.isRework());
            ps.setBoolean(17, testCase.isPriority());
            ps.setBoolean(18, testCase.isRegression());
            ps.setBoolean(19, testCase.isData());
            ps.setInt(20, testCase.getIdSystem());
            ps.setString(21, testCase.getCreatedBy());
            ps.setString(22, testCase.getModifiedBy());
            ps.setTimestamp(23, new java.sql.Timestamp(testCase.getModifyDate().getTime()));
            ps.setTimestamp(24, new java.sql.Timestamp(testCase.getCreateDate().getTime()));
            ps.setInt(25, testCase.getIdRevision());
            ps.setInt(26, testCase.getIdTestCaseType());

            int affectedRows = ps.executeUpdate();
             
            if (affectedRows == 0) {
                return null;
            } else {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    testCase.setId(id);                    
                    
                   generatedKeys.close();
                    return testCase;
                }
                return null;
            }

        } catch (Exception ex) {
            
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar CT", ex);
            return null;
       }finally{
            ps.close();
                    
            
        }


    }

    public TesteCaseTSBean update(TesteCaseTSBean testCase) throws Exception {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);
       
        PreparedStatement ps = null;
        


        String SQL_UPDATE_TC = "UPDATE [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision] SET "
                + "[TestPlan] = ?,"
                + "[STIPRJ] = ?,"
                + "[Fase] = ?,"
                + "[TestPhase] = ?,"
                + "[TestScriptName] = ?,"
                + "[TestScriptDescription] = ?,"
                + "[StepDescription] = ?,"
                + "[ExpectedResults] = ?,"
                + "[Product] = ?,"
                + "[DataPlanejada] = ?,"
                + "[NumeroCenario] = ?,"
                + "[NumeroCt] = ?,"
                + "[Complexidade] = ?,"
                + "[Automatizado] = ?,"
                + "[Cenario] = ?,"
                + "[Rework] = ?,"
                + "[Priority] = ?,"
                + "[Regression] = ?,"
                + "[Data] = ?,"
                + "[IdSystem] = ?,"
                + "[createdBy] = ?,"
                + "[modifiedBy] = ?,"
                + "[createDate] = ?,"
                + "[modifyDate] = ?, "
                + "[IdRevision] = ?,"
                + "[IdTestCaseType] = ? "
                + "WHERE [Id] = ?";
        try {

            ps = cf.getConnection().prepareStatement(SQL_UPDATE_TC);

            ps.setString(1, testCase.getTestPlan());
            ps.setString(2, testCase.getSTIPRJ());
            ps.setString(3, testCase.getFase());
            ps.setString(4, testCase.getTestPhase());
            ps.setString(5, testCase.getTestScriptName());
            ps.setString(6, testCase.getTestScriptDescription());
            ps.setString(7, testCase.getStepDescription());
            ps.setString(8, testCase.getExpectedResults());
            ps.setString(9, testCase.getProduct());
            ps.setTimestamp(10, new java.sql.Timestamp(testCase.getDataPlanejada().getTime()));
            ps.setString(11, testCase.getNumeroCenario());
            ps.setString(12, testCase.getNumeroCt());
            ps.setString(13, testCase.getComplexidade());
            ps.setBoolean(14, testCase.isAutomatizado());
            ps.setString(15, testCase.getCenario());
            ps.setBoolean(16, testCase.isRework());
            ps.setBoolean(17, testCase.isPriority());
            ps.setBoolean(18, testCase.isRegression());
            ps.setBoolean(19, testCase.isData());
            ps.setInt(20, testCase.getIdSystem());
            ps.setString(21, testCase.getCreatedBy());
            ps.setString(22, testCase.getModifiedBy());
            ps.setTimestamp(23, new java.sql.Timestamp(testCase.getModifyDate().getTime()));
            ps.setTimestamp(24, new java.sql.Timestamp(testCase.getCreateDate().getTime()));
            ps.setInt(25, testCase.getIdRevision());
            ps.setInt(26, testCase.getIdTestCaseType());
            ps.setInt(27, testCase.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return null;
            } else {
                return testCase;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar CT", ex);
            return null;
       }
    }
    
    public boolean delete(int id){
         String SQL_DELETE_TC = "DELETE FROM [CTCreatorDB].[dbo].[TesteCaseTSBeanRevision] WHERE [Id] = ?";
        try {
            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_DELETE_TC);
            
            ps.setInt(1, id);
            
            int row = ps.executeUpdate();
            
            if(row > 0 ){
                return true;
            }else{
                return false;
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar CT", ex);
            return false;
        }
    }

}
