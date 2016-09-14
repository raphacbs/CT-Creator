/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author raphael.da.silva
 */
public class CopiaPlanilha {
   private File origem; 
   private File destino;
   
   public CopiaPlanilha(File origem, File destino) throws FileNotFoundException, IOException{
       InputStream in = new FileInputStream(origem);
        OutputStream out = new FileOutputStream(destino);           // Transferindo bytes de entrada para saída
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
   }
}
