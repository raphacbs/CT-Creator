/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
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
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

/**
 *
 * @author brucilin.de.gouveia
 */
public class TestPlanTSDao implements Serializable {
    
    private TestPlanTSBean testPlan;
    private List<TesteCaseTSBean> listTc;
    private final static Logger logger = Logger.getLogger(TestPlanTSDao.class);
    private static final long serialVersionUID =8816035183481948888L; 

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
                    + "[product]"
                    + " FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[TestPlanTSBean] "
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
                plano.setProduct("createdBy");
            }

            return plano;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }
    
    public List<TestPlanTSBean> getByFields(String fields) {
        try {
           
            String SQL_SELECT = "SELECT "
                    + "[Id],"
                    + "[Name],"
                    + "[Sti],"
                    + "[CrFase],"
                    + "[TestPhase],"
                    + "[Release],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate],"
                    + "[product]"
                    + " FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[TestPlanTSBean] "
                   + "WHERE " + fields;

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT);
          
            
            ResultSet rs = ps.executeQuery();
            
            List<TestPlanTSBean> planos= new ArrayList<TestPlanTSBean>();
            
            
            while (rs.next()) {
                TestPlanTSBean plano = new TestPlanTSBean();
                plano.setId(rs.getInt("Id"));
                plano.setName(rs.getString("Name"));
                plano.setSti(rs.getString("Sti"));
                plano.setCrFase(rs.getString("CrFase"));
                plano.setTestPhase(rs.getString("TestPhase"));
                plano.setRelease(rs.getString("Release"));
                plano.setCreatedBy(rs.getString("createdBy"));
                plano.setModifiedBy(rs.getString("modifiedBy"));
                plano.setCreateDate(rs.getTimestamp("createDate"));
                plano.setModifyDate(rs.getTimestamp("modifyDate"));
                plano.setProduct(rs.getString("createdBy"));
                plano.setProduct(rs.getString("product"));
                
//                TesteCaseTSInstanceDAO instanceDAO = new TesteCaseTSInstanceDAO();
//                plano.setTestCase(instanceDAO.getByFields(" [IdTestPlanTS] = "+ plano.getId()));
                planos.add(plano);
            }

            return planos;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }
    
    
    public TestPlanTSBean insert(TestPlanTSBean plano)  {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);
try {
        String SQL_INSERT_TC = "INSERT INTO "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[TestPlanTSBean] ("
                + "[Name],"
                + "[Sti],"
                + "[CrFase],"
                + "[TestPhase],"
                + "[Release],"
                + "[createdBy],"
                + "[modifiedBy],"
                + "[createDate],"
                + "[modifyDate],"
                + "[product]"
                + ")"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        

            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT_TC, Statement.RETURN_GENERATED_KEYS);
            AtomicInteger cont = new AtomicInteger(1);
            ps.setString(cont.getAndIncrement(), plano.getName());
            ps.setString(cont.getAndIncrement(), plano.getSti());
            ps.setString(cont.getAndIncrement(), plano.getCrFase());
            ps.setString(cont.getAndIncrement(), plano.getTestPhase());
            ps.setString(cont.getAndIncrement(), plano.getRelease());
            ps.setString(cont.getAndIncrement(), plano.getCreatedBy());
            ps.setString(cont.getAndIncrement(), plano.getModifiedBy());
            ps.setTimestamp(cont.getAndIncrement(), new java.sql.Timestamp(plano.getCreateDate().getTime()));
            ps.setTimestamp(cont.getAndIncrement(), new java.sql.Timestamp(plano.getModifyDate().getTime()));
            ps.setString(cont.getAndIncrement(), plano.getProduct());

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
    
      public TestPlanTSBean update(TestPlanTSBean plano){
        ConnectionFactory cf = new ConnectionFactory(MSSQL);
try {
        String SQL_UPDATE_TC = "UPDATE "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[TestPlanTSBean] SET "
                + "[Name] = ?,"
                + "[Sti] = ?,"
                + "[CrFase] = ?,"
                + "[TestPhase] = ?,"
                + "[Release] = ?,"
                + "[createdBy]= ?,"
                + "[modifiedBy]= ?,"
                + "[createDate]= ?,"
                + "[modifyDate]= ?,"
                + "[product] = ?"
                + " WHERE [Id] = ?";
        

            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_UPDATE_TC);
            AtomicInteger cont = new AtomicInteger(1);
            ps.setString(cont.getAndIncrement(), plano.getName());
            ps.setString(cont.getAndIncrement(), plano.getSti());
            ps.setString(cont.getAndIncrement(), plano.getCrFase());
            ps.setString(cont.getAndIncrement(), plano.getTestPhase());
            ps.setString(cont.getAndIncrement(), plano.getRelease());
            ps.setString(cont.getAndIncrement(), plano.getCreatedBy());
            ps.setString(cont.getAndIncrement(), plano.getModifiedBy());
            ps.setTimestamp(cont.getAndIncrement(), new java.sql.Timestamp(plano.getCreateDate().getTime()));
            ps.setTimestamp(cont.getAndIncrement(), new java.sql.Timestamp(plano.getModifyDate().getTime()));
            ps.setString(cont.getAndIncrement(), plano.getProduct());
            ps.setInt(cont.getAndIncrement(), plano.getId());
            
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
         try { String SQL_DELETE_TC = "DELETE FROM "+SVNPropertiesVOBean.getInstance().getDatabaseNameBD()+".[dbo].[TestPlanTSBean] WHERE [Id] = ?";
       
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
