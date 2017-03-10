/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.control.ManipulaDadosSQLite;
import com.accenture.bean.StepPadrao;
import com.accenture.control.ExtraiPlanilha;
import com.accenture.ts.dao.SvnConnectionDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class SplashScreen extends JWindow {

    private int duration;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SplashScreen.class);
    private SvnConnectionDao connection;
    private SVNPropertiesVOBean filePropertiesLocal;
    private String caminhoBuild;
    final private double version = 1.00;
    private JLabel copyrt;
    private String user;
    private String pass;
    private boolean erroSVN = false;

    public SplashScreen(int d) {
//        try {
            duration = d;
//            filePropertiesLocal = new SVNPropertiesVOBean();
//            if (filePropertiesLocal.getUser().equals("") || filePropertiesLocal.getPass().equals("")) {
//
//                criaJanelaLoginSvn();
//
//            }
//            connection = new SvnConnectionDao();
//
//        } catch (SVNException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            erroSVN = true;
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        }
    }

// Este é um método simples para mostrar uma tela de apresentção
// no centro da tela durante a quantidade de tempo passada no construtor
    public void showSplash() throws InterruptedException {

        try {
            
          
                      
                    
            ManipulaDadosSQLite bd;
            JPanel content = (JPanel) getContentPane();
            content.setBackground(Color.lightGray);
            StepPadrao stepPadrao = new StepPadrao();
            stepPadrao.setVersao(0.0);
            stepPadrao.setDescStep("exemplo");
            stepPadrao.setNomeStep("exemplo");
            stepPadrao.setTipoStepPadrao("exemplo");
            stepPadrao.setSistema("exemplo");
            stepPadrao.setResultadoStep("exemplo");
            
            //captura lookandfeel  do windows
//            try {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//
//            } catch (IllegalAccessException ex) {
//                // TODO
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            } catch (ClassNotFoundException ex) {
//                // TODO
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            } catch (InstantiationException ex) {
//                // TODO
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            } catch (UnsupportedLookAndFeelException ex) {
//                // TODO
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            }
            // Configura a posição e o tamanho da janela
            int width = 400;
            int height = 300;
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width - width) / 2;
            int y = (screen.height - height) / 2;
            setBounds(x, y, width, height);
            // Constrói o splash screen
            JLabel label = new JLabel(new ImageIcon("res/logo_ct_creator.png"));
            copyrt = new JLabel("Copyright 2016, Accenture", JLabel.CENTER);
            Thread.sleep(3000);
            copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
            content.add(label, BorderLayout.CENTER);
            content.add(copyrt, BorderLayout.SOUTH);
            //        Color oraRed = new Color(156, 20, 20,  255);
//        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
// Torna visível
            setVisible(true);
            MainScreenView guiPrincipal;
//Seta o encoding

//            System.setProperty("file.encoding", "UTF-8");
            System.out.println(System.getProperty("file.encoding"));
            logger.info("Encoding Sistema: " + System.getProperty("file.encoding"));
