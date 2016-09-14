/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.model;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;

/**
 *
 * @author raphael.da.silva
 */
public class CtAlm {

    private String campo;
    private String valor;
    private List<Plano> listPlano;

    public CtAlm() {
        listPlano = new ArrayList<Plano>();
    }

    public List<Plano> getListPlano() {
        return listPlano;
    }

    public void setListPlano(List<Plano> listPlano) {
        this.listPlano = listPlano;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Plano> getCTs(List<CtAlm> listCtAlm) {
        List<Plano> listPlano2 = new ArrayList<Plano>();
        Plano p = new Plano();

        for (int i = 0; i < listCtAlm.size() / 64; i++) {
            //loop com o n'umeros dos campos no xml 
            for (int j = 0; j < 64; j++) {
                switch (listCtAlm.get(j).getCampo()) {
                    case "user-template-08":
                        p.setQtdSistemas(Integer.parseInt(listCtAlm.get(j).getValor()));
                        break;

                    case "user-template-09":
                        p.setQtdStep(Integer.parseInt(listCtAlm.get(j).getValor()));
                        break;

                    case "user-template-01":
                        p.setSistemaMaster(listCtAlm.get(j).getValor());
                        break;

                    case "user-template-03":
                        p.setCenarioTeste(listCtAlm.get(j).getValor());
                        break;

                    case "user-template-06":
                        p.setRequisito(listCtAlm.get(j).getValor());
                        break;

                    case "user-template-05":
                        p.setFornecedor(listCtAlm.get(j).getValor());
                        break;

                    case "description":
                        String descricao = listCtAlm.get(j).getValor();
                        descricao = Jsoup.parse(descricao).text();
                        p.setDescCasoTeste(descricao);
                        break;

                    case "user-template-28":
                        p.setTpRequisito(listCtAlm.get(j).getValor());
                        break;

                    case "user-template-22":
                        p.setSistemasEnvolvidos(listCtAlm.get(j).getValor());
                        break;

                    case "user-template-24":
                        p.setComplexidade(listCtAlm.get(j).getValor());
                        break;

                    case "name":
                        p.setCasoTeste(listCtAlm.get(j).getValor());
                        break;

                    case "subtype-id":
                        p.setType(listCtAlm.get(j).getValor());
                        break;
                }
                
            }
            listPlano2.add(p);
        }

        return listPlano2;
    }

}
