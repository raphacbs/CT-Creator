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
                AtomicInteger counter = new AtomicInteger(0);
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
                AtomicInteger count = new AtomicInteger(0);

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

                return plano;

            } else {
                plano.getTestCase().stream().forEach(tc -> {
                    tc = tcInstanceDao.update(tc);
                });

                boolean erro = plano.getTestCase().stream().anyMatch(ct -> ct == null);
                if (erro) {
                    return null;
                }
                final int idPlano = plano.getId();
                AtomicInteger counter = new AtomicInteger(0);

                plano.getTestCase().stream().forEach(tc -> {
                    tc.setIdTestPlanTS(idPlano);
                    tc.setOrder(counter.getAndIncrement());
                });
                List<TesteCaseTSBean> update = new ArrayList<>();
                List<TesteCaseTSBean> insert = new ArrayList<>();
                List<TesteCaseTSBean> delete = new ArrayList<>();

//
//                List<Integer> idsBD = plano.getTestCase().stream().map(TesteCaseTSBean::getId).collect(Collectors.toList());
//                List<Integer> ids = testCase.getListStep().stream().map(Step::getId).collect(Collectors.toList());
//
//                updateStep = testCase.getListStep().stream().filter(s -> idsBD.contains(s.getId())).collect(Collectors.toList());
//                deleteStep = testCaseBD.getListStep().stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
//                insertStep = testCase.getListStep().stream().filter(s -> s.getId() == 0).collect(Collectors.toList());
//
//                int idTestCase = testCase.getId();
//                StepDAO stepDAO = new StepDAO();
//                updateStep.stream().forEach(s -> stepDAO.update(s));
//                deleteStep.stream().forEach(s -> stepDAO.delete(s.getId()));
//                insertStep.stream().forEach(s -> {s.setIdTesteCaseTSBean(idTestCase); stepDAO.insert(s);});
//
//                boolean erro = plano.getTestCase().stream().anyMatch(testecase -> testecase == null);
//                if (erro) {
//                    return null;
//                }
//                
                return plano;
            }
        } catch (IOException ex) {

            Logger.getLogger(TestPlanTSRN.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
