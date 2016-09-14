/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.alm;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

    private StringBuffer estruturaLida = new StringBuffer(200);
    private StringBuffer valorAtual = new StringBuffer(100);

    public void startDocument() {
        System.out.print("Iniciando leitura XML ...");
    }

    public void endDocument() {
        System.out.print("\n Finalizando leitura XML...");
    }

    public void startElement(String uri, String localName, String tag, Attributes atributos) {
        estruturaLida.append("/" + tag);
        System.out.print("\n<" + estruturaLida.substring(1) + (atributos.getLength() != 0 ? " +ATRIBUTOS" : "") + ">" );
        System.out.println(estruturaLida.substring(1) );
        valorAtual.delete(0, valorAtual.length());
    }

    public void endElement(String uri, String localName, String tag) {
        System.out.print(valorAtual.toString().trim());
        valorAtual.delete(0, valorAtual.length());
        estruturaLida.delete(estruturaLida.length() - tag.length() - 1, estruturaLida.length());
    }

    public void characters(char[] ch, int start, int length) {
        valorAtual.append(ch, start, length);
    }

    
   
}
