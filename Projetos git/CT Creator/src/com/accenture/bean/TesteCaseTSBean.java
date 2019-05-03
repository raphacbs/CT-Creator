/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author raphael.da.silva
 */
public class TesteCaseTSBean implements Serializable {

    private TestCaseTSPropertiesBean testCaseProperties;
    private String testPlan;
    private String STIPRJ;
    private String fase;
    private String testPhase;
    private String testScriptName;
    private String testScriptDescription;
    private final int STEP_NUMERO = 1;
    private String stepDescription;
    private String expectedResults;
    private String product;
    private Date dataPlanejada;
    private List<Step> listStep;
    private List<ParameterBean> parameters;
    private String numeroCenario; //Alterado para String para aceita 01, 00... CR 15112
    private String numeroCt; //Alterado para String para aceita 01, 00... CR 15112
    private int hashCode;
    private String complexidade;
    private boolean automatizado;
    private String cenario;
    private static final long serialVersionUID = 8449377741076660063L;//-1432622747367978334L; 
    private boolean rework;
    private boolean priority;
    private boolean regression;
    private boolean data;
    
    private int idSystem;
    private int Id;
    private String createdBy;
    private String modifiedBy;
    private Date createDate;
    private Date modifyDate;
    private int ordem;
//
    public TesteCaseTSBean(TestCaseTSPropertiesBean testCaseProperties) {
        super();
        this.testCaseProperties = testCaseProperties;
        
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String complexidade) {
        this.complexidade = complexidade;
    }
    
    

    public List<Step> getListStep() {
        return listStep;
    }

    public void setListStep(List<Step> listStep) {
        this.listStep = listStep;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }
    
    
    
    public TesteCaseTSBean(){
        
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
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

    public boolean isAutomatizado() {
        return automatizado;
    }

    public void setAutomatizado(boolean automatizado) {
        this.automatizado = automatizado;
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

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public TestCaseTSPropertiesBean getTestCaseProperties() {
        return testCaseProperties;
    }

    public void setTestCaseProperties(TestCaseTSPropertiesBean testCaseProperties) {
        this.testCaseProperties = testCaseProperties;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    
    
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Date getDataPlanejada() {
        return dataPlanejada;
    }

    public void setDataPlanejada(Date dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
    }

    public String getFASE() {
        return fase;
    }

    public int getSTEP_NUMERO() {
        return STEP_NUMERO;
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(String expectedResults) {
        this.expectedResults = expectedResults;
    }

     public List<ParameterBean> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterBean> parameters) {
        this.parameters = parameters;
    }
    //Alterado para String para aceita 01, 00... CR 15112
    public String getNumeroCenario() {
        return numeroCenario;
    }
    //Alterado para String para aceita 01, 00... CR 15112
    public void setNumeroCenario(String numeroCenario) {
        this.numeroCenario = numeroCenario;
    }
    //Alterado para String para aceita 01, 00... CR 15112
    public String getNumeroCt() {
        return numeroCt;
    }
    //Alterado para String para aceita 01, 00... CR 15112
    public void setNumeroCt(String numeroCt) {
        this.numeroCt = numeroCt;
    }

    public String getCenario() {
        return cenario;
    }

    public void setCenario(String cenario) {
        this.cenario = cenario;
    }

    public boolean isRework() {
        return rework;
    }

    public void setRework(boolean rework) {
        this.rework = rework;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean isRegression() {
        return regression;
    }

    public void setRegression(boolean regression) {
        this.regression = regression;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public int getIdSystem() {
        return idSystem;
    }

    public void setIdSystem(int idSystem) {
        this.idSystem = idSystem;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
    
    
    

}
