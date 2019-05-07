/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.ParameterBean;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.ts.dao.ParameterDAO;
import com.accenture.ts.dao.ParameterInstanceDAO;
import com.accenture.ts.dao.TestPlanTSDao;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author raphael.da.silva
 */
public class ParameterRN {

    private String nome;
    private String valor;
    private ParameterDAO parameteDAO;

    public ParameterRN() {
        parameteDAO = new ParameterDAO();
    }

    public void addParameter(String nome, String Valor, boolean applyAll) {
       parameteDAO.getParameter().setParameterName(nome);
       parameteDAO.getParameter().setParameterValue(Valor);
       parameteDAO.getParameter().setApllyToAll(applyAll);
    }

   
    
    public ParameterBean getParameter(){
        return  parameteDAO.getParameter();
    }
    
    public void setParameter(ParameterBean parameter){
        this.parameteDAO.setParameter(parameter);
    }

    public List<String> searchParameters(String texto) {
        String regex;
        Pattern p;
        Matcher m;
        List<String> paramentros = new ArrayList<String>();
        regex = "(\\<{3}(\\w+{20})\\>{3})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);

        while (m.find()) {
            System.out.println(m.group(1));
            paramentros.add(m.group(1));
        }

        return paramentros;
    }
    
       public List<TesteCaseTSBean> getByListTestCaseTSBeanInstance(List<TesteCaseTSBean> tcs) {
        
        List<Integer> ids = tcs.stream().map(TesteCaseTSBean::getIdTesteCaseTSBeanInstance).collect(Collectors.toList());
        ParameterInstanceDAO paramDAO = new ParameterInstanceDAO();
        List<ParameterBean> list = paramDAO.getByIdTestCaseInstance(ids);
        List<Integer> idsParametros = list.stream().map(ParameterBean::getIdTestCaseInstance).collect(Collectors.toList());
        
        tcs.forEach(tc->{
            if(idsParametros.stream().anyMatch(id-> id == tc.getIdTesteCaseTSBeanInstance())){
                tc.setParameters(list.stream().filter(pb-> tc.getIdTesteCaseTSBeanInstance() == pb.getIdTestCaseInstance()).collect(Collectors.toList()));
            }
        });
        
        
        
        
      
        
        return tcs;

    }


}
