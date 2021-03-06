/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.util.Date;
import java.util.List;

/**
 *
 * @author raphael.da.silva
 */
public class FlowBean implements Comparable<FlowBean> {
    
    private String id;
    private String name;
    private String description;
    private Date registerDate; 
    private String system;
    private List<String> testCases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public List<String> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<String> testCases) {
        this.testCases = testCases;
    }

    @Override
    public String toString() {
        return  name+" - "+system;
    }
    
     @Override
     public int compareTo(FlowBean outroFlow) {
         
         int sistema = this.getSystem().compareToIgnoreCase(outroFlow.getSystem());
         if (sistema !=0) return sistema;
         
         int name = this.getName().compareToIgnoreCase(outroFlow.getName());
         if(name != 0)return name;
         
         return this.getRegisterDate().compareTo(outroFlow.getRegisterDate());
     }
    
    
    
}
