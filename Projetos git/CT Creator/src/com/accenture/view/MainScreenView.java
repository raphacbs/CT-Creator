/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.ts.dao.TesteCaseTSDAO;
import com.accenture.ts.rn.TestCaseTSRN;
import java.awt.Color;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class MainScreenView extends javax.swing.JFrame {

    private JDesktopPane desktop; // = new JDesktopPane();; 

    /**
     * Creates new form Principal
     */
    public MainScreenView() {
        try {
            setTitle("CT Creator - Versão: " + new SVNPropertiesVOBean().getVersion());
            initComponents();

//        URL url = this.getClass().getResource("carregado.gif");
//        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
//        this.setIconImage(null);
            this.setIconImage(ImageIO.read(new File("res/logo_ctcreator.png")));
        } catch (IOException ex) {
            //Nao deve acontecer se o icone estiver no lugar certo.
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        desktop = new JDesktopPane();
        setDesktop(desktop);
        desktop.setBackground(Color.LIGHT_GRAY);
        setContentPane(desktop);
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        setExtendedState(MAXIMIZED_BOTH);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCT = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        itemMenuNovoCT = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itemMenuCTExistente = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        itemMenuPesquisaCT = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        itemMenuConsultaCTAlm = new javax.swing.JMenuItem();
        menuImportarExportar = new javax.swing.JMenu();
        itemMenuImportaStepPadrao = new javax.swing.JMenuItem();
        itemMenuImportaCT = new javax.swing.JMenuItem();
        itemMenuExportarPlanilhaTI = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItemFuncionalidade = new javax.swing.JMenuItem();
        menuMatrizRastreabilidade = new javax.swing.JMenu();
        itemMenuComponentes = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuItemCT = new javax.swing.JMenuItem();
        menuConfiguracoes = new javax.swing.JMenu();
        itemMenuConfiguracoes = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("principal"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenuBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseClicked(evt);
            }
        });

        menuCT.setText("Casos de Teste");
        menuCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCTActionPerformed(evt);
            }
        });

        jMenu1.setText("Novo");

        itemMenuNovoCT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        itemMenuNovoCT.setText("TI");
        itemMenuNovoCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuNovoCTActionPerformed(evt);
            }
        });
        jMenu1.add(itemMenuNovoCT);

        jMenuItem2.setText("TS");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        menuCT.add(jMenu1);

        jMenu2.setText("Novo a partir de existente");

        itemMenuCTExistente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        itemMenuCTExistente.setText("TI");
        itemMenuCTExistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuCTExistenteActionPerformed(evt);
            }
        });
        jMenu2.add(itemMenuCTExistente);

        jMenuItem3.setText("TS");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        menuCT.add(jMenu2);

        jMenu3.setText("Pesquisar/Editar");

        itemMenuPesquisaCT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        itemMenuPesquisaCT.setText("TI");
        itemMenuPesquisaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuPesquisaCTActionPerformed(evt);
            }
        });
        jMenu3.add(itemMenuPesquisaCT);

        jMenuItem4.setText("TS");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        menuCT.add(jMenu3);

        itemMenuConsultaCTAlm.setText("Consultar CT no ALM (beta)");
        itemMenuConsultaCTAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuConsultaCTAlmActionPerformed(evt);
            }
        });
        menuCT.add(itemMenuConsultaCTAlm);

        jMenuBar1.add(menuCT);

        menuImportarExportar.setText("Importar/Exportar");

        itemMenuImportaStepPadrao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        itemMenuImportaStepPadrao.setText("Importar Steps Padrão");
        itemMenuImportaStepPadrao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuImportaStepPadraoActionPerformed(evt);
            }
        });
        menuImportarExportar.add(itemMenuImportaStepPadrao);

        itemMenuImportaCT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        itemMenuImportaCT.setText("Importar CTs / Configurações");
        itemMenuImportaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuImportaCTActionPerformed(evt);
            }
        });
        menuImportarExportar.add(itemMenuImportaCT);

        itemMenuExportarPlanilhaTI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        itemMenuExportarPlanilhaTI.setText("Exportar para planilha de carga TI");
        itemMenuExportarPlanilhaTI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuExportarPlanilhaTIActionPerformed(evt);
            }
        });
        menuImportarExportar.add(itemMenuExportarPlanilhaTI);

        jMenuItem5.setText("Instânciar CTs e exportar planilha TS");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        menuImportarExportar.add(jMenuItem5);

        jMenuBar1.add(menuImportarExportar);

        jMenu4.setText("Fluxos");

        jMenuItemFuncionalidade.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemFuncionalidade.setText("Gerenciar Fluxos");
        jMenuItemFuncionalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFuncionalidadeActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemFuncionalidade);

        jMenuBar1.add(jMenu4);

        menuMatrizRastreabilidade.setText("Automação");

        itemMenuComponentes.setText("Componentes");
        itemMenuComponentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuComponentesActionPerformed(evt);
            }
        });
        menuMatrizRastreabilidade.add(itemMenuComponentes);

        jMenuBar1.add(menuMatrizRastreabilidade);

        jMenu5.setText("Relatórios");

        menuItemCT.setText("Caso de Testes");
        menuItemCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCTActionPerformed(evt);
            }
        });
        jMenu5.add(menuItemCT);

        jMenuBar1.add(jMenu5);

        menuConfiguracoes.setText("Configurações");
        menuConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfiguracoesActionPerformed(evt);
            }
        });

        itemMenuConfiguracoes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        itemMenuConfiguracoes.setText("Opções");
        itemMenuConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuConfiguracoesActionPerformed(evt);
            }
        });
        menuConfiguracoes.add(itemMenuConfiguracoes);

        jMenuBar1.add(menuConfiguracoes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 637, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCTActionPerformed

    }//GEN-LAST:event_menuCTActionPerformed

    private void itemMenuNovoCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuNovoCTActionPerformed
        // TODO add your handling code here:
        final JFrame GUIPrincipal = this;
        new SwingWorker() {
            JDialog aguarde = new WaitScreenView(GUIPrincipal, true);

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                aguarde.setModal(true);
                criaJanelaTelaCadCT();
                return null;
            }

            @Override
            protected void done() {
//                aguarde.setModal(false);
                aguarde.dispose();
            }

        }.execute();


    }//GEN-LAST:event_itemMenuNovoCTActionPerformed

    private void itemMenuCTExistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCTExistenteActionPerformed
        try {
            criaJanelaSelecionaCT();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuCTExistenteActionPerformed

    private void itemMenuImportaStepPadraoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuImportaStepPadraoActionPerformed
        try {
            criaJanelaTelaImportaStepPadrao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuImportaStepPadraoActionPerformed

    private void itemMenuPesquisaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuPesquisaCTActionPerformed
        try {
            // TODO add your handling code here:
//            criaJanelaMantemCT();
            criaJanelaFiltro2();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuPesquisaCTActionPerformed

    private void itemMenuImportaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuImportaCTActionPerformed
        try {
            criaJanelaImportaPlanilha();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuImportaCTActionPerformed

    private void itemMenuExportarPlanilhaTIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuExportarPlanilhaTIActionPerformed
        try {
            criaJanelaTelaExportaPlanilha();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuExportarPlanilhaTIActionPerformed

    private void itemMenuConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuConfiguracoesActionPerformed
        try {
            criaJanelaTelaConfiguracoes();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuConfiguracoesActionPerformed

    private void itemMenuConsultaCTAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuConsultaCTAlmActionPerformed
        try {
            // TODO add your handling code here:
            criaJanelaConsultaCTALM();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuConsultaCTAlmActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaEditScreenTSView();
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        final JFrame GUIPrincipal = this;
        new SwingWorker() {
            JDialog aguarde = new WaitScreenView(GUIPrincipal, true);

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                aguarde.setModal(true);
                criaJanelaRegisterScreenTSView();
                return null;
            }

            @Override
            protected void done() {
//                aguarde.setModal(false);
                aguarde.dispose();
            }

        }.execute();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfiguracoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuConfiguracoesActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaSelecionaCtTs();
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaExportarTs();
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuBar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuBar1MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JInternalFrame[] j = desktop.getAllFrames();

        if (j.length > 0) {
            JOptionPane.showMessageDialog(null, "Favor fechar todas as janelas internas. ", "Alerta", JOptionPane.WARNING_MESSAGE);
            repaint();

        } else {
            try {
                new TestCaseTSRN().deleteDir("");
            } catch (IOException ex) {
                System.out.println("com.accenture.view.MainScreenView.formWindowClosing() - " + ex.toString());
                dispose();
                System.exit(0); //calling the method is a must
            } catch (SVNException ex) {
                System.out.println("com.accenture.view.MainScreenView.formWindowClosing() - " + ex.toString());
                dispose();
                System.exit(0); //calling the method is a must
            }
            dispose();
            System.exit(0); //calling the method is a must
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemFuncionalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFuncionalidadeActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaFuncionalidade();
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemFuncionalidadeActionPerformed

    private void menuItemCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCTActionPerformed
        try {
            criaJanelaReport();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_menuItemCTActionPerformed

    private void itemMenuComponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuComponentesActionPerformed
        try {
            criaJanelaComponentes();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuComponentesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreenView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemMenuCTExistente;
    private javax.swing.JMenuItem itemMenuComponentes;
    private javax.swing.JMenuItem itemMenuConfiguracoes;
    private javax.swing.JMenuItem itemMenuConsultaCTAlm;
    private javax.swing.JMenuItem itemMenuExportarPlanilhaTI;
    private javax.swing.JMenuItem itemMenuImportaCT;
    private javax.swing.JMenuItem itemMenuImportaStepPadrao;
    private javax.swing.JMenuItem itemMenuNovoCT;
    private javax.swing.JMenuItem itemMenuPesquisaCT;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItemFuncionalidade;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu menuCT;
    private javax.swing.JMenu menuConfiguracoes;
    private javax.swing.JMenu menuImportarExportar;
    private javax.swing.JMenuItem menuItemCT;
    private javax.swing.JMenu menuMatrizRastreabilidade;
    // End of variables declaration//GEN-END:variables

    RegisterScreenTIView guiJanelaCadCT;

    ChooseTestCaseScreenView guiJanelaSelecionaCT;
    EditStepDefaultScreenView guiJanelaManterStepPadrao;
    ImportStepDefaultScreenView guiImportaStepPadrao;
    EditTestCaseScreenTIView guiMantemCt;
    ImportSheetScreenView guiImportaPlanilha;
    ExportSheetScreenTIView guiExportaPlanilha;
    SettingsScreenView guiConfiguracoes;
    FilterTestCaseScreen2View guiFiltroCT2;
    SearchAlmScreenView guiConsultaALM;
    RegisterScreenTSView guiCadTS;
    EditScreenTSView guiEdit;
    ChooseTestCaseTsScreenView guiChooseTestCaseTsScreenView;
    InstanceScreenTSView guiInstaceTs;
    ManageflowsScreenView guiManageflowsScreenView;
    FilterReportScreenView guiFilterReportScreenView;
    ManageComponentsScreenView guiComponentsScreenView;

    public void criaJanelaReport() throws IOException, ClassNotFoundException, SQLException, SVNException {
        guiFilterReportScreenView = new FilterReportScreenView();
        desktop.add(guiFilterReportScreenView);
        guiFilterReportScreenView.centralizaJanela();
        guiFilterReportScreenView.setVisible(true);
    }

    public void criaJanelaExportarTs() throws IOException, ClassNotFoundException, SQLException, SVNException {
        guiInstaceTs = new InstanceScreenTSView();
        desktop.add(guiInstaceTs);
        guiInstaceTs.centralizaJanela();
        guiInstaceTs.setVisible(true);
    }

    public void criaJanelaEditScreenTSView() throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiEdit = new EditScreenTSView();
        desktop.add(guiEdit);
        guiEdit.centralizaJanela();
        guiEdit.setVisible(true);
    }

    public void criaJanelaRegisterScreenTSView() throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiCadTS = new RegisterScreenTSView();
        desktop.add(guiCadTS);
        guiCadTS.centralizaJanela();
        guiCadTS.setVisible(true);
    }

    public void criaJanelaTelaConfiguracoes() throws IOException, ClassNotFoundException, SQLException {

        guiConfiguracoes = new SettingsScreenView();
        guiConfiguracoes.setDesktop(this);
        desktop.add(guiConfiguracoes);
        guiConfiguracoes.centralizaJanela();
        guiConfiguracoes.setVisible(true);

    }

    public void criaJanelaTelaExportaPlanilha() throws IOException, ClassNotFoundException, SQLException {

        guiExportaPlanilha = new ExportSheetScreenTIView();
        desktop.add(guiExportaPlanilha);
        guiExportaPlanilha.centralizaJanela();
        guiExportaPlanilha.setVisible(true);
    }

    public void criaJanelaTelaImportaStepPadrao() throws IOException, ClassNotFoundException, SQLException {

        guiImportaStepPadrao = new ImportStepDefaultScreenView();
        desktop.add(guiImportaStepPadrao);
        guiImportaStepPadrao.centralizaJanela();

        guiImportaStepPadrao.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiImportaStepPadrao.setVisible(true);
        System.out.println("-**-criou-**-");

    }

    public void criaJanelaTelaCadCT() throws IOException, ClassNotFoundException, SQLException {

        guiJanelaCadCT = new RegisterScreenTIView();
        desktop.add(guiJanelaCadCT);
        guiJanelaCadCT.centralizaJanela();

        guiJanelaCadCT.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaCadCT.setVisible(true);
        System.out.println("-**-criou-**-");

    }

//    private void criaJanelaTelaImportacao() {
//
//        if (guiJanelaImportacao == null) { 
//            guiJanelaImportacao = new GUIImportacao();
//            desktop.add(guiJanelaImportacao);
//            guiJanelaImportacao.setVisible(true);
//
//            guiJanelaImportacao.setDefaultCloseOperation(HIDE_ON_CLOSE);
//            guiJanelaImportacao.setVisible(true);
//
//        }
//    }
    private void criaJanelaSelecionaCT() throws SQLException, ClassNotFoundException {

        guiJanelaSelecionaCT = new ChooseTestCaseScreenView();
        desktop.add(guiJanelaSelecionaCT);
        guiJanelaSelecionaCT.centralizaJanela();
        guiJanelaSelecionaCT.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaSelecionaCT.setVisible(true);

    }

    private void criaJanelaSelecionaCtTs() throws SQLException, ClassNotFoundException {

        guiChooseTestCaseTsScreenView = new ChooseTestCaseTsScreenView();
        desktop.add(guiChooseTestCaseTsScreenView);
        guiChooseTestCaseTsScreenView.centralizaJanela();
        guiChooseTestCaseTsScreenView.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiChooseTestCaseTsScreenView.setVisible(true);

    }

    private void criaJanelaStepPadrao() throws SQLException, ClassNotFoundException, IOException {

        guiJanelaManterStepPadrao = new EditStepDefaultScreenView();
        desktop.add(guiJanelaManterStepPadrao);
        guiJanelaManterStepPadrao.centralizaJanela();
        guiJanelaManterStepPadrao.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaManterStepPadrao.setVisible(true);

    }

    private void criaJanelaMantemCT() throws SQLException, ClassNotFoundException, IOException {

        guiMantemCt = new EditTestCaseScreenTIView();
        desktop.add(guiMantemCt);
        guiMantemCt.centralizaJanela();
        guiMantemCt.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiMantemCt.setVisible(true);

    }

    private void criaJanelaFiltro2() throws SQLException, ClassNotFoundException, IOException {

        guiFiltroCT2 = new FilterTestCaseScreen2View();
        desktop.add(guiFiltroCT2);
        guiFiltroCT2.centralizaJanela();

        guiFiltroCT2.setVisible(true);

    }

    private void criaJanelaImportaPlanilha() throws SQLException, ClassNotFoundException, IOException {
        guiImportaPlanilha = new ImportSheetScreenView();
        desktop.add(guiImportaPlanilha);
        guiImportaPlanilha.centralizaJanela();
        guiImportaPlanilha.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiImportaPlanilha.setVisible(true);

    }

    private void criaJanelaConsultaCTALM() throws SQLException, ClassNotFoundException, IOException {
        guiConsultaALM = new SearchAlmScreenView();
        desktop.add(guiConsultaALM);
        guiConsultaALM.centralizaJanela();

        guiConsultaALM.setVisible(true);

    }

    private void criaJanelaFuncionalidade() throws SQLException, ClassNotFoundException, IOException, SVNException {
        guiManageflowsScreenView = new ManageflowsScreenView();
        desktop.add(guiManageflowsScreenView);
        guiManageflowsScreenView.centralizaJanela();

        guiManageflowsScreenView.setVisible(true);

    }

    private void criaJanelaComponentes() throws SQLException, ClassNotFoundException, IOException, SVNException {
        guiComponentsScreenView = new ManageComponentsScreenView();
        desktop.add(guiComponentsScreenView);
        guiComponentsScreenView.centralizaJanela();
        guiComponentsScreenView.setVisible(true);

    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public MainScreenView getPrincipal() {
        return this;
    }

    public void removeTela(JInternalFrame intFrame) {
        intFrame.setVisible(false);
        desktop.remove(intFrame);
    }

    /*
    @Override
    public synchronized void addWindowListener(WindowListener wl) {
       // super.addWindowListener(wl); //To change body of generated methods, choose Tools | Templates.
        JInternalFrame [] j = desktop.getAllFrames();
           
           if(j.length > 0 ){
               JOptionPane.showMessageDialog(null, "Favor fechar todas as janelas internas. ", "Alerta", JOptionPane.WARNING_MESSAGE);
           }else{
               System.out.println(".windowClosing()");
               dispose();
               System.exit(0); //calling the method is a must
           }
    }
     */
}
