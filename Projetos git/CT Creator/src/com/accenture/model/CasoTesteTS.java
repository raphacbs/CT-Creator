/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raphael.da.silva
 */
public class CasoTesteTS {
    private String testPlan;
    private String STIPRJ;
    private final String FASE = "TS";
    private String testPhase;
    private String testScriptName;
    private String testScriptDescription;
    private final int STEP_NUMERO = 1;
    private String stepDescription;
    private String expectedResults;
    private String product;
    private String dataPlanejada;
    private List<Step> listStep;

    public CasoTesteTS() {
         listStep = new ArrayList<Step>();
    }

    public String getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(String testPlan) {
        this.testPlan = testPlan;
    }

    public String getSTIPRJ() {
        return STIPRJ;
    }

    public void setSTIPRJ(String STIPRJ) {
        this.STIPRJ = STIPRJ;
    }

    public String getTestPhase() {
        return testPhase;
    }

    public void setTestPhase(String testPhase) {
        this.testPhase = testPhase;
    }

    public String getTestScriptName() {
        return testScriptName;
    }

    public void setTestScriptName(String testScriptName) {
        this.testScriptName = testScriptName;
    }

    public String getTestScriptDescription() {
        return testScriptDescription;
    }

    public void setTestScriptDescription(String testScriptDescription) {
        this.testScriptDescription = testScriptDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(List<Step> listStep) {
        StringBuilder stringTemp = new StringBuilder();
        String quebraLinha = System.getProperty("line.separator");
        //varre toda a lista de step
        for(int i = 0; i < listStep.size(); i++){
            //verifica o tamanho do nome do step para capturar apenas o número
            if(listStep.get(i).getNomeStep().length() < 7){
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 6));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getDescStep());
                stringTemp.append(quebraLinha);                
            }else{
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 7));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getDescStep());
                stringTemp.append(quebraLinha);
            }            
        }        
        this.stepDescription = stringTemp.toString();
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(List<Step> listStep) {
        StringBuilder stringTemp = new StringBuilder();
        String quebraLinha = System.getProperty("line.separator");
        //varre toda a lista de step
        for(int i = 0; i < listStep.size(); i++){
            //verifica o tamanho do nome do step para capturar apenas o número
            if(listStep.get(i).getNomeStep().length() < 7){
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 6));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getResultadoStep());
                stringTemp.append(quebraLinha);                
            }else{
                stringTemp.append(listStep.get(i).getNomeStep().substring(5, 7));
                stringTemp.append(" - ");
                stringTemp.append(listStep.get(i).getResultadoStep());
                stringTemp.append(quebraLinha);
            }            
        }        
        this.expectedResults = stringTemp.toString();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDataPlanejada() {
        return dataPlanejada;
    }

    public void setDataPlanejada(String dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
    }

    public String getFASE() {
        return FASE;
    }

    public int getSTEP_NUMERO() {
        return STEP_NUMERO;
    }
    
    
    
    
    
    
}
