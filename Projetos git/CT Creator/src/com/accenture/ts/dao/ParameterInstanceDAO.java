/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.ParameterBean;
import com.accenture.bean.Step;
import com.accenture.connection.ConnectionFactory;
import com.accenture.connection.EnumConnection;
import static com.accenture.connection.EnumConnection.MSSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author raphael.da.silva
 */
public class ParameterInstanceDAO {

    private final static Logger logger = LogManager.getLogger(ParameterInstanceDAO.class.getName());
    private final EnumConnection BD = MSSQL;

    private ParameterBean parameter;

    public ParameterInstanceDAO() {
        parameter = new ParameterBean();
    }

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean p) {
        this.parameter = p;
    }

    public List<ParameterBean> getByIdTestCaseInstance(int idTestCaseInstance) {
        String SQL_SELECT_BY_IDSTEP = "SELECT "
                + "   pb.[Id]"
                + " ,[ParameterName]"
                + " ,[ParameterValue]"
                + " ,[ApllyToAll]"
                + " ,[idTestCaseInstance]"
                + " FROM [CTCreatorDB].[dbo].[ParameterBeanInstance] "
                + " JOIN [CTCreatorDB].[dbo].[TesteCaseTSBeanInstance] s on s.Id = pb.[idTestCaseInstance] "
                + " WHERE idTestCaseInstance = ?";
        try {
            List<ParameterBean> parameters = new ArrayList<>();

            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareCall(SQL_SELECT_BY_IDSTEP);
            ps.setInt(1, idTestCaseInstance);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParameterBean pb = new ParameterBean();
                pb.setId(rs.getInt("Id"));
                pb.setApllyToAll(rs.getBoolean("ApllyToAll"));
                pb.setIdStep(rs.getInt("idTestCaseInstance"));
                pb.setParameterName(rs.getString("ParameterName"));
                pb.setParameterValue(rs.getString("ParameterValue"));

                parameters.add(pb);
            }

            return parameters;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao selecionar os parâmetros", ex);
            return null;

        }
    }

