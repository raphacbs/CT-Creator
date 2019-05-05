/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.connection.ConnectionFactory;
import static com.accenture.connection.EnumConnection.MSSQL;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author brucilin.de.gouveia
 */
public class TestPlanTSDao implements Serializable {
    
    private TestPlanTSBean testPlan;
    private List<TesteCaseTSBean> listTc;
    private final static Logger logger = Logger.getLogger(TestPlanTSDao.class);

    public TestPlanTSDao() {
        testPlan = new TestPlanTSBean();
        listTc = new ArrayList<TesteCaseTSBean>();
    }

    public List<TesteCaseTSBean> getListTc() {
        return listTc;
    }

    public void setListTc(List<TesteCaseTSBean> listTc) {
        this.listTc = listTc;
    }
    
    
    
    public void addTestCase(TesteCaseTSBean ct){
        this.testPlan.addTestCase(ct);
    }
    
    public void removeTestCase(int id){
        this.testPlan.removeTestCase(id);
    }

    public TestPlanTSBean getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlanTSBean testPlan) {
        this.testPlan = testPlan;
    }
    
     public void moveUpTestCase(int index){
        Collections.swap(this.testPlan.getTestCase(), index, index - 1);
    }
    
    public void moveDownTestCase(int index){
        Collections.swap(this.testPlan.getTestCase(), index, index + 1);
    }
    
    public TestPlanTSBean getById(int id) {
        try {
           
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[Name],"
                    + "[Sti],"
                    + "[CrFase],"
                    + "[TestPhase],"
                    + "[Realese],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate],"
                    + " FROM [CTCreatorDB].[dbo].[TestPlanTSBean] "
                    + "WHERE [Id] = ?";

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_TC);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            TestPlanTSBean plano = new TestPlanTSBean();
            
            while (rs.next()) {
                plano.setId(rs.getInt("Id"));
                plano.setName(rs.getString("Name"));
                plano.setCrFase(rs.getString("CrFase"));
                plano.setTestPhase(rs.getString("TestPhase"));
                plano.setRelease("Release");
                plano.setCreatedBy("createdBy");
                plano.setModifiedBy("modifiedBy");
                plano.setCreateDate(new java.sql.Timestamp(plano.getCreateDate().getTime()));
                plano.setModifyDate(new java.sql.Timestamp(plano.getModifyDate().getTime()));
            }

            return plano;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }
    
    public TestPlanTSBean insert(TestPlanTSBean plano)  {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);

        String SQL_INSERT_TC = "INSERT INTO [CTCreatorDB].[dbo].[TestPlanTSBean] ("
                + "[Name],"
                + "[Sti],"
                + "[CrFase],"
                + "[TestPhase],"
                + "[Release],"
                + "[createdBy],"
                + "[modifiedBy],"
                + "[createDate],"
                + "[modifyDate]"
                + ")"
                + " VALUES(?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT_TC, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, plano.getName());
            ps.setString(2, plano.getSti());
            ps.setString(3, plano.getCrFase());
            ps.setString(4, plano.getTestPhase());
            ps.setString(5, plano.getRelease());
            ps.setString(6, plano.getCreatedBy());
            ps.setString(7, plano.getModifiedBy());
            ps.setTimestamp(8, new java.sql.Timestamp(plano.getCreateDate().getTime()));
            ps.setTimestamp(9, new java.sql.Timestamp(plano.getModifyDate().getTime()));
 

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return null;
            } else {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    plano.setId(id);
                    ps.close();
                    generatedKeys.close();
                    return plano;
                }
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar plano", ex);
            return null;
       }
        


    }
    
      public TestPlanTSBean update(TestPlanTSBean plano) throws Exception {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);

        String SQL_UPDATE_TC = "UPDATE [CTCreatorDB].[dbo].[TestPlanTSBean] SET "
                + "[Name] = ?,"
                + "[Sti] = ?,"
                + "[CrFase] = ?,"
                + "[TestPhase] = ?,"
                + "[Realease] = ?,"
                + "[createdBy]= ?,"
                + "[modifiedBy]= ?,"
                + "[createDate]= ?,"
                + "[modifyDate]= ?"
                + " WHERE [Id] = ?";
        try {

            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_UPDATE_TC);

            ps.setString(1, plano.getName());
            ps.setString(2, plano.getSti());
            ps.setString(3, plano.getCrFase());
            ps.setString(4, plano.getTestPhase());
            ps.setString(5, plano.getRelease());
            ps.setString(6, plano.getCreatedBy());
            ps.setString(7, plano.getModifiedBy());
            ps.setTimestamp(8, new java.sql.Timestamp(plano.getCreateDate().getTime()));
            ps.setTimestamp(9, new java.sql.Timestamp(plano.getModifyDate().getTime()));
            
            
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return null;
            } else {
                return plano;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar plano", ex);
            return null;
       }
    }
      
         public boolean delete(int id){
         String SQL_DELETE_TC = "DELETE FROM [CTCreatorDB].[dbo].[TestPlanTSBean] WHERE [Id] = ?";
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
            logger.error("Erro ao excluir o plano", ex);
            return false;
        }
    }
    
    
}
