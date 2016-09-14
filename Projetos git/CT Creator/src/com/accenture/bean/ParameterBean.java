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
    
    
    public ParameterBean(){
        
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

   
    
    
    
}


