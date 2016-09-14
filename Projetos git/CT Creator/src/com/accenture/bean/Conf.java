/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

/**
 *
 * @author Raphael
 */
public class Conf {
    private int cod;
    private String descricao;

    public Conf(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }
    
    public Conf() {
         
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
