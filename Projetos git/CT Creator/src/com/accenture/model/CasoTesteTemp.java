/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.model;

/**
 *
 * @author raphael.da.silva
 */
public class CasoTesteTemp {
    private String casoTeste;
    private int celula;
    private int duplicado;
    private int sobrescrever;

    public int getDuplicado() {
        return duplicado;
    }

    public void setDuplicado(int duplicado) {
        this.duplicado = duplicado;
    }

    public int getSobrescrever() {
        return sobrescrever;
    }

    public void setSobrescrever(int sobrescrever) {
        this.sobrescrever = sobrescrever;
    }
    

    public CasoTesteTemp() {
    }

    public String getCasoTeste() {
        return casoTeste;
    }

    public void setCasoTeste(String casoTeste) {
        this.casoTeste = casoTeste;
    }

    public int getCelula() {
        return celula;
    }

    public void setCelula(int celula) {
        this.celula = celula;
    }
    
    
    
}
