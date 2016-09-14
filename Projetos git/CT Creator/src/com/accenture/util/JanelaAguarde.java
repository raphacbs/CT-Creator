/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author raphael.da.silva
 */
public class JanelaAguarde extends JDialog{
    
        private JDialog janelaAguarde;
        JProgressBar progress = new JProgressBar();

   
       
    public  JanelaAguarde() {
//        
//         janelaAguarde = new JDialog( frame, true);
//        
//         new SwingWorker() {
//            @Override
//              protected Object doInBackground() throws Exception {
//           
//                setTitle("Aguarde...");
//                setAutoRequestFocus(true);
//                
//                progress.setIndeterminate(true);
//                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
//                setLocationRelativeTo(frame);
//                setSize(50, 50);
//                progress.setSize(400, 50);             
//                getContentPane().add(progress);
//                pack();
//                Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
//                setLocation((tela.width - getSize().width) / 2,
//                        (tela.height - getSize().height) / 2);
//                
//                setVisible(true);
//                 
//                 
////       
//                return null;
//              }
//             @Override
//            protected void done() {
//                
//            }
//        }.execute();
        
      
//        
         }
        
       
    

    public JDialog getDialogAguarde(Frame frame) {
                
                
              new JDialog(frame) ;
//        
//         new SwingWorker() {
//            @Override
//              protected Object doInBackground() throws Exception {
           
                setTitle("Aguarde...");
                setAutoRequestFocus(true);
                
                progress.setIndeterminate(true);
//                janelaAguarde.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
                setLocationRelativeTo(frame);
                setSize(50, 50);
                progress.setSize(400, 50);             
                getContentPane().add(progress);
                pack();
                Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
                setLocation((tela.width - getSize().width) / 2,
                        (tela.height - getSize().height) / 2);
                
                setVisible(true);
                 
                 return this;
//       
//                return null;
//              }
//             @Override
//            protected void done() {
//                
//            }
//        }.execute();
//
    }
//    
//    public void setVisible(boolean b){
//        janelaAguarde.setVisible(true);
//    }
    
    public void fecharJanela(){
        janelaAguarde.dispose();
        
    }
}
