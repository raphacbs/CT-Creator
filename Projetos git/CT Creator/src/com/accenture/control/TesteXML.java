/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author raphael.da.silva
 */
public class TesteXML {

    public static void main(String[] args) throws Exception {
        //Aqui você informa o nome do arquivo XML.  
//        File f = new File("c:/xml.xml");

// Adicionando comentario Raphael - testando conflito

        File f = new File("C:\\FastPlan\\alm.xml");

        //Criamos uma classe SAXBuilder que vai processar o XML4  
        SAXBuilder sb = new SAXBuilder();

        //Este documento agora possui toda a estrutura do arquivo.  
        Document d = sb.build(f);

        //Recuperamos o elemento root  
        Element mural = d.getRootElement();
        
        if(Integer.parseInt(mural.getAttributeValue("TotalResults")) == 0){
            System.out.println("A busca não retornou resultados");
        }else{

        //Recuperamos os elementos filhos (children)  
        List elements = mural.getChildren();
        List elements2 = mural.getChild("Entity").getChild("Fields").getChildren();
       
        
        

        Iterator i = elements.iterator();

        //Iteramos com os elementos filhos, e filhos do dos filhos  
//        while (i.hasNext()) {
//            Element element = (Element) i.next();
//            System.out.println("Códido:" + element.getChild("mensagem").getAttributeValue("id"));
//            System.out.println("Prioridade:" + element.getChild("mensagem").getAttributeValue("prioridade"));
//            System.out.println("Para:" + element.getChild("mensagem").getChildText("para"));
//            System.out.println("De:" + element.getChild("mensagem").getChildText("de"));
//            System.out.println("Corpo:" + element.getChild("mensagem").getChildText("corpo"));
//        }
         //Iteramos com os elementos filhos, e filhos do dos filhos  
//        while (i.hasNext()) {
//            Element element = (Element) i.next();
//            System.out.println("Type:" + element.getAttributeValue("Type"));
//            
//            System.out.println("Name:" +  element.getChild("Fields").getChild("Field").getAttributeValue("Name"));
//            
//            System.out.println("Value:" + element.getChild("Fields").getChild("Field").getChildText("Value"));
////            System.out.println("De:" + element.getChildText("de"));
////            System.out.println("Corpo:" + element.getChildText("corpo"));
//        }
        while (i.hasNext()) {
            
            org.jdom2.Element element = (org.jdom2.Element) i.next();
            System.out.println("Type:" + element.getAttributeValue("Type"));
            
                
            
            Iterator j = elements2.iterator();
            int cont = 0;
            while (j.hasNext()) {
                
                org.jdom2.Element element2 = (org.jdom2.Element) j.next();
//                System.out.println("Name:" + element2.getAttributeValue("Name"));
                
                System.out.println("Name:" + element.getChild("Fields").getChildren().get(cont).getAttributeValue("Name"));
                System.out.println("Value:" + element.getChild("Fields").getChildren().get(cont).getChildText("Value"));
                
                cont++;

            }
            
            
            
        }
    }
    }
}
