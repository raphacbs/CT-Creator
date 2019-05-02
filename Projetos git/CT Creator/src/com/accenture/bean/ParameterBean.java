/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raphael.da.silva
 */
public class ParameterBean implements Serializable {
    private String parameterName;
    private String parameterValue;
    private boolean apllyToAll;
    private int Id;
    private int IdStep;
    
    
    public ParameterBean(){
        
    }

    public ParameterBean(String parameterName, String parameterValue, boolean apllyToAll, int Id, int IdStep) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.apllyToAll = apllyToAll;
        this.Id = Id;
        this.IdStep = IdStep;
    }
    
  

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public boolean isApllyToAll() {
        return apllyToAll;
    }

    public void setApllyToAll(boolean apllyToAll) {
        this.apllyToAll = apllyToAll;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getIdStep() {
        return IdStep;
    }

    public void setIdStep(int IdStep) {
        this.IdStep = IdStep;
    }

   
    
    
    
}


