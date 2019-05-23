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
import com.accenture.connection.ConnectionFactory;
import static com.accenture.connection.EnumConnection.MSSQL;
import com.accenture.ts.dao.ParameterDAO;
import com.accenture.ts.dao.ParameterInstanceDAO;
import com.accenture.ts.dao.RevisionDAO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class TestCaseTSRN {

    private TesteCaseTSDAO tsDao;

    public TesteCaseTSDAO getTsDao() {
        return tsDao;
    }

    public void setTsDao(TesteCaseTSDAO tsDao) {
        this.tsDao = tsDao;
    }

    public SvnConnectionDao getSvnDao() {
        return svnDao;
    }

    public void setSvnDao(SvnConnectionDao svnDao) {
        this.svnDao = svnDao;
    }
    private SvnConnectionDao svnDao;

    public TestCaseTSRN(String fase) throws SVNException, IOException {
        tsDao = new TesteCaseTSDAO();
        svnDao = new SvnConnectionDao();
    }

    public TestCaseTSRN() {
        tsDao = new TesteCaseTSDAO();
    }

    /**
     * Importa todos os CTs de uma planilha e salva no SVN cada CT em uma
     * planilha
     *
     * @param pathSheetFull : diretório completo commitMessege : Comentário que
     * será adicionado no SVN
     */
    public void importSheetAllCts(String pathSheetFull, String commitMessege, String fase) throws IOException, SVNException {

        String folderTemp = SVNPropertiesVOBean.getInstance().getFolderTemplocal();
        List<TesteCaseTSBean> listTS = tsDao.readSheet(pathSheetFull);
        String dirFull = "";
        tsDao.deleteDir(new File(folderTemp));

        String temp = "";
        for (int i = 0; i < listTS.size(); i++) {
            String systemFolder = listTS.get(i).getProduct();
            dirFull = folderTemp + systemFolder;
            String fileName = "";
            if (listTS.get(i).getTestScriptName().length() > 27) {
                fileName = createId(listTS.get(i).getProduct(), fase) + " - " + listTS.get(i).getTestScriptName().substring(0, 27) + ".xlsx";
            } else {
                fileName = createId(listTS.get(i).getProduct(), fase) + " - " + listTS.get(i).getTestScriptName() + ".xlsx";
            }

            if (!temp.equals(systemFolder)) {
                new SvnConnectionDao(fase).checkOutEmpytFolder(systemFolder, fase);
                tsDao.newTsSheet(dirFull, fileName, listTS.get(i));
                svnDao.addFileOrFolder(systemFolder, commitMessege);
                dirFull = "";
                temp = systemFolder;
            } else {
                tsDao.newTsSheet(dirFull, fileName, listTS.get(i));
                svnDao.addFileOrFolder(systemFolder, commitMessege);
                dirFull = "";
            }

        }

    }

    public String createId(String folder, String fase) throws SVNException, IOException {

        List<TestCaseTSPropertiesBean> list = new SvnConnectionDao(fase).listAllTestCase(folder);
        Integer biggerID = 1;
        for (int i = 0; i < list.size(); i++) {
            if (biggerID <= Integer.parseInt(list.get(i).getTesteCaseId())) {
                biggerID = Integer.parseInt(list.get(i).getTesteCaseId());
                biggerID++;
            }
        }
        int size = biggerID.toString().length();
        String id = biggerID.toString();
        switch (size) {
            case 1:
                id = "000" + id;
                break;
            case 2:
                id = "00" + id;
                break;
            case 3:
                id = "0" + id;
                break;

        }
        id = id + "-";
        return id;
    }

    /**
     * Gera uma planlha de TS
     *
     * @param pathSheetFull : diretório completo commitMessege : Comentário que
     * será adicionado no SVN
     */
    public String createSheet(TesteCaseTSBean testCase, String system, String fase) throws IOException, SVNException {
        String nameSheet = createId(system, fase) + testCase.getTestScriptName().toUpperCase();
        String pathSheet = SVNPropertiesVOBean.getInstance().getFolderTemplocal() + system;
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.newTsSheet(pathSheet, nameSheet, testCase);
        return pathSheet + "\\" + nameSheet;
    }

    //CR
    public String createSheet(TesteCaseTSBean testCase, String system, int hashCode, String fase) throws IOException, SVNException {
        String nameSheet = createId(system, fase) + testCase.getTestScriptName().toUpperCase();
        String pathSheet = SVNPropertiesVOBean.getInstance().getFolderTemplocal() + hashCode + "\\" + system;
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.newTsSheet(pathSheet, nameSheet, testCase);
        return pathSheet + "\\" + nameSheet;
    }
    //CR

    public TestPlanTSBean importPlanSheet(String pathSheetFull) throws IOException {
        return tsDao.readPlanSheet(pathSheetFull);
    }

    public void createSheetExport(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws IOException, Exception {
        String fullDesc = "";
        String fullResult = "";
        for (int i = 0; i < testPlan.getTestCase().size(); i++) {
            for (int j = 0; j < breakTestCaseForStep(testPlan.getTestCase().get(i).getStepDescription()).size(); j++) {
                String desc = breakTestCaseForStep(testPlan.getTestCase().get(i).getStepDescription()).get(j);
                String result = breakTestCaseForStep(testPlan.getTestCase().get(i).getExpectedResults()).get(j);

                if (!desc.equals("") || !result.equals("")) {
                    fullDesc = fullDesc + desc;
                    fullResult = fullResult + result;
                }
            }
            testPlan.getTestCase().get(i).setStepDescription(fullDesc);
            testPlan.getTestCase().get(i).setExpectedResults(fullResult);
            fullDesc = "";
            fullResult = "";
        }

        tsDao.newTsSheet(pathSheet, nameSheet, testPlan);
    }

    public void createSpreadsheetTSNew(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws FileNotFoundException, IOException, InterruptedException, Exception {
        nameSheet = nameSheet.replace("Siebel 6.3", "SBL6.3");
        nameSheet = nameSheet.replace("Siebel 8", "SBL8");
        nameSheet = nameSheet.replace("STC DADOS", "STC");
        nameSheet = nameSheet.replace("STC VOZ", "STC");

//        for (int i = 0; i < testPlan.getTestCase().size(); i++) {
//            List<Step> steps = new ArrayList<>();
//            List<String> desc = new ArrayList<>();
//            List<String> result = new ArrayList<>();
//
//            desc = breakTestCaseForStep(testPlan.getTestCase().get(i).getStepDescription());
//            result = breakTestCaseForStep(testPlan.getTestCase().get(i).getExpectedResults());
//
//            System.out.println("com.accenture.ts.rn.TestCaseTSRN.createSpreadsheetTSNew() - " + i);
//
//            for (int s = 0; s < desc.size(); s++) {
//
//                if (!desc.equals("") || !result.equals("")) {
//
//                    desc.set(s, desc.get(s).substring(desc.get(s).indexOf("-") + 2));
//                    result.set(s, result.get(s).substring(result.get(s).indexOf("-") + 2));
//
//                }
//
//                Step step = new Step();
//                step.setDescStep(desc.get(s));
//                step.setResultadoStep(result.get(s));
//
//                steps.add(step);
//            }
//
//            testPlan.getTestCase().get(i).setListStep(steps);

//            if (testPlan.getTestCase().get(i).getProduct().equals("STC VOZ") || testPlan.getTestCase().get(i).getProduct().equals("STC DADOS")) {
//                testPlan.getTestCase().get(i).setProduct("STC");
//            }
//
//        }

        tsDao.createSpreadsheetTS(pathSheet, nameSheet, testPlan);
    }

    public void writerSheet(String pathSheet, String nameSheet, TesteCaseTSBean testCase) throws IOException {
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.updateTsSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + "\\" + pathSheet, nameSheet, testCase);
    }

    //CR PERMITE VARIAS TELAS
    public void writerSheet(String pathSheet, String nameSheet, TesteCaseTSBean testCase, int hashCode) throws IOException {
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.updateTsSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + hashCode + "\\" + pathSheet, nameSheet, testCase);
    }
    //CR

    public List<TesteCaseTSBean> readSheet(String pathSheetFull) throws IOException {
        List<TesteCaseTSBean> list = tsDao.readSheet(pathSheetFull);

        for (int i = 0; i < list.size(); i++) {
            List<Step> listStep = new ArrayList<Step>();
            for (int j = 0; j < breakTestCaseForStep(list.get(i).getStepDescription()).size(); j++) {
                Step s = new Step();

                String desc = breakTestCaseForStep(list.get(i).getStepDescription()).get(j);
                String result = breakTestCaseForStep(list.get(i).getExpectedResults()).get(j);
                if (!desc.equals("") || !result.equals("")) {
                    s.setDescStep(desc.substring(desc.indexOf("-") + 2));
                    s.setResultadoStep(result.substring(result.indexOf("-") + 2));
                }
                listStep.add(s);
            }
//             for(int j = 0; j < list.get(i).getStepDescription().split("|").length; j++){
//                 Step s = new Step();
//
//                String desc = list.get(i).getStepDescription().split("|")[j];
//                String result = list.get(i).getExpectedResults().split("|")[j];
//                if (!desc.equals("") || !result.equals("")) {
//                    s.setDescStep(desc.substring(desc.indexOf("-") + 2));
//                    s.setResultadoStep(result.substring(result.indexOf("-") + 2));
//                }
//                listStep.add(s);
//             }

            list.get(i).setListStep(listStep);
        }

        return list;
    }

    public String addPipeStepDescription(List<Step> listStep, boolean isExportSvn) {
        //
        //Adicionado um parâmetro boolean ao método para definir se os steps serão demarcados com |

        StringBuilder stringTemp = new StringBuilder();
        String quebraLinha = System.getProperty("line.separator");
        //varre toda a lista de step
        for (int i = 0; i < listStep.size(); i++) {
            //verifica o tamanho do nome do step para capturar apenas o número
            if (listStep.get(i).getNomeStep().length() < 7) {
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 6));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getDescStep());
                //adicionar o pipe antes de quebrar a linha ex.: stringTemp.append("|");
                if (isExportSvn && listStep.size() != i + 1) {
                    stringTemp.append("|");
                }
                stringTemp.append(quebraLinha);
            } else {
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 7));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getDescStep());
                if (isExportSvn && listStep.size() != i + 1) {
                    stringTemp.append("|");
                }
                stringTemp.append(quebraLinha);
            }
        }
        return stringTemp.toString();
    }

    public String addPipeExpectedResult(List<Step> listStep, boolean isExportSvn) {
        StringBuilder stringTemp = new StringBuilder();
        String quebraLinha = System.getProperty("line.separator");
        //varre toda a lista de step
        for (int i = 0; i < listStep.size(); i++) {
            //verifica o tamanho do nome do step para capturar apenas o número
            if (listStep.get(i).getNomeStep().length() < 7) {
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 6));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getResultadoStep());
                //adicionar o pipe antes de quebrar a linha ex.: stringTemp.append("|");
                if (isExportSvn && listStep.size() != i + 1) {
                    stringTemp.append("|");
                }
                stringTemp.append(quebraLinha);
            } else {
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 7));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getResultadoStep());
                if (isExportSvn && listStep.size() != i + 1) {
                    stringTemp.append("|");
                }
                stringTemp.append(quebraLinha);
            }
        }
        return stringTemp.toString();
    }

    /*
     * O método BreakTestCaseForStep, recebe uma string com um caso de teste completo e separa os steps,
     * idividualmente atribuindo-os à uma string e adicionando-os em um Array de strings que é retornado pelo método.
     */
    public ArrayList<String> breakTestCaseForStep(String testeCaseComplete) {

        ArrayList<String> steps = new ArrayList<>();
        String testeCaseBreak;
        int countBegin = 0;
        int countEnd = 0;
        int countPipe = this.counterPipe(testeCaseComplete);
        int currentLocationPipe;

        for (int i = 0; i <= countPipe; i++) {

            if (i > 0) {
                countBegin = 0;
            }

            countEnd = 0;
            if (i == countPipe) {
                //System.out.println(stringComplete);
                steps.add(testeCaseComplete);
                break;
            }

            testeCaseBreak = testeCaseComplete;

            countEnd = testeCaseComplete.length();

            currentLocationPipe = testeCaseBreak.indexOf("|");
            testeCaseBreak = testeCaseComplete.substring(countBegin, currentLocationPipe);

            testeCaseComplete = testeCaseComplete.substring(currentLocationPipe + 1, countEnd);
            //System.out.println(nameControl);
            steps.add(testeCaseBreak);

        }
        return steps;
    }

    /*
     * Método CalculatorPipe recebe uma String marcada por pipes como parâmetro,
     * e retorna um inteiro com a quantidade de pipes encontradas na string.
     */
    public int counterPipe(String stringComplete) {

        String steps = stringComplete;
        int countPipe = 0;

        while (steps.indexOf("|") > 0) {
            countPipe++;
            steps = steps.substring(steps.indexOf("|") + 1, steps.length());
        }

        return countPipe;
    }

    public ArrayList<String> systemsTestCase() throws IOException {
        String temp = SVNPropertiesVOBean.getInstance().getSystems();
        return breakTestCaseForStep(temp);
    }

    public ArrayList<String> faseCRTestCase() throws IOException {
        String temp = SVNPropertiesVOBean.getInstance().getFaseCR();
        return breakTestCaseForStep(temp);
    }

    public ArrayList<String> testPhase() throws IOException {
        String temp = SVNPropertiesVOBean.getInstance().getTestPhaseTS();
        return breakTestCaseForStep(temp);
    }

    public ArrayList<String> complexidade() throws IOException {
        String temp = SVNPropertiesVOBean.getInstance().getComplexidade();
        return breakTestCaseForStep(temp);
    }

    public void deleteDir() throws IOException {
        tsDao.deleteDir(new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal()));
    }

    public void deleteDir(String folder) throws IOException {
        tsDao.deleteDir(new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + folder));
    }

    ///banco
    public TesteCaseTSBean saveTestCaseTSBD(TesteCaseTSBean testCase) {

        TesteCaseTSDAO tSDAO = new TesteCaseTSDAO();
            StepDAO stepDAO = new StepDAO();
        try {
            
            ;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date d = new Date(System.currentTimeMillis());
            testCase.setModifiedBy(SVNPropertiesVOBean.getInstance().getUser());
            testCase.setModifyDate(d);
            testCase.setDataPlanejada(d);
            testCase.setIdTestCaseType(1);
            
             RevisionDAO revisionDAO = new RevisionDAO();
                int Idrevision = revisionDAO.insert(testCase);
                if(Idrevision == 0){
                    return null;
                }
                testCase.setIdRevision(Idrevision);

            //salvando CT
            if (testCase.getId() == 0) {
                ConnectionFactory cf = new ConnectionFactory(MSSQL);
//                cf.getConnection().setAutoCommit(false);
                
               
                
                testCase.setCreatedBy(SVNPropertiesVOBean.getInstance().getUser());
                testCase.setCreateDate(d);
                testCase = tsDao.insert(testCase);

                if (testCase == null) {
                    cf.getConnection().rollback();
                    return null;
                }

                
                final int idTestcase = testCase.getId();
                AtomicInteger counter = new AtomicInteger(0);
                testCase.getListStep().stream().forEach(s -> {s.setOrdem(counter.getAndIncrement()); s.setIdRevision(Idrevision); s.setIdTesteCaseTSBean(idTestcase); s = stepDAO.insert(s); });
                //s.setIdTesteCaseTSBean(testCase.getId())
                boolean erro = testCase.getListStep().stream().anyMatch(s -> s == null);

                if (erro) {
                    cf.getConnection().rollback();
                    return null;
                }
                
//                cf.getConnection().commit();
                
//                //criando revision
//                int testCaseRevisionId = tSDAO.createTestCaseRevision(testCase.getId());
//                if (testCaseRevisionId == 0){
//                    return null;
//                }
//                boolean revision = stepDAO.createStepRevision(testCaseRevisionId);
//
//                return testCase;

            } else {
                
                int idTestcase = testCase.getId();
                TesteCaseTSBean testCaseBD = getTesteCaseTSBeanById(idTestcase);
                AtomicInteger counter = new AtomicInteger(1);
                //atualizando lista de steps
                testCase.getListStep().stream().forEach(s -> { s.setNomeStep("Step "+counter.getAndIncrement());s.setOrdem(counter.get()); s.setIdTesteCaseTSBean(idTestcase); });
                List<Step> updateStep = new ArrayList<>();
                List<Step> insertStep = new ArrayList<>();
                List<Step> deleteStep = new ArrayList<>();

                List<Integer> idsBD = testCaseBD.getListStep().stream().map(Step::getId).collect(Collectors.toList());
                List<Integer> ids = testCase.getListStep().stream().map(Step::getId).collect(Collectors.toList());

                updateStep = testCase.getListStep().stream().filter(s -> idsBD.contains(s.getId())).collect(Collectors.toList());
                deleteStep = testCaseBD.getListStep().stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
                insertStep = testCase.getListStep().stream().filter(s -> s.getId() == 0).collect(Collectors.toList());

                int idTestCase = testCase.getId();
                
                updateStep.stream().forEach(s ->{ s.setIdRevision(Idrevision); stepDAO.update(s);});
                deleteStep.stream().forEach(s ->{s.setIdRevision(Idrevision); stepDAO.delete(s.getId());});
                insertStep.stream().forEach(s -> {s.setIdRevision(Idrevision); s.setIdTesteCaseTSBean(idTestCase); stepDAO.insert(s);});

                testCase = tsDao.update(testCase);
                List<Step> steps = new ArrayList<>();
                steps.addAll(updateStep);
                steps.addAll(insertStep);
                
                
              
                
                
                if(testCase == null){
                    return null;
                }
                
                testCase.setListStep(steps);

                
            }
            
               //criando revision
                int testCaseRevisionId = tSDAO.createTestCaseRevision(testCase.getId());
                if (testCaseRevisionId == 0){
                    return null;
                }
                boolean revision = stepDAO.createStepRevision(testCase.getId(),Idrevision,testCaseRevisionId);
                
                return testCase;
            
            
        } catch (Exception ex) {
            return null;
        }

    }
    
    public boolean delete(TesteCaseTSBean testCase){
        StepDAO stepDAO = new StepDAO();
        
       // testCase.getListStep().stream().forEach(s-> { stepDAO.delete(s.getId());});
        List<Boolean> b = testCase.getListStep().stream().map(s-> stepDAO.delete(s.getId())).collect(Collectors.toList());               
        
        if(!b.contains(false)){
            return tsDao.delete(testCase.getId());
        }
        
        return false;
    }

    public List<SystemBean> getSystemsBD() {

        return new SystemDAO().getAll();
    }
    
    public SystemBean getSystemsByName(String product) {

        return new SystemDAO().getByName(product);
    }

    public List<TesteCaseTSBean> getTesteCaseTSBeanBD() {
        return tsDao.getAll();
    }

    public List<TesteCaseTSBean> getTesteCaseTSBeanBySystemBeanBD(SystemBean sb) {
        return tsDao.getBySystemBean(sb);
    }

    public List<TesteCaseTSBean> getTesteCaseTSBeanBySystemNameBD(int IdSystem, String name, String id) {
        
        String fieldId = "";
        
        if(id.isEmpty() || id.equals("0") ){
            fieldId = "";
        }else{
            fieldId = " AND [Id] =  " + id;
        }
       
        List<TesteCaseTSBean> list = tsDao.getByFields("IdSystem = " + IdSystem + " AND [TestScriptName] LIKE '%" + name + "%' " + fieldId);

        return list;

    }
    
    public TesteCaseTSBean saveOrGetTesteCase(TesteCaseTSBean tc) {
        
        List<TesteCaseTSBean> list = tsDao.getByFields("IdSystem = " + tc.getIdSystem() + " AND [TestScriptName] LIKE '%" + tc.getTestScriptName() + "%' " );
        TesteCaseTSBean temp = null;
        
        if(list.size() == 0){
            
            temp = saveTestCaseTSBD(tc);
            return temp;
        }else{
            StepDAO stepDAO = new StepDAO();
            temp = list.get(0);
            temp.setListStep(stepDAO.getByTestCaseBean(temp.getId()));
        }
        
        return temp;

    }
    
    
    
    public List<TesteCaseTSBean> getTesteCaseTSBeanById(int... id) {
        StringBuilder sbids = new StringBuilder();
        for(int i =0 ; i < id.length; i++){
            sbids.append(id[i]);
            if(i  != id.length-1){
                sbids.append(",");
            }               
                
        }
        
        List<TesteCaseTSBean> list = tsDao.getByFields("Id in ( " + sbids.toString() + ")");
        AtomicInteger cont = new AtomicInteger(0);
        list.stream().forEach(tc-> {
                
            if (tc != null) {
                    StepDAO stepDAO = new StepDAO();
                    ParameterDAO pd = new ParameterDAO();

                    List<Step> steps = stepDAO.getByTestCaseBean(tc.getId());    

                    for (int i = 0; i < steps.size(); i++) {
                         List<ParameterBean> listParameters = new ArrayList<>();
                           List<String> parameterNames = tsDao.getParameter(steps.get(i).getDescStep());
                           parameterNames.addAll(tsDao.getParameter(steps.get(i).getResultadoStep()));
                        for (int j = 0; j < parameterNames.size(); j++) {

                            
                            ParameterBean pb = new ParameterBean();
                            pb.setApllyToAll(false);
                            pb.setParameterName(parameterNames.get(j));
                            pb.setParameterValue("");
                            pb.setIdStep(steps.get(i).getId());
                            if(!listParameters.stream().anyMatch(p-> p.getParameterName().equals(pb.getParameterName()))){
                               listParameters.add(pb);
                            }
                           

                        }
                        steps.get(i).setParameters(listParameters);
                    }
                    
                    tc.setListStep(steps);
                };
    
    
         });
        
              
        
        

        return list;

    }

    public TesteCaseTSBean getTesteCaseTSBeanById(int Id) {

        TesteCaseTSBean testeCaseTSBean = tsDao.getById(Id);

        if (testeCaseTSBean != null) {
            StepDAO stepDAO = new StepDAO();
            ParameterDAO pd = new ParameterDAO();

            List<Step> steps = stepDAO.getByTestCaseBean(testeCaseTSBean.getId());

            List<Integer> ids = steps.stream().map(Step::getId).collect(Collectors.toList());
            
            List<String> parameterNames = steps.stream().map(Step::getDescStep).collect(Collectors.toList());
            parameterNames.addAll(steps.stream().map(Step::getResultadoStep).collect(Collectors.toList()));
            
            
            for (int i = 0; i < steps.size(); i++) {
                 List<ParameterBean> listParameters = new ArrayList<>();
                for (int j = 0; j < parameterNames.size(); j++) {

                    ParameterBean pb = new ParameterBean();
                    pb.setApllyToAll(false);
                    pb.setParameterName(parameterNames.get(j));
                    pb.setParameterValue("");
                    listParameters.add(pb);

                }
                steps.get(i).setParameters(listParameters);
            }


//
//                ParameterBean pb = new ParameterBean();
//                pb.setApllyToAll(false);
//                pb.setParameterName(parameters.get(j));
//                pb.setParameterValue("");
//                listParameters.add(pb);
//
//            }

//            if (ids.size() > 0) {
//                List<ParameterBean> pbs = pd.getByIdStep(ids);
//                steps.stream().forEach(u -> u.setParameters(pbs.stream().filter(x -> x.getIdStep() == u.getId()).collect(Collectors.toList())));
//            }
//            for (Step s : steps) {
//                s.setParameters(pbs.stream().filter(x-> x.getIdStep() == s.getId()).collect(Collectors.toList()));
//            }

            testeCaseTSBean.setListStep(steps);

            return testeCaseTSBean;

        } else {
            return null;
        }

    }

}
