/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author brucilin.de.gouveia
 */
public class TestPlanTSBean implements Serializable {

    private int testPlanId;
    private String name;
    private String sti;
    private String crFase;
    private String testPhase;
    private List<TesteCaseTSBean> testCases;
    private List<ParameterBean> parameters;
    private String release;
    private static final long serialVersionUID = -7932047122858971692L;
//    private static final long serialVersionUID = -7932047122858971692L; -1432622747367978334

    public TestPlanTSBean() {
       testCases = new ArrayList<TesteCaseTSBean>();
    }
    public int getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(int testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSti() {
        return sti;
    }

    public void setSti(String sti) {
        this.sti = sti;
    }

    public String getCrFase() {
        return crFase;
    }

    public void setCrFase(String crFase) {
        this.crFase = crFase;
    }

    public String getTestPhase() {
        return testPhase;
    }

    public void setTestPhase(String testPhase) {
        this.testPhase = testPhase;
    }

    public List<TesteCaseTSBean> getTestCase() {
        return testCases;
    }

    public void setTestCase(List<TesteCaseTSBean> testCase) {
        this.testCases = testCase;
    }
    
    public void addTestCase(TesteCaseTSBean testCase){
        this.testCases.add(testCase);
    }
    
    public void removeTestCase(int id){
        this.testCases.remove(id);
    }
    
    public void moveUpTestCase(int index){
        Collections.swap(this.testCases, index, index - 1);
    }
    
    public void moveDownTestCase(int index){
        Collections.swap(this.testCases, index, index + 1);
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

   
    
    

}