//            Field  charset = Charset.class.getDeclaredField("defaultCharset");
//            charset.setAccessible(true);
//            charset.set(null, null);
            // Espera ate que os recursos estejam carregados
            
            File folderRes = new File("C:\\FastPlan\\res\\svn1.properties");
            copyrt.setText("Verificando arquivo "+folderRes.getName());
            if(folderRes.exists()){
                
                new File("C:\\FastPlan\\res\\svn.properties").delete();
                copyrt.setText("svn.properties deletado");
                folderRes.renameTo(new File("C:\\FastPlan\\res\\svn.properties"));
                copyrt.setText("svn.properties criado");
                Thread.sleep(3000);
                
            }

            try {
                filePropertiesLocal = new SVNPropertiesVOBean();
                copyrt.setText("Verificando usuário e senha svn...");
                if (filePropertiesLocal.getUser().equals("") || filePropertiesLocal.getPass().equals("")) {
                    
                    criaJanelaLoginSvn();

                }
                connection = new SvnConnectionDao();
            } catch (SVNException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                erroSVN = true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

            try {
                copyrt.setText("Iniciando Banco de Dados...");
                bd = new ManipulaDadosSQLite(true);
                copyrt.setText("Banco de Dados : OK");
                Thread.sleep(2000);
                copyrt.setText("Verificando Carga de configurações...");
                if (bd.verificaCargaConf()) {
                    copyrt.setText("Carga de configurações : OK");
                } else {
//                    JOptionPane.showMessageDialog(null, "Favor realizar uma carga de configurações. Na tela de importação: Importar/Exporta > Importar Cts / Configurações", "Importação", JOptionPane.INFORMATION_MESSAGE);
                    copyrt.setText("Importando dados de configurações da planilha de TI...");
                    ExtraiPlanilha ep = new ExtraiPlanilha();
                    ep.extraiConfPlanilha("C:\\FastPlan\\sheets\\TI.xlsx");
                    copyrt.setText("Importação concluída...");
                }
                if (!bd.getCountStepPadrao()) {
                    bd.insertStepPadrao(stepPadrao);
                }
                //verifica nova versão
                copyrt.setText("Verificando atualizações...");
                if (erroSVN == false) {
                    verifyUpdate();
                }

                //setar campo CASO_TESTE como obrigatório
                bd.atualizaCamposObrigatorios("CASO_TESTE", 1);
                SVNPropertiesVOBean svnProperties = new SVNPropertiesVOBean();
                svnProperties.setComplexidade("Muito Alto|Alto|Medio|Baixo|Muito Baixo");
                
                //verifica se arquivo svn.properties foi atualizado
            
            copyrt.setText("Iniciando...");
                guiPrincipal = new MainScreenView();
                guiPrincipal.setVisible(true);
//            Thread.sleep(duration);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            setVisible(false);

        } catch (SecurityException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showSplashAndExit() {
        try {
            showSplash();
//        System.exit(0);        
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        
         
        
        // Mostra uma imagem com o título da aplicação 
        SplashScreen splash = new SplashScreen(10000);
        splash.showSplashAndExit();
    }
    ImportSheetScreenView guiImportaPlanilha;

    private void criaJanelaImportaPlanilha() throws SQLException, ClassNotFoundException, IOException {
        guiImportaPlanilha = new ImportSheetScreenView();
        guiImportaPlanilha.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiImportaPlanilha.setVisible(true);
        setVisible(false);
        this.dispose();

    }

    LoginSvnView guiLoginSvn;

    private void criaJanelaLoginSvn() throws SQLException, ClassNotFoundException, IOException {
        guiLoginSvn = new LoginSvnView(this);
        guiLoginSvn.centralizaJanelaDialogo();
        guiLoginSvn.setVisible(true);
        while (!guiLoginSvn.isFinish()) {
            copyrt.setText("Aguardando usuário e senha svn...");
            System.out.println("JANELA LOGIN ABERTA!");
        }

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) throws IOException {
        this.user = user;
        filePropertiesLocal.setUser(user);
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) throws IOException {
        this.pass = pass;

        filePropertiesLocal.setPass(pass);
    }

    private void verifyUpdate() {
        try {

            String path = "C:\\FastPlan\\temp\\conf\\remote_svn.properties";
            FileInputStream file;
            connection.exportFileOrFolder("remote_svn.properties", "C:\\FastPlan\\temp\\", "conf");

            Properties fileProperties = new Properties();
            file = new FileInputStream(path);
            fileProperties.load(file);
            file.close();

            caminhoBuild = fileProperties.getProperty("caminhoBuild");
            double versionSVN = Double.parseDouble(fileProperties.getProperty("buildVersao"));

            if (versionSVN > filePropertiesLocal.getVersion() && fileProperties.getProperty("buildRequired").equals("false")) {
                if (JOptionPane.showConfirmDialog(null, "Existe versão nova disponível, deseja atualizar agora?\nVersão disponível: " + versionSVN + "\nVersão instalada: " + filePropertiesLocal.getVersion(), "CT Creator", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    copyrt.setText("Iniciando atualização");
                    Runtime.getRuntime().exec("java -jar Upgrade.jar");
                    filePropertiesLocal.setVersion(versionSVN);
                    System.exit(0);
                }

            } else if (versionSVN > filePropertiesLocal.getVersion() && fileProperties.getProperty("buildRequired").equals("true")) {
                JOptionPane.showMessageDialog(null, "Existem uma versão nova obrigatória disponível.\nVersão disponível: " + versionSVN + "\nVersão instalada: " + filePropertiesLocal.getVersion(), "CT Creator", JOptionPane.INFORMATION_MESSAGE);
                Runtime.getRuntime().exec("java -jar Upgrade.jar");
                filePropertiesLocal.setVersion(versionSVN);
                System.exit(0);
            }

        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

}
