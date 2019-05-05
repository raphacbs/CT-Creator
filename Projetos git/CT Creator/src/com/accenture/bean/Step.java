/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Raphael
 */
public class Step implements Serializable {
    private int Id;
    private String nomeStep;
    private String descStep;
    private String resultadoStep;
    private int idPlano;
    private int IdTesteCaseTSBean;
    private int IdTesteCaseTSBeaninstance;
    private int Ordem;
    private int IdRevision;
    
    private List<ParameterBean> parameters;

    public Step(int id, String nomeStep, String descStep, String resultadoStep, int idPlano) {
        this.Id = id;
        this.nomeStep = nomeStep;
        this.descStep = descStep;
        this.resultadoStep = resultadoStep;
        this.idPlano = idPlano;
    }
    
    public Step(){
        
    }

    public int getId() {
        return Id;
    }
    


    public void setId(int id) {
        this.Id = id;
    }

    public String getNomeStep() {
        return nomeStep;
    }

    public void setNomeStep(String nomeStep) {
        this.nomeStep = nomeStep;
    }

    public String getDescStep() {
        return descStep;
    }

    public void setDescStep(String descStep) {
        this.descStep = descStep;
    }

    public String getResultadoStep() {
        return resultadoStep;
    }

    public void setResultadoStep(String resultadoStep) {
        this.resultadoStep = resultadoStep;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public int getIdTesteCaseTSBean() {
        return IdTesteCaseTSBean;
    }

    public void setIdTesteCaseTSBean(int IdTesteCaseTSBean) {
        this.IdTesteCaseTSBean = IdTesteCaseTSBean;
    }

    public int getOrdem() {
        return Ordem;
    }

    public void setOrdem(int Ordem) {
        this.Ordem = Ordem;
    }

    public List<ParameterBean> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterBean> parameters) {
        this.parameters = parameters;
    }

    public int getIdTesteCaseTSBeaninstance() {
        return IdTesteCaseTSBeaninstance;
    }

    public void setIdTesteCaseTSBeaninstance(int IdTesteCaseTSBeaninstance) {
        this.IdTesteCaseTSBeaninstance = IdTesteCaseTSBeaninstance;
    }

    public int getIdRevision() {
        return IdRevision;
    }

    public void setIdRevision(int IdRevision) {
        this.IdRevision = IdRevision;
    }
    
    
    
    
}
