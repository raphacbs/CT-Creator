/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.Step;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.ts.dao.SvnConnectionDao;
import com.accenture.ts.dao.TesteCaseTSDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public TestCaseTSRN() throws SVNException, IOException {
        tsDao = new TesteCaseTSDAO();
        svnDao = new SvnConnectionDao();
    }

    /**
     * Importa todos os CTs de uma planilha e salva no SVN cada CT em uma
     * planilha
     *
     * @param pathSheetFull : diretório completo commitMessege : Comentário que
     * será adicionado no SVN
     */
    public void importSheetAllCts(String pathSheetFull, String commitMessege) throws IOException, SVNException {

        String folderTemp = new SVNPropertiesVOBean().getFolderTemplocal();
        List<TesteCaseTSBean> listTS = tsDao.readSheet(pathSheetFull);
        String dirFull = "";
        tsDao.deleteDir(new File(folderTemp));

        String temp = "";
        for (int i = 0; i < listTS.size(); i++) {
            String systemFolder = listTS.get(i).getProduct();
            dirFull = folderTemp + systemFolder;
            String fileName = "";
            if (listTS.get(i).getTestScriptName().length() > 27) {
                fileName = createId(listTS.get(i).getProduct()) + " - " + listTS.get(i).getTestScriptName().substring(0, 27) + ".xlsx";
            } else {
                fileName = createId(listTS.get(i).getProduct()) + " - " + listTS.get(i).getTestScriptName() + ".xlsx";
            }

            if (!temp.equals(systemFolder)) {
                new SvnConnectionDao().checkOutEmpytFolder(systemFolder);
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

    public String createId(String folder) throws SVNException, IOException {

        List<TestCaseTSPropertiesBean> list = new SvnConnectionDao().listAllTestCase(folder);
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
    public String createSheet(TesteCaseTSBean testCase, String system) throws IOException, SVNException {
        String nameSheet = createId(system) + testCase.getTestScriptName().toUpperCase();
        String pathSheet = new SVNPropertiesVOBean().getFolderTemplocal() + system;
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.newTsSheet(pathSheet, nameSheet, testCase);
        return pathSheet + "\\" + nameSheet;
    }

    //CR
    public String createSheet(TesteCaseTSBean testCase, String system, int hashCode) throws IOException, SVNException {
        String nameSheet = createId(system) + testCase.getTestScriptName().toUpperCase();
        String pathSheet = new SVNPropertiesVOBean().getFolderTemplocal() + hashCode + "\\" + system;
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.newTsSheet(pathSheet, nameSheet, testCase);
        return pathSheet + "\\" + nameSheet;
    }
    //CR

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
    
    public void createSpreadsheetTSNew(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws FileNotFoundException, IOException, InterruptedException, Exception{
        nameSheet = nameSheet.replace("Siebel 6.3", "SBL6.3");
        nameSheet = nameSheet.replace("Siebel 8", "SBL8");  
        
   
        
        
        for (int i = 0; i < testPlan.getTestCase().size(); i++) {
            List<Step> steps = new ArrayList<>();
             List<String> desc = new ArrayList<>();
             List<String> result = new ArrayList<>();
            
                desc = breakTestCaseForStep(testPlan.getTestCase().get(i).getStepDescription());
                result = breakTestCaseForStep(testPlan.getTestCase().get(i).getExpectedResults());                
          
            System.out.println("com.accenture.ts.rn.TestCaseTSRN.createSpreadsheetTSNew() - "+i);
           
               for(int s = 0 ; s <desc.size(); s++ ){
                   
                   if (!desc.equals("") || !result.equals("") ) {
                       
                    desc.set(s,  desc.get(s).substring(desc.get(s).indexOf("-") + 2));
                    result.set(s,  result.get(s).substring(result.get(s).indexOf("-") + 2));
                    
                    
                } 
                   
                  Step step = new Step();
                  step.setDescStep(desc.get(s));
                  step.setResultadoStep(result.get(s));
                  
                  steps.add(step);
               }
          
            
            testPlan.getTestCase().get(i).setListStep(steps);
                   
            
           
        }

        tsDao.createSpreadsheetTS(pathSheet, nameSheet, testPlan);
    }

    public void writerSheet(String pathSheet, String nameSheet, TesteCaseTSBean testCase) throws IOException {
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.updateTsSheet(new SVNPropertiesVOBean().getFolderTemplocal() + "\\" + pathSheet, nameSheet, testCase);
    }
    
    //CR PERMITE VARIAS TELAS
    public void writerSheet(String pathSheet, String nameSheet, TesteCaseTSBean testCase, int hashCode) throws IOException {
        testCase.setStepDescription(addPipeStepDescription(testCase.getListStep(), true));
        testCase.setExpectedResults(addPipeExpectedResult(testCase.getListStep(), true));
        tsDao.updateTsSheet(new SVNPropertiesVOBean().getFolderTemplocal() +hashCode+ "\\" + pathSheet, nameSheet, testCase);
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
        String temp = new SVNPropertiesVOBean().getSystems();
        return breakTestCaseForStep(temp);
    }

    public ArrayList<String> faseCRTestCase() throws IOException {
        String temp = new SVNPropertiesVOBean().getFaseCR();
        return breakTestCaseForStep(temp);
    }

    public ArrayList<String> testPhase() throws IOException {
        String temp = new SVNPropertiesVOBean().getTestPhaseTS();
        return breakTestCaseForStep(temp);
    }
    
    public ArrayList<String> complexidade() throws IOException{
        String temp = new SVNPropertiesVOBean().getComplexidade();
        return breakTestCaseForStep(temp);
    }

    public void deleteDir() throws IOException {
        tsDao.deleteDir(new File(new SVNPropertiesVOBean().getFolderTemplocal()));
    }

    public void deleteDir(String folder) throws IOException {
        tsDao.deleteDir(new File(new SVNPropertiesVOBean().getFolderTemplocal()+folder));
    }

}
