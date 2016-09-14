/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.ParameterBean;
import java.util.Map;

/**
 *
 * @author raphael.da.silva
 */
public class ParameterDAO {

       
    private ParameterBean parameter;
    
    public ParameterDAO(){
        parameter = new ParameterBean();
    }
    
    
    public ParameterBean getParameter(){
        return parameter;
    }
    
    public void setParameter(ParameterBean p){
        this.parameter = p;
    }

    
    
    

}
