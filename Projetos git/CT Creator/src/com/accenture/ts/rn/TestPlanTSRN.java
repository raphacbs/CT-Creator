/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.ParameterBean;
import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.Step;
import com.accenture.bean.SystemBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.ts.dao.ParameterDAO;
import com.accenture.ts.dao.ParameterInstanceDAO;
import com.accenture.ts.dao.StepDAO;
import com.accenture.ts.dao.StepInstanceDAO;
import com.accenture.ts.dao.StepRevisionDAO;
import com.accenture.ts.dao.SvnConnectionDao;
import com.accenture.ts.dao.SystemDAO;
import com.accenture.ts.dao.TestPlanTSDao;
import com.accenture.ts.dao.TesteCaseTSDAO;
import com.accenture.ts.dao.TesteCaseTSInstanceDAO;
import com.accenture.view.RegisterScreenTIView;
import com.accenture.view.RegisterScreenTSView;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class TestPlanTSRN {

    public TestPlanTSRN() {

    }

    public TestPlanTSBean savePlanDB(TestPlanTSBean plano) {

        try {

            TesteCaseTSInstanceDAO tcInstanceDao = new TesteCaseTSInstanceDAO();
            StepInstanceDAO stepInstanceDAO = new StepInstanceDAO();
            TestPlanTSDao testPlanTSDao = new TestPlanTSDao();
            ParameterInstanceDAO parameterDAO = new ParameterInstanceDAO();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date currentDate = new Date(System.currentTimeMillis());
            String user = SVNPropertiesVOBean.getInstance().getUser();
            plano.setModifyDate(currentDate);
            plano.setModifiedBy(user);
            // List<TesteCaseTSBean> testCaseList = plano.getTestCase();
            //   List<Step>
            if (plano.getId() == 0) {

                plano.setCreateDate(currentDate);

                plano.setCreatedBy(user);

                plano = testPlanTSDao.insert(plano);

                if (plano == null) {
                    return null;
                }

                //salvando os testcase
                final int idplano = plano.getId();
                AtomicInteger counter = new AtomicInteger(1);
                plano.getTestCase().stream().forEach(tc -> {
                    tc.setOrder(counter.getAndIncrement());
                    final int id = Integer.parseInt(idplano + "" + tc.getIdRevision()+ "" + tc.getOrder());
                    tc.setIdTesteCaseTSBeanInstance(id);                    
                    tc.setIdTestPlanTS(idplano);
                   // tc = tcInstanceDao.insert(tc);
                });

                tcInstanceDao.insert(plano.getTestCase());

                boolean erro = plano.getTestCase().stream().anyMatch(testecase -> testecase == null);
                if (erro) {
                    return null;
                }

                //add parametros
                List<ParameterBean> parameters = new ArrayList<>();
                List<String> parameterNames = new ArrayList<String>();
                AtomicInteger count = new AtomicInteger(1);

                for (TesteCaseTSBean tc : plano.getTestCase()) {
                    final int idTc = tc.getIdTesteCaseTSBeanInstance();
                   // parameterNames.addAll(tc.getParameters().stream().map(ParameterBean::getParameterName).collect(Collectors.toList()));
//                    if (tc.getParameters().stream().anyMatch(p -> !parameterNames.contains(p.getParameterName()))) {
//                        tc.getParameters().forEach(pb -> pb.setIdTestCaseInstance(idTc));
//                        parameters.addAll(tc.getParameters());
//                        parameterNames.addAll(tc.getParameters().stream().map(ParameterBean::getParameterName).collect(Collectors.toList()));
//                    }

                    for (ParameterBean pb : tc.getParameters()) {
                        pb.setIdTestCaseInstance(idTc);
                        Predicate<ParameterBean> contains = p -> p.getParameterName().equals(pb.getParameterName()) && p.getIdTestCaseInstance() == pb.getIdTestCaseInstance();
                        boolean noExist = !parameters.stream().anyMatch(contains);

//                        boolean condition1 = parameters.stream().anyMatch(p-> p.getParameterName().equals(pb.getParameterName()));
//                        boolean condition2 = parameters.stream().anyMatch(p-> p.getIdTestCaseInstance() == pb.getIdTestCaseInstance());
                        if (noExist || count.getAndIncrement() == 0) {
                            parameters.add(pb);
                        }
                    }

                }

                parameterDAO.insert(parameters);
                
                for (int i = 0; i < plano.getTestCase().size(); i++) {
                    List<ParameterBean> pb = parameterDAO.getByIdTestCaseInstance(plano.getTestCase().get(i).getIdTesteCaseTSBeanInstance());
                    plano.getTestCase().get(i).setParameters(pb);
                }
                

                return plano;

            } else {
                
                parameterDAO = new ParameterInstanceDAO();
                testPlanTSDao.update(plano);
                               
                List<TesteCaseTSBean> updateStep = new ArrayList<>();
                List<TesteCaseTSBean> insertStep = new ArrayList<>();
                List<TesteCaseTSBean> deleteStep = new ArrayList<>();
                
                

                final int idPlano = plano.getId();
                AtomicInteger counter = new AtomicInteger(1);

                plano.getTestCase().stream().forEach(tc -> {
                    tc.setIdTestPlanTS(idPlano);
                    tc.setOrder(counter.getAndIncrement());
                });
                
                List<TesteCaseTSBean> update = new ArrayList<>();
                List<TesteCaseTSBean> insert = new ArrayList<>();
                List<TesteCaseTSBean> delete = new ArrayList<>();
                
                 TesteCaseTSInstanceDAO instanceDAO = new TesteCaseTSInstanceDAO();
                 List<TesteCaseTSBean> tcBD =  instanceDAO.getByFields(" IdTestPlanTS = "+plano.getId());
                  
                 List<Integer> idsBD = tcBD.stream().map(TesteCaseTSBean::getIdTesteCaseTSBeanInstance).collect(Collectors.toList());
                 List<Integer> ids = plano.getTestCase().stream().map(TesteCaseTSBean::getIdTesteCaseTSBeanInstance).collect(Collectors.toList());
                 
                 
                 update = plano.getTestCase().stream().filter(tc -> idsBD.contains(tc.getIdTesteCaseTSBeanInstance())).collect(Collectors.toList());
                 delete = tcBD.stream().filter(tc -> !ids.contains(tc.getIdTesteCaseTSBeanInstance())).collect(Collectors.toList());
                 insert = plano.getTestCase().stream().filter(tc -> tc.getIdTesteCaseTSBeanInstance() == 0).collect(Collectors.toList());

                 update.stream().forEach(tc -> instanceDAO.update(tc));
                 
                 //deletando os parametros
                 List<Integer> parametersDelete = new ArrayList<>();
                 //delete.stream().forEach(tc-> {parametersDelete.addAll(tc.getParameters());});
                 for(TesteCaseTSBean tc : delete){
                    parameterDAO.deleteByTestCaseInstanceBean(tc.getIdTesteCaseTSBeanInstance());
                 }
                 
                 
                 //deletando os TCS
                 delete.stream().forEach(tc -> instanceDAO.delete(tc.getIdTesteCaseTSBeanInstance()));
                 
                 ///inserindo os TCs
                 insert.stream().forEach(tc -> {tc.setIdTestPlanTS(idPlano);
                 final int id = Integer.parseInt(idPlano + "" + tc.getIdRevision()+ "" + tc.getOrder());
                 tc.setIdTesteCaseTSBeanInstance(id);
                 });
                 
                 tcInstanceDao.insert(insert);         
                
                
                List<ParameterBean> parametersUpdate = new ArrayList<>();
                update.stream().forEach(tc-> {parametersUpdate.addAll(tc.getParameters());});
                parameterDAO.update(parametersUpdate);
                
                
                 //add parametros
                List<ParameterBean> parameters = new ArrayList<>();
                List<String> parameterNames = new ArrayList<String>();
                AtomicInteger count = new AtomicInteger(1);

                for (TesteCaseTSBean tc : insert) {
                    final int idTc = tc.getIdTesteCaseTSBeanInstance();
                    for (ParameterBean pb : tc.getParameters()) {
                        pb.setIdTestCaseInstance(idTc);
                        Predicate<ParameterBean> contains = p -> p.getParameterName().equals(pb.getParameterName()) && p.getIdTestCaseInstance() == pb.getIdTestCaseInstance();
                        boolean noExist = !parameters.stream().anyMatch(contains);
                        if (noExist || count.getAndIncrement() == 0) {
                            parameters.add(pb);
                        }
                    }

                }
                parameterDAO.insert(parameters);
                
                for(TesteCaseTSBean tc : insert){
                    tc.setParameters( parameterDAO.getByIdTestCaseInstance(tc.getIdTesteCaseTSBeanInstance()));
                }
                
                insert.addAll(update);
                plano.setTestCase(insert);
               
                for (int i = 0; i < plano.getTestCase().size(); i++) {
                    List<ParameterBean> pb = parameterDAO.getByIdTestCaseInstance(plano.getTestCase().get(i).getIdTesteCaseTSBeanInstance());
                    plano.getTestCase().get(i).setParameters(pb);
                }
                
                plano.getTestCase().stream().sorted(Comparator.comparingInt(TesteCaseTSBean::getOrder)).collect(Collectors.toList());
                

                return plano;
            }
        } catch (IOException ex) {

            Logger.getLogger(TestPlanTSRN.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public List<TestPlanTSBean> getTesteCaseTSBeanBySystemNameBD(String product, String name, String id) {
        TestPlanTSDao planTSDao = new TestPlanTSDao();
        String fieldId = id.isEmpty() ? "" : "AND [Id] =  " + id;
        List<TestPlanTSBean> list = planTSDao.getByFields("[product] = '" + product + "' AND [Name] LIKE '%" + name + "%' " + fieldId);

        return list;

    }
    
     public TestPlanTSBean getById(int id) {
        TestPlanTSDao planTSDao = new TestPlanTSDao();
        String fieldId = " [Id] =  " + id;
        TestPlanTSBean plan = planTSDao.getByFields(fieldId).get(0);
        
        TesteCaseTSInstanceDAO instanceDAO = new TesteCaseTSInstanceDAO();
        plan.setTestCase(instanceDAO.getByFields(" IdTestPlanTS = "+plan.getId()));
                
        ParameterRN parameterRN = new ParameterRN();
        plan.setTestCase(parameterRN.getByListTestCaseTSBeanInstance(plan.getTestCase()));
        
        plan.setTestCase(plan.getTestCase().stream().sorted(Comparator.comparingInt(TesteCaseTSBean::getOrder)).collect(Collectors.toList()));
        
        
        StepRevisionDAO revisionDAO = new StepRevisionDAO();
            plan.getTestCase().stream().forEach(tc->{tc.setListStep(revisionDAO.getByTestCaseBean(tc.getId(), tc.getIdRevision()));});
        
        
        return plan;

    }
     
   
}
