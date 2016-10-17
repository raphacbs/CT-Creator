/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import org.tmatesoft.svn.core.SVNDirEntry;

/**
 *
 * @author brucilin.de.gouveia
 */
public class TestCaseTSPropertiesBean  implements Comparable<TestCaseTSPropertiesBean> {

    private SVNDirEntry dirEntry;
    private String testCaseName;
    private String testeCaseId;
    private String system;
    int pointPosition;
    int startPoint;
    int startPointId;
    int finishPointId;
    private int hashCode;

    public TestCaseTSPropertiesBean(SVNDirEntry dirEntry) {

        this.pointPosition = 0;
        this.startPoint = 0;
        this.startPointId = 0;
        this.finishPointId = 0;
        pointPosition = dirEntry.getName().indexOf(".");
        startPoint = dirEntry.getName().indexOf("-") + 1;
        startPointId = 0;//dirEntry.getName().indexOf("TS") + 2;
        finishPointId = dirEntry.getName().indexOf("-") ;
        this.dirEntry = dirEntry;
        this.testCaseName = dirEntry.getName().substring(startPoint, pointPosition);
        this.testeCaseId = dirEntry.getName().substring(startPointId, finishPointId);
        this.hashCode = dirEntry.hashCode();
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }
    
    

    public SVNDirEntry getDirEntry() {
        return dirEntry;
    }

    public void setDirEntry(SVNDirEntry dirEntry) {
        this.dirEntry = dirEntry;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getTesteCaseId() {
        return testeCaseId;
    }

    public void setTesteCaseId(String testeCaseId) {
        this.testeCaseId = testeCaseId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
    
     @Override
     public int compareTo(TestCaseTSPropertiesBean caseTSPropertiesBean) {
         
         int sistema = this.getSystem().compareToIgnoreCase(caseTSPropertiesBean.getSystem());
         if (sistema !=0) return sistema;
         
         int name = this.getTesteCaseId().compareToIgnoreCase(caseTSPropertiesBean.getTesteCaseId());
         if(name != 0)return name;
         
         return this.getTestCaseName().compareTo(caseTSPropertiesBean.getTestCaseName());
     }
    
    

}
