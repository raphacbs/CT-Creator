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
import com.accenture.log.MyLogger;
import com.accenture.ts.dao.SvnConnectionDao;
import com.accenture.util.ProjectSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class SplashScreen extends JWindow {

    private int duration;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(com.accenture.view.SplashScreen.class);
    private SvnConnectionDao connection;
    private SVNPropertiesVOBean filePropertiesLocal;
    private String caminhoBuild;
    final private double version = 1.00;
    private JLabel copyrt;
    private String user;
    private String pass;
    private boolean erroSVN = false;
    private final static Logger Log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static String fase;

    public SplashScreen(int d, String fase) throws IOException {
//        try {
        this.fase = fase;
        duration = d;
       // MyLogger.setup();
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
    public void showSplash() throws InterruptedException, IOException {

        try {

            ManipulaDadosSQLite bd;
            JPanel content = (JPanel) getContentPane();
            content.setBackground(new Color(237,237,237));
            StepPadrao stepPadrao = new StepPadrao();
            stepPadrao.setVersao(0.0);
            stepPadrao.setDescStep("exemplo");
            stepPadrao.setNomeStep("exemplo");
            stepPadrao.setTipoStepPadrao("exemplo");
            stepPadrao.setSistema("exemplo");
            stepPadrao.setResultadoStep("exemplo");

            File folderRes = new File("C:\\FastPlan\\res\\svn1.properties");
//            copyrt.setText("Verificando arquivo "+folderRes.getName());
            if (folderRes.exists()) {
                Log.setLevel(Level.INFO);
                Log.info("ARQUIVO DE ATUALIZAÇÃO DO SVN ENCONTRADO.");
//                mataProcessoUpdate();
                killWindowsUpgrade();

//                boolean b = new File("C:\\FastPlan\\res\\svn.properties").delete();
                File file = new File("C:\\FastPlan\\res\\svn.properties");

                BufferedReader br = null;
                FileInputStream flimp = null;
                flimp = new FileInputStream(file);
                br = new BufferedReader(new InputStreamReader(flimp, Charset.forName("ISO-8859-1")));

                flimp.close();

                br.close();

                boolean deletado = file.delete();

//                if (b) {
                Log.log(Level.INFO, "ARQUIVO ANTIGO DELETADO:" + deletado);
//                } else {
//                    //TODO del /F C:\\FastPlan\\res\\svn.properties
//                    String command = "cmd del /F C:\\\\FastPlan\\\\res\\\\svn.properties";
//                    Process p = Runtime.getRuntime().exec(command);
//                    Log.warning("ARQUIVO ANTIGO NAO FOI DELETADO");
//                }

                boolean renomeado = folderRes.renameTo(new File("C:\\FastPlan\\res\\svn.properties"));

                Log.log(Level.WARNING, "ARQUIVO ANTIGO NOMEADO: " + renomeado);

            }

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
            Log.log(Level.INFO, "Encoding Sistema: "+ System.getProperty("file.encoding"));
//            Field  charset = Charset.class.getDeclaredField("defaultCharset");
//            charset.setAccessible(true);
//            charset.set(null, null);
            // Espera ate que os recursos estejam carregados
            Thread.sleep(3000);
//            File folderRes = new File("C:\\FastPlan\\res\\svn1.properties");

//            try {
//                filePropertiesLocal = SVNPropertiesVOBean.getInstance();
//                copyrt.setText("Verificando usuário e senha svn...");
//                if (filePropertiesLocal.getUser().equals("") || filePropertiesLocal.getPass().equals("")) {
//                    criaJanelaLoginSvn();
//                }
//                connection = new SvnConnectionDao(this.fase);
//            } catch (SVNException ex) {
//                Log.log(Level.SEVERE, "ERROR", ex);
//                if (ex.getMessage().contains("E170001")) {
//                    JOptionPane.showMessageDialog(null, "Verifique o usuário e senha", "Usuário e/ou senha incorretos", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//                }
//
//                erroSVN = true;
//            } catch (IOException ex) {
//                Log.log(Level.SEVERE, "ERROR", ex);
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            } catch (SQLException ex) {
//                Log.log(Level.SEVERE, "ERROR", ex);
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            } catch (ClassNotFoundException ex) {
//                Log.log(Level.SEVERE, "ERROR", ex);
//                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            }

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
//                copyrt.setText("Verificando atualizações...");
//                if (erroSVN == false) {
//                    verifyUpdate();
//                }

                //setar campo CASO_TESTE como obrigatório
                bd.atualizaCamposObrigatorios("CASO_TESTE", 1);
                SVNPropertiesVOBean.getInstance().setComplexidade("Muito Alto|Alto|Medio|Baixo|Muito Baixo");

                
                //verifica se arquivo svn.properties foi atualizado
                copyrt.setText("Iniciando...");
                guiPrincipal = new MainScreenView();
                guiPrincipal.setVisible(true);
//            Thread.sleep(duration);

            } catch (Exception ex) {
                Log.log(Level.SEVERE, "ERROR", ex);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);

                JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: \n" + sw.toString() + ". Verifique o arquivo de log", "Erro: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                logger.error(ex);
                System.exit(1);

            }
            setVisible(false);

        } catch (SecurityException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            Logger
                    .getLogger(SplashScreen.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showSplashAndExit() {
        try {
            showSplash();
//        System.exit(0);        
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) throws IOException {

        // Mostra uma imagem com o título da aplicação 
        SplashScreen splash = new SplashScreen(10000,ProjectSettings.FASE_TS);
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
            connection.exportFileOrFolder("remote_svn.properties", "C:\\FastPlan\\temp\\", "conf", this.fase);

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
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void mataProcessoUpdate() {

        try {
            //Executa comando que lista todos os processos ativos de java
            Process p = Runtime.getRuntime().exec("jps");

            //Ler lista de processos
            InputStream is = p.getInputStream();
            InputStreamReader readerInput = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(readerInput);

            String linha = "";
            //Enquanto linha não for nula, exibe linha
            while ((linha = reader.readLine()) != null) {

                //Se existir o processo abaixo, divide a String em 2, PID e NomeProcesso.
                //nomeDoProcesso é o nome do processo que  mataremos. 
                if (linha.contains("Upgrade")) {

                    String[] getPID = linha.split(" ");

                    System.out.println("PID: " + getPID[0]);
                    System.out.println("Process name: " + getPID[1]);

                    String processName = getPID[1];

                    if (processName.contains("Upgrade")) {
                        //Mata Processo pelo pid
                        Runtime.getRuntime().exec("taskkill /f /pid " + getPID[0]);
                        Thread.sleep(600);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void killWindowsUpgrade() {
        try {
            String command = "cmd /c for /f \"tokens=2 delims=,\" %F in ('tasklist /nh /fi \"WINDOWTITLE eq Atualização CT Creator\" /fo csv') do @echo %~F";
            Process p = Runtime.getRuntime().exec(command);

            String result = "";

            InputStream is = p.getInputStream();
            InputStreamReader readerInput = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(readerInput);

            while ((result = reader.readLine()) != null) {
                Runtime.getRuntime().exec("taskkill /f /pid " + result);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

    }

}
