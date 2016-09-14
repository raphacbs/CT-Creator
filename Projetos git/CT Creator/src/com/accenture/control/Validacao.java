/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Raphael
 */
public class Validacao {

    private Pattern p;
    private Matcher m;
    private String regex;
    private ManipulaDadosSQLite bd = new ManipulaDadosSQLite();

    public Validacao(String texto) throws SQLException, ClassNotFoundException {
        getParametros(texto);
        verifaParamentroInvalidos(texto);
    }
    
    public Validacao() throws SQLException, ClassNotFoundException {
        
    }
    
    //Método responsável por localizar todas os para
    public List<String> getParametros(String texto) {
        List<String> paramentros = new ArrayList<String>();
        regex = "(\\<{3}(\\w+{20})\\>{3})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);

        while (m.find()) {
            System.out.println(m.group(2));
            paramentros.add(m.group(2));
        }

        return paramentros;
    }

    public List<String> verifaParamentroInvalidos(String texto) {
        List<String> paramentrosInvalidos = new ArrayList<String>();

        //Localizando parametros inválidos 1 - <<_>>>
        regex = "(\\<{2}([^<]\\w+)\\>{3})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }

        //Localizando parametros inválidos 2 - <_>>>
        regex = "(\\ <{1}(\\w+)\\>{3})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
           
            paramentrosInvalidos.add(m.group(2));
        }

        //Localizando parametros inválidos 3
        regex = "(\\ (\\w+)\\>{3})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }

        //Localizando parametros inválidos 4
        regex = "(\\ (\\w+)\\>{2})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }

        //Localizando parametros inválidos 5
        regex = "(\\ (\\w+)\\>{1})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
        //Localizando parametros inválidos 6
        regex = "(\\<{3}(\\w+) )";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
         //Localizando parametros inválidos 7
        regex = "(\\ <{2}(\\w+) )";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
         //Localizando parametros inválidos 8
        regex = "(\\ <{1}(\\w+) )";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
         //Localizando parametros inválidos 9
        regex = "(\\ <{3}(\\w+)\\>{2} )+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
         //Localizando parametros inválidos 10
        regex = "(\\ <{3}(\\w+)\\>{1} )+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
         //Localizando parametros inválidos 11
        regex = "(\\ <{2}(\\w+)\\>{2})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
        //Localizando parametros inválidos 12
        regex =  "(\\ <{2}(\\w+)\\>{2}[^>])";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
        //Localizando parametros inválidos 13
        regex =  "(\\ <{1}(\\w+)\\>{1}[^>])";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        
//        Localizando parametros inválidos 14
        regex =  "(\\ <{3}(\\W+|)>{3})";
        p = Pattern.compile(regex);
        m = p.matcher(texto);
        while (m.find()) {
            
            paramentrosInvalidos.add(m.group(2));
        }
        if(paramentrosInvalidos.size() > 0){
            for(int i = 0; i < paramentrosInvalidos.size();i++){
                 System.out.println("Parametros Invalidos"+paramentrosInvalidos.get(i).toString());
            }
        }
//         System.out.println(paramentrosInvalidos.get(0).toString());
        return paramentrosInvalidos;
    }
    
    public List<String> campoObrigatorios() throws SQLException{
        List<String> listCamposObrigatorios = new ArrayList<String>();
        listCamposObrigatorios = bd.getCamposObrigatorios();
        
        
        return listCamposObrigatorios;
    }
    
}