//    public List<ParameterBean> getByIdStep(List<Integer> idStep) {
//        String SQL_SELECT_BY_IDSTEP = "SELECT "
//                + "  [Id]"
//                + " ,[ParameterName]"
//                + " ,[ParameterValue]"
//                + " ,[ApllyToAll]"
//                + " ,[IdStep]"
//                + " FROM [CTCreatorDB].[dbo].[ParameterBeanInstance] "
//               // + " JOIN [CTCreatorDB].[dbo].[Step] s on s.Id = pb.[IdStep] "
//                + " WHERE IdStep in (IDS_STEPS)";
//        try {
//            String ids = "";
//            for(int i =0; i <  idStep.size(); i++){
//                if(i == (idStep.size() - 1)){
//                    ids+= idStep.get(i)+"";
//                }else{
//                    ids+= idStep.get(i)+",";
//                }
//            }
//            SQL_SELECT_BY_IDSTEP = SQL_SELECT_BY_IDSTEP.replace("IDS_STEPS", ids.replaceAll(" ", ""));
//            List<ParameterBean> parameters = new ArrayList<>();
//
//            ConnectionFactory cf = new ConnectionFactory(BD);
//            Statement ps = cf.getConnection().createStatement();
//            
//
//            ResultSet rs = ps.executeQuery(SQL_SELECT_BY_IDSTEP);
//
//            while (rs.next()) {
//                ParameterBean pb = new ParameterBean();
//                pb.setId(rs.getInt("Id"));
//                pb.setApllyToAll(rs.getBoolean("ApllyToAll"));
//                pb.setIdStep(rs.getInt("IdStep"));
//                pb.setParameterName(rs.getString("ParameterName"));
//                pb.setParameterValue(rs.getString("ParameterValue"));
//
//                parameters.add(pb);
//            }
//
//            return parameters;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            logger.error("Erro ao selecionar os parâmetros", ex);
//            return null;
//
//        }
//    }

    
    public ParameterBean getById(int id) {
        String SQL_SELECT_BY_ID = "SELECT "
                + "  [Id]"
                + " ,[ParameterName]"
                + " ,[ParameterValue]"
                + " ,[ApllyToAll]"
                + " ,[idTestCaseInstance]"
                + " FROM [CTCreatorDB].[dbo].[ParameterBeanInstance] "
                + " WHERE Id = ?";
        try {
            ParameterBean pb = new ParameterBean();

            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareCall(SQL_SELECT_BY_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                pb.setId(rs.getInt("Id"));
                pb.setApllyToAll(rs.getBoolean("ApllyToAll"));
                pb.setIdStep(rs.getInt("idTestCaseInstance"));
                pb.setParameterName(rs.getString("ParameterName"));
                pb.setParameterValue(rs.getString("ParameterValue"));

            }

            return pb;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Erro ao selecionar os parâmetros", ex);
            return null;

        }
    }

    public ParameterBean insert(ParameterBean parameterBean) {
        String SQL_INSERT = "INSERT INTO [CTCreatorDB].[dbo].[ParameterBeanInstance]"
                + "([ParameterName], [ParameterValue], [ApllyToAll], [idTestCaseInstance])"
                + "VALUES(?,?,?,?)";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, parameterBean.getParameterName());
            ps.setString(2, parameterBean.getParameterValue());
            ps.setBoolean(3, parameterBean.isApllyToAll());
            ps.setInt(4, parameterBean.getIdTestCaseInstance());

            int row = ps.executeUpdate();

            if (row <= 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                ps.close();
                generatedKeys.close();
                parameterBean.setId(id);
                return parameterBean;
                }else{
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            return null;
        }

    }

    
      public boolean insert(List<ParameterBean> parameterBeans) {
        String SQL_INSERT = "INSERT INTO [CTCreatorDB].[dbo].[ParameterBeanInstance]"
                + "([ParameterName], [ParameterValue], [ApllyToAll], [idTestCaseInstance])"
                + "VALUES(?,?,?,?)";
        try {
            ConnectionFactory cf = new ConnectionFactory(BD);
            AtomicInteger cont = new AtomicInteger(1);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT);
            
            
            final int batchSize = 1000;
            int count = 0;
            
            for(ParameterBean parameterBean : parameterBeans){
                ps.setString(cont.getAndIncrement(), parameterBean.getParameterName());
                ps.setString(cont.getAndIncrement(), parameterBean.getParameterValue());
                ps.setBoolean(cont.getAndIncrement(), parameterBean.isApllyToAll());
                ps.setInt(cont.getAndIncrement(), parameterBean.getIdTestCaseInstance());
                ps.addBatch();
                
                cont.set(1);
                
                if (++count % batchSize == 0) {
                    int[] row = ps.executeBatch();
                }
            
            }
            
            int [] row = ps.executeBatch();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            return false;
        }

    }
    
    public boolean delete(int Id) {
        String SQL_DELETE_BY_ID = "DELETE FROM [CTCreatorDB].[dbo].[ParameterBeanInstance] "
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
    
     public ParameterBean update(ParameterBean parameterBean){
         String SQL_UPDATE_BY_ID = "UPDATE [CTCreatorDB].[dbo].[ParameterBeanInstance]"
                  + "[ParameterName] = ? , [ParameterValue] = ?, [ApllyToAll] = ?, [idTestCaseInstance] = ?"
                 + "WHERE Id = ?";
         try{
         ConnectionFactory cf = new ConnectionFactory(BD);
         PreparedStatement ps = cf.getConnection().prepareCall(SQL_UPDATE_BY_ID);
         ps.setString(1, parameterBean.getParameterName());
         ps.setString(2, parameterBean.getParameterValue());
         ps.setBoolean(3, parameterBean.isApllyToAll());
         ps.setInt(4, parameterBean.getIdTestCaseInstance());

         
         int row = ps.executeUpdate();
         ps.close();
         if(row <= 1){
              return parameterBean;
         }else{
             return null;
         }
         
         }catch(Exception ex){
             ex.printStackTrace();
             logger.error(ex);
             return null;
         }
         
     }

}
