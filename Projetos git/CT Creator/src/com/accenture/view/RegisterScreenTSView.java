/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.bean.Step;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.log.MyLogger;
import com.accenture.ts.dao.TestPlanTSDao;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.util.CabecalhoJTableCheckBox;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.tmatesoft.svn.core.SVNException;
import com.accenture.util.TextAreaCellEditor;
import com.accenture.util.TextAreaCellRenderer;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author raphael.da.silva
 */
public class RegisterScreenTSView extends javax.swing.JInternalFrame {

    private SvnConnectionRN svnRN;
    private TestCaseTSRN testCaseRN;
    private int numeroStep;
    private List<Step> listSteps;
    private int lineSelect = -1;
    private boolean headerColumnAll = false;
    private final static Logger Log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String fase;

    /**
     * Creates new form guiCadTS
     */
    public RegisterScreenTSView(String fase) throws IOException {

        initComponents();
        this.fase = fase;
        setTitle("Cadastro de CT "+fase);
     //   MyLogger.setup();
        Log.setLevel(Level.INFO);
        listSteps = new ArrayList<Step>();
        final JFrame GUIPrincipal = (JFrame) getParent();
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception, SVNException, IOException {

                try {
//                JOptionPane.showMessageDialog(null, "Iniciando carregamento do combobox", "IOException", JOptionPane.ERROR_MESSAGE);
                    loadComboTS();
//                    JOptionPane.showMessageDialog(null, "carregamento ok combobox", "IOException", JOptionPane.ERROR_MESSAGE);
                    addIconInButton();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage() + " \nCausa: Desconhecida"
                            + "\nSolução: Deconhecida", "IOException", JOptionPane.ERROR_MESSAGE);
                    exceptionSVN(ex.getMessage());

                }

                return null;
            }

            @Override
            protected void done() {

            }

        }.run();

        new SwingWorker() {

            @Override
            protected Object doInBackground() throws IOException, SVNException {
                renderTableStep();

                return null;
            }

            @Override
            protected void done() {
                recoveredTestCase();
            }

        }.run();

//       tabelaSteps.setDefaultEditor(String.class, new DefaultCellEditor(new JTextField()){
//     @Override
//     public Component getTableCellEditorComponent(JTable table,
//              Object value, boolean isSelected, int row, int column) {
//        // code on line below is redundant but would be needed if need to see
//        // other property of the value object than toString()
//        String valueStr = (value == null) ? "null" : value.toString();
//        System.out.printf("[%d, %d]: %s%n", row, column, valueStr);
//        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
//     }
//
//     @Override
//     public Object getCellEditorValue() {
//        System.out.printf("cell editor value: %s%n", super.getCellEditorValue());
//        return super.getCellEditorValue();
//     }
//  });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupTabelaStep = new javax.swing.JPopupMenu();
        jComboSistemasTS = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextNameTS = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboFaseCR = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescriptionTS = new javax.swing.JTextArea();
        bntAddStep = new javax.swing.JButton();
        bntDeleteStep = new javax.swing.JButton();
        bntMudaStepSubir = new javax.swing.JButton();
        bntMudaStepDescer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaSteps = new javax.swing.JTable();
        bntSalvar = new javax.swing.JButton();
        bntCancelar = new javax.swing.JButton();
        bntColar = new javax.swing.JButton();
        bntCopiar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboComplexidade = new javax.swing.JComboBox<>();
        jCheckBoxAutomatizado = new javax.swing.JCheckBox();

        JMenuItem menuItemCopyAll = new JMenuItem("Copiar para clipboard");
        menuItemCopyAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyClipboardStep();
            }
        });
        jPopupTabelaStep.add(menuItemCopyAll);
        JMenuItem menuItemCopySelected = new JMenuItem("Copiar selecinado para clipboard");
        menuItemCopySelected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyClipboardStep();
            }
        });
        jPopupTabelaStep.add(menuItemCopySelected);

        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de CT");
        setPreferredSize(new java.awt.Dimension(1200, 650));

        jLabel1.setText("Sistema:");

        jLabel2.setText("Nome CT: ");

        jTextNameTS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextNameTSFocusLost(evt);
            }
        });
        jTextNameTS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextNameTSKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNameTSKeyTyped(evt);
            }
        });

        jLabel3.setText("Fase:");

        jLabel4.setText("Descrição:");

        jTextAreaDescriptionTS.setColumns(20);
        jTextAreaDescriptionTS.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDescriptionTS);

        bntAddStep.setText("Add Step");
        bntAddStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAddStepMouseClicked(evt);
            }
        });

        bntDeleteStep.setText("Deletar Step");
        bntDeleteStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntDeleteStepMouseClicked(evt);
            }
        });

        bntMudaStepSubir.setText("Move UP");
        bntMudaStepSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntMudaStepSubirMouseClicked(evt);
            }
        });

        bntMudaStepDescer.setText("Move Down");
        bntMudaStepDescer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntMudaStepDescerMouseClicked(evt);
            }
        });

        tabelaSteps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Step", "Descrição", "Resultado Esperado", "Todos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaSteps.setColumnSelectionAllowed(true);
        tabelaSteps.getTableHeader().setReorderingAllowed(false);
        tabelaSteps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaStepsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaSteps);
        //TableColumn col;
        //           for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
            //            col = tabelaSteps.getColumnModel().getColumn(i);
            //            col.setCellRenderer(new TextAreaCellRenderer());
            //        }
        tabelaSteps.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tabelaSteps.getColumnModel().getColumnCount() > 0) {
            tabelaSteps.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(0).setMaxWidth(100);
            tabelaSteps.getColumnModel().getColumn(3).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(3).setMaxWidth(80);
        }
        tabelaSteps.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor());
        tabelaSteps.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor());

        bntSalvar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        bntSalvar.setText("Salvar");
        bntSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntSalvarMouseClicked(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCancelarMouseClicked(evt);
            }
        });

        bntColar.setText("Colar");
        bntColar.setEnabled(false);
        bntColar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntColarMouseClicked(evt);
            }
        });

        bntCopiar.setText("Copiar");
        bntCopiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCopiarMouseClicked(evt);
            }
        });
        bntCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCopiar1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Complexidade:");

        jCheckBoxAutomatizado.setText("Automatizado?");
        jCheckBoxAutomatizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAutomatizadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                        .addComponent(bntSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 372, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextNameTS, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboComplexidade, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jCheckBoxAutomatizado)
                                    .addGap(35, 35, 35)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboFaseCR, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bntAddStep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntDeleteStep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntMudaStepSubir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntMudaStepDescer)
                                .addGap(12, 12, 12)
                                .addComponent(bntCopiar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntColar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextNameTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboComplexidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxAutomatizado)
                            .addComponent(jLabel3)
                            .addComponent(jComboFaseCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntAddStep)
                            .addComponent(bntDeleteStep)
                            .addComponent(bntMudaStepSubir)
                            .addComponent(bntMudaStepDescer)
                            .addComponent(bntColar)
                            .addComponent(bntCopiar)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntAddStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAddStepMouseClicked
        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tabelaSteps.editingStopped(null);
            //model da tabela step
            DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
            //add nova linha na tabela
            model.addRow(new String[]{"Step " + 1, "<<<parametro>>>", "<<<parametro>>>"});
            model.setValueAt(false, tabelaSteps.getRowCount() - 1, 3);

            //ordena a numeração dos steps
            int numLinhas = model.getRowCount();
            for (int j = 0; j < numLinhas; j++) {
                numeroStep = j + 1;
                System.out.println("Antes: " + model.getValueAt(j, 0));
                model.setValueAt("Step " + numeroStep, j, 0);
                System.out.println("Depois: " + model.getValueAt(j, 0));

            }

//            calculaComplexidade();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JScrollBar bar = jScrollPane2.getVerticalScrollBar();
                    bar.setValue(bar.getMaximum());
                }
            });
        }

    }//GEN-LAST:event_bntAddStepMouseClicked

    private void bntDeleteStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntDeleteStepMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = tabelaSteps.getRowCount();
        int qtdSelecionados = 0;

        String nomeStepSelecionado = null;

        for (int i = 0; i < countRow; i++) {
            if (tabelaSteps.getValueAt(i, 3) != null && model.getValueAt(i, 3).toString().equals("true")) {
                qtdSelecionados++;
                nomeStepSelecionado = model.getValueAt(i, 0).toString();

            }
        }

        if (qtdSelecionados > 0) {

            if (qtdSelecionados == 1) {

                if (JOptionPane.showConfirmDialog(this, "Deseja excluir o " + nomeStepSelecionado + "?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
                        if (tabelaSteps.getValueAt(i, 3) != null && model.getValueAt(i, 3).toString().equals("true")) {
                            tabelaSteps.editingStopped(null);
                            model.removeRow(i);

                        }
                    }

                } else {
                    //exclusão cancelada
                }

            } else if (JOptionPane.showConfirmDialog(this, "Deseja excluir os steps selecionados?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (int i = countRow - 1; i >= 0; i--) {
                    if (tabelaSteps.getValueAt(i, 3) != null && model.getValueAt(i, 3).toString().equals("true")) {
                        tabelaSteps.editingStopped(null);
                        model.removeRow(i);

                    }
                }

            } else {
                //exclusão cancelada
            }
        } else {
            JOptionPane.showMessageDialog(this, "Favor selecionar Step que deseja excluir.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }

        if (model.getRowCount() == 0) {

        } else {

            //String numeroStep = model.getValueAt(0, i);
            int numLinhas = model.getRowCount();
            for (int j = 0; j < numLinhas; j++) {
                numeroStep = j + 1;
                System.out.println("Antes: " + model.getValueAt(j, 0));
                model.setValueAt("Step " + numeroStep, j, 0);
                System.out.println("Depois: " + model.getValueAt(j, 0));
                //                   numeroStep = Integer.parseInt(numStepTemp.substring(5,6));

            }

        }

        lineSelect = -1;

    }//GEN-LAST:event_bntDeleteStepMouseClicked

    private void bntMudaStepSubirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntMudaStepSubirMouseClicked
        moveUp();


    }//GEN-LAST:event_bntMudaStepSubirMouseClicked

    private void bntMudaStepDescerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntMudaStepDescerMouseClicked
        moveDown();

    }//GEN-LAST:event_bntMudaStepDescerMouseClicked

    private void bntSalvarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntSalvarMouseClicked
        //CR 15125 - INICIO - DIMINUIDO O TAMANHO MÍNIMO E AUMENTADO O TAMANHO MÁXIMO
        if (jTextNameTS.getText().length() >= 5 && jTextNameTS.getText().length() <= 80) {
            final Frame GUIPrincipal = new MainScreenView();
            final JInternalFrame ji = this;

            new SwingWorker() {
                JDialog aguarde = new WaitScreenView((JFrame) GUIPrincipal, true, ji);

                @Override
                protected Object doInBackground() throws Exception, SVNException, IOException {
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    aguarde.setLocationRelativeTo(GUIPrincipal);
                    aguarde.setVisible(true);
                    blockedFieldBnt(false);
                    addTCSVN();

                    return null;
                }

                @Override
                protected void done() {
                    aguarde.dispose();
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }.execute();
        } else if (jTextNameTS.getText().length() < 5) {
            JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: O nome do caso de teste tem menos de 5 caracteres."
                    + "\nSolução: Altere o nome caso de teste com mais de 5 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: O nome do caso de teste tem mais de 80 caracteres."
                    + "\nSolução: Altere o nome caso de teste com menos de 80 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);

        }


    }//GEN-LAST:event_bntSalvarMouseClicked

    private void bntCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntCancelarMouseClicked
        if (JOptionPane.showConfirmDialog(this, "Ao sair da tela seus dados serão perdidos, deseja realmente sair?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                testCaseRN = new TestCaseTSRN(this.fase);
                testCaseRN.deleteDir(this.hashCode() + "");

                dispose();
            } catch (SVNException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getErrorMessage(), "ERRO SVN", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_bntCancelarMouseClicked

    private void tabelaStepsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaStepsMouseClicked

        int column = tabelaSteps.columnAtPoint(evt.getPoint());
        int row = tabelaSteps.rowAtPoint(evt.getPoint());

        //instancia o model da tabela de steps
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        if (evt.getButton() == MouseEvent.BUTTON3) {
            System.out.println("botão direito!");
            System.out.println("x: " + evt.getX() + " y: " + evt.getY());
            jPopupTabelaStep.show(tabelaSteps, evt.getX(), evt.getY());

        } else if (column != 3) {

            if (true) {
                for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
                    model.setValueAt(false, i, 3);

                }
            }
            //verifica se existe alguma linha selecionada
            if (lineSelect != -1) {
                //caso exista linha selecionada o checkbox e desmarcado 
                model.setValueAt(false, lineSelect, 3);
                lineSelect = tabelaSteps.getSelectedRow();
                //é marcado o checkbox da linha atual
                model.setValueAt(true, lineSelect, 3);
            } else {
                //marca checkbox da linha atual

                model.setValueAt(true, row, 3);
                lineSelect = row;
            }

        }

//        tabelaSteps.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor());
//        tabelaSteps.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor());

    }//GEN-LAST:event_tabelaStepsMouseClicked

    private void bntColarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntColarMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = tabelaSteps.getRowCount();

        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < listSteps.size(); i++) {
                model.addRow(new String[]{"Step " + 1, listSteps.get(i).getDescStep(), listSteps.get(i).getResultadoStep()});

                //ordena a numeração dos steps
                int numLinhas = model.getRowCount();
                for (int j = 0; j < numLinhas; j++) {
                    numeroStep = j + 1;
                    model.setValueAt("Step " + numeroStep, j, 0);
                }
            }

        }
    }//GEN-LAST:event_bntColarMouseClicked

    private void bntCopiarMouseClicked(java.awt.event.MouseEvent evt) {

        if (tabelaSteps.getSelectedRowCount() > 0) {
            DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
            int countRow = tabelaSteps.getRowCount();

            tabelaSteps.editingStopped(null);
            listSteps.clear();
            for (int i = 0; i < countRow; i++) {
                Step s = new Step();
                if (tabelaSteps.getValueAt(i, 3) != null && model.getValueAt(i, 3).toString().equals("true")) {
                    s.setNomeStep(model.getValueAt(i, 0).toString());
                    s.setDescStep(model.getValueAt(i, 1).toString());
                    s.setResultadoStep(model.getValueAt(i, 2).toString());
                    listSteps.add(s);
                }
            }

            bntColar.setEnabled(true);
        }
    }

    private void jTextNameTSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextNameTSFocusLost
        boolean exist = false;

        SwingWorker sw = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception, SVNException, IOException {
                isExistsCT(jComboSistemasTS.getSelectedItem().toString(), jTextNameTS.getText());

                return null;
            }

            @Override
            protected void done() {

            }
        };

        sw.execute();


    }//GEN-LAST:event_jTextNameTSFocusLost

    private void bntCopiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCopiar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bntCopiar1ActionPerformed

    private void jTextNameTSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNameTSKeyReleased
        if (evt.getKeyChar() == '(' || evt.getKeyChar() == ')' || evt.getKeyChar() == '\\' || evt.getKeyChar() == '/') {
            evt.consume();
        }
    }//GEN-LAST:event_jTextNameTSKeyReleased

    private void jTextNameTSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNameTSKeyTyped
        if (evt.getKeyChar() == '(' || evt.getKeyChar() == ')' || evt.getKeyChar() == '\\' || evt.getKeyChar() == '/') {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextNameTSKeyTyped

    private void jCheckBoxAutomatizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAutomatizadoActionPerformed
        if (jCheckBoxAutomatizado.isSelected()) {
            if (!jComboSistemasTS.getSelectedItem().toString().isEmpty()) {
                new SwingWorker() {

                    @Override
                    protected Object doInBackground() throws Exception {
                        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        openScreenAddScript();

                        return null;
                    }

                    @Override
                    protected void done() {
                        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }

                }.execute();

            } else {

                JOptionPane.showMessageDialog(null, "Por favor selecione um Sistema para realizar a busca.", "Atenção", JOptionPane.WARNING_MESSAGE);

            }
        }
    }//GEN-LAST:event_jCheckBoxAutomatizadoActionPerformed

    private void openScreenAddScript() {
        try {

//            ChooseComponenteScreenView view = new ChooseComponenteScreenView(jComboSistemasTS.getSelectedItem().toString(), this, null, true);
//            view.centralizaJanela();
//            view.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(ManageComponentsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void loadComboTS() throws IOException, SVNException {

        testCaseRN = new TestCaseTSRN(this.fase);
//         JOptionPane.showMessageDialog(null, " testCaseRN = new TestCaseTSRN();", "Combo Sistema carregado!", JOptionPane.INFORMATION_MESSAGE);
        ArrayList systems = testCaseRN.systemsTestCase();
//        JOptionPane.showMessageDialog(null, " ArrayList systems = testCaseRN.systemsTestCase();", "Combo Sistema carregado!", JOptionPane.INFORMATION_MESSAGE);
        ArrayList fases = testCaseRN.faseCRTestCase();
//        JOptionPane.showMessageDialog(null, " ArrayList fases = testCaseRN.faseCRTestCase();", "Combo Sistema carregado!", JOptionPane.INFORMATION_MESSAGE);
        ArrayList complexidades = testCaseRN.complexidade();
//        JOptionPane.showMessageDialog(null, "  ArrayList complexidades = testCaseRN.complexidade();", "Combo Sistema carregado!", JOptionPane.INFORMATION_MESSAGE);

        for (int i = 0; i < systems.size(); i++) {
            jComboSistemasTS.addItem(systems.get(i).toString());
        }

//        JOptionPane.showMessageDialog(null, systems, "Combo Sistema carregado!", JOptionPane.INFORMATION_MESSAGE);
        for (int i = 0; i < fases.size(); i++) {
            jComboFaseCR.addItem(fases.get(i).toString());
        }

//        JOptionPane.showMessageDialog(null, fases, "Combo fases carregado!", JOptionPane.INFORMATION_MESSAGE);
        for (int i = 0; i < complexidades.size(); i++) {
            jComboComplexidade.addItem(complexidades.get(i).toString());
        }

//        JOptionPane.showMessageDialog(null, complexidades, "Combo complexidades carregado!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void organizaNumeracaoStep(int numLinhas, DefaultTableModel model) {
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            System.out.println("Antes: " + model.getValueAt(j, 0));
            model.setValueAt("Step " + numeroStep, j, 0);
            System.out.println("Depois: " + model.getValueAt(j, 0));
            //                   numeroStep = Integer.parseInt(numStepTemp.substring(5,6));

        }
    }

    private void addTCSVN() {
        try {
            boolean existeCT = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date(System.currentTimeMillis());

            List<Step> listStep = new ArrayList<Step>();

            testCaseRN = new TestCaseTSRN(this.fase);
            svnRN = new SvnConnectionRN(this.fase);
            testCaseRN.getTsDao().getTestCase().setTestScriptName(jTextNameTS.getText());
            testCaseRN.getTsDao().getTestCase().setProduct(jComboSistemasTS.getSelectedItem().toString());
            testCaseRN.getTsDao().getTestCase().setComplexidade(jComboComplexidade.getSelectedItem().toString()); //add complexidade
            testCaseRN.getTsDao().getTestCase().setTestScriptDescription(jTextAreaDescriptionTS.getText() + "\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>");
            testCaseRN.getTsDao().getTestCase().setAutomatizado(jCheckBoxAutomatizado.isSelected());
            testCaseRN.getTsDao().getTestCase().setDataPlanejada(d);
            tabelaSteps.editingStopped(null);
            for (int cont = 0; cont < tabelaSteps.getRowCount(); cont++) {
                Step step = new Step();
                step.setNomeStep((tabelaSteps.getValueAt(cont, 0)).toString());
                step.setDescStep((tabelaSteps.getValueAt(cont, 1)).toString());
                step.setResultadoStep((tabelaSteps.getValueAt(cont, 2)).toString());
                listStep.add(step);
            }
            testCaseRN.getTsDao().getTestCase().setListStep(listStep);
            Log.info("SALVANDO CT : "+jTextNameTS.getText());
            
            
            List<TestCaseTSPropertiesBean> listProperties = new SvnConnectionRN(this.fase).search(testCaseRN.getTsDao().getTestCase().getProduct(), jTextNameTS.getText());
            if (listProperties.isEmpty()) {
                svnRN.addTestCaseSVN(testCaseRN.getTsDao().getTestCase(), "CT CADASTRADO VIA CT CREATOR", this.hashCode(), this.fase);
                JOptionPane.showMessageDialog(null, "Caso de teste salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                testCaseRN.deleteDir(this.hashCode() + "");
                 Log.info("CADASTRO DO CT : "+jTextNameTS.getText());
                if (JOptionPane.showConfirmDialog(this, "Deseja realiza novo cadastro?", "Cadastro", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    cleanFields();
                    blockedFieldBnt(true);
                } else {
                    this.dispose();
                }

            } else {
                for (int i = 0; i < listProperties.size(); i++) {
                    String s = listProperties.get(i).getTestCaseName();
                    if (s.equalsIgnoreCase(jTextNameTS.getText())) {
                         Log.info("Mensagem: Não é possível salvar o Caso de teste. \nCausa: Já existe um caso de teste com o mesmo nome na base de dados."
                                + "\nSolução: Altere o nome caso de teste ou vá para a tela edição para editar-lo.");
                        JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: Já existe um caso de teste com o mesmo nome na base de dados."
                                + "\nSolução: Altere o nome caso de teste ou vá para a tela edição para editar-lo.", "Infomarção", JOptionPane.WARNING_MESSAGE);
                        blockedFieldBnt(true);
                        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }

        } catch (SVNException ex) {
            exceptionSVN(ex.getMessage());
            blockedFieldBnt(true);
            saveFile(testCaseRN.getTsDao().getTestCase());
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (IOException ex) {
            exceptionSVN(ex.getMessage());
            blockedFieldBnt(true);
            saveFile(testCaseRN.getTsDao().getTestCase());
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            exceptionSVN(ex.getMessage());
            blockedFieldBnt(true);
            saveFile(testCaseRN.getTsDao().getTestCase());
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void exceptionSVN(String exception) {

        switch (exception.substring(5, 12)) {
            case "E175005": //execeção arquivo já existe no diretório
                JOptionPane.showMessageDialog(null, "SVN: Erro ao salvar o Caso de teste. \nCausa: Já existe um caso de teste com o mesmo nome na base de dados."
                        + "\nSolução: Altere o nome caso de teste ou vá para a tela edição para editar-lo.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            case "E204899":
                JOptionPane.showMessageDialog(null, "SVN: Erro na tentativa de exclusão do arquivo. \nCausa: Provávelmente o arquivo está bloqueado ."
                        + "\nSolução: Desbloquei o arquivo via SVN e tente novamente.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            case "E170001":// Falha na autenticação
                JOptionPane.showMessageDialog(null, "SVN: Falha na autenticação do SVN \nCausa: Usuário ou senha incorretos."
                        + "\nSolução: Verifique usuário e senha na tela de configuração.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            case "E160013":// Falha na autenticação
                JOptionPane.showMessageDialog(null, "SVN: Diretório não encontrado no SVN.  \nCausa: Pasta do sistema não foi criada."
                        + "\nSolução: Solicite a criação da pasta do sistema.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Geral: " + exception + " \nCausa: desconhecida"
                        + "\nSolução: Desconhecida.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }

    }

    public void renderTableStep() {
        TableColumn col;
        for (int i = 0; i < tabelaSteps.getColumnCount() - 1; i++) {
            col = tabelaSteps.getColumnModel().getColumn(i);
            col.setCellRenderer(new TextAreaCellRenderer());
        }

        final DefaultTableModel modelo = (DefaultTableModel) tabelaSteps.getModel();

        class MyItemListener implements ItemListener {

            public void itemStateChanged(ItemEvent e) {
                Object source = e.getSource();
                if (source instanceof AbstractButton == false) {
                    return;
                }
                boolean checked = e.getStateChange() == ItemEvent.SELECTED;
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    modelo.setValueAt(checked, i, 3);
                    headerColumnAll = checked;
                }
            }
        }

        TableColumnModel modeloColuna = tabelaSteps.getColumnModel();
        modeloColuna.getColumn(3).setHeaderRenderer(new CabecalhoJTableCheckBox(new MyItemListener()));

    }

    public void addIconInButton() {
        ButtonIconBean iconBean = new ButtonIconBean();

//        bntAddStepPadrao.setIcon(icoBntAddStepDefault);
        bntAddStep.setIcon(iconBean.getIconBntAddNewStep());
        bntDeleteStep.setIcon(iconBean.getIconBntRemoveStep());
        bntMudaStepSubir.setIcon(iconBean.getIconBntMoveStepTop());
        bntMudaStepDescer.setIcon(iconBean.getIconBntMoveStepBottom());
        bntCancelar.setIcon(iconBean.getIconBntCacelAction());
        bntSalvar.setIcon(iconBean.getIconBntConfirmAction());
    }

    public void blockedFieldBnt(boolean b) {
        jComboFaseCR.setEnabled(b);
        jComboSistemasTS.setEnabled(b);
        jTextNameTS.setEnabled(b);
        jTextAreaDescriptionTS.setEnabled(b);
        bntAddStep.setEnabled(b);
        bntCancelar.setEnabled(b);
        bntDeleteStep.setEnabled(b);
        bntMudaStepDescer.setEnabled(b);
        bntMudaStepSubir.setEnabled(b);
        bntSalvar.setEnabled(b);
        tabelaSteps.setEnabled(b);
        bntCopiar.setEnabled(b);
        bntColar.setEnabled(b);
        jCheckBoxAutomatizado.setEnabled(b);
    }

    private void moveUp() {
        tabelaSteps.editingStopped(null);
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = model.getRowCount();
        int qtdSelect = 0;
        List<Integer> lines = new ArrayList<Integer>();
        for (int i = 0; i < countRow; i++) {
            if (((Boolean) tabelaSteps.getValueAt(i, 3)).booleanValue() ? true : false) {
                qtdSelect++;
                lines.add(i);
            }
        }
        int[] rows = new int[qtdSelect];
        for (int i = 0; i < qtdSelect; i++) {

            rows[i] = lines.get(i);
        }
        model.moveRow(rows[0], rows[rows.length - 1], rows[0] - 1);

        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            model.setValueAt("Step " + numeroStep, j, 0);

        }

    }

    private void moveDown() {
        tabelaSteps.editingStopped(null);
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = model.getRowCount();
        int qtdSelect = 0;
        List<Integer> lines = new ArrayList<Integer>();
        for (int i = 0; i < countRow; i++) {
            if (((Boolean) tabelaSteps.getValueAt(i, 3)).booleanValue() ? true : false) {
                qtdSelect++;
                lines.add(i);
            }
        }
        int[] rows = new int[qtdSelect];
        for (int i = 0; i < qtdSelect; i++) {

            rows[i] = lines.get(i);
        }
        model.moveRow(rows[0], rows[rows.length - 1], rows[0] + 1);

        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            model.setValueAt("Step " + numeroStep, j, 0);

        }

    }

    private void isExistsCT(String system, String nameCT) {
        boolean exists = false;
        try {
            svnRN = new SvnConnectionRN(this.fase);
            List<TestCaseTSPropertiesBean> listCtProperties = svnRN.search(system, nameCT);

            for (int i = 0; i < listCtProperties.size(); i++) {
                if (listCtProperties.get(i).getTestCaseName().equalsIgnoreCase(nameCT)) {
                    exists = true;
                }

            }
            if (exists) {
                JOptionPane.showMessageDialog(this, "O CT já existe na base de dados! Deseja edita-lo?", "SVN INFO", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SVNException ex) {
            exceptionSVN(ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void copyClipboardStep() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        int qtdSelecionados = 0;
        int countRow = tabelaSteps.getRowCount();

//        for (int i = 0; i < countRow; i++) {
//            if (tabelaSteps.getValueAt(i, 3) != null && tabelaSteps.getModel().getValueAt(i, 3).toString().equals("true")) {
//                qtdSelecionados++;
//            }
//        }
//        
//        if(qtdSelecionados > 1){
//            
//        }
        StringBuilder sb = new StringBuilder();
        String space = " ";
        Step s = new Step();

        for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
            sb.append(tabelaSteps.getModel().getValueAt(i, 0) + " | " + tabelaSteps.getModel().getValueAt(i, 1) + " | " + tabelaSteps.getModel().getValueAt(i, 2) + " |");
            sb.append("\n");
        }

        StringSelection selection = new StringSelection(sb.toString());
        clipboard.setContents(selection, null);
    }

    public void loadFields(TesteCaseTSBean tc) {
        jComboSistemasTS.setSelectedItem(tc.getProduct());
        jTextNameTS.setText(tc.getTestScriptName() + "-Cópia");
        jTextAreaDescriptionTS.setText(tc.getTestScriptDescription().replace("\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>", ""));
        jComboComplexidade.setSelectedItem(tc.getComplexidade());
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
//        List<String> listDesc = testCaseRN.breakTestCaseForStep(tc.getStepDescription());
//        List<String> listResult = testCaseRN.breakTestCaseForStep(tc.getExpectedResults());

        for (int i = 0; i < tc.getListStep().size(); i++) {
            model.addRow(new String[]{"Step " + 1, tc.getListStep().get(i).getDescStep(), tc.getListStep().get(i).getResultadoStep()});
            model.setValueAt(false, tabelaSteps.getRowCount() - 1, 3);
        }
        //ordena a numeração dos steps
        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            System.out.println("Antes: " + model.getValueAt(j, 0));
            model.setValueAt("Step " + numeroStep, j, 0);
            System.out.println("Depois: " + model.getValueAt(j, 0));

        }
    }

    public void cleanFields() {
        jComboSistemasTS.setSelectedItem("ARBOR");
        jTextNameTS.setText("");
        jTextAreaDescriptionTS.setText("");
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
//        List<String> listDesc = testCaseRN.breakTestCaseForStep(tc.getStepDescription());
//        List<String> listResult = testCaseRN.breakTestCaseForStep(tc.getExpectedResults());

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAddStep;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntColar;
    private javax.swing.JButton bntCopiar;
    private javax.swing.JButton bntDeleteStep;
    private javax.swing.JButton bntMudaStepDescer;
    private javax.swing.JButton bntMudaStepSubir;
    private javax.swing.JButton bntSalvar;
    private javax.swing.JCheckBox jCheckBoxAutomatizado;
    private javax.swing.JComboBox<String> jComboComplexidade;
    private javax.swing.JComboBox<String> jComboFaseCR;
    private javax.swing.JComboBox<String> jComboSistemasTS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPopupMenu jPopupTabelaStep;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaDescriptionTS;
    private javax.swing.JTextField jTextNameTS;
    private javax.swing.JTable tabelaSteps;
    // End of variables declaration//GEN-END:variables

    private void saveFile(TesteCaseTSBean testCase) {
        try {
            String nomeArquivo = "AUTOSAVE";
            File out = new File("C:\\FastPlan\\tempTestCase");
            out.mkdir();
            FileOutputStream saveFile = new FileOutputStream("C:\\FastPlan\\tempTestCase\\RecoveredTestCase.testcase");
            ObjectOutputStream stream = new ObjectOutputStream(saveFile);

            // salva o objeto
            stream.writeObject(testCase);

            stream.close();
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
        }
    }

    private void recoveredTestCase() {
        try {
            TesteCaseTSBean testCase;
            File tempDir = new File("C:\\FastPlan\\tempTestCase");
            tempDir.mkdir();
            
            File temp = new File("C:\\FastPlan\\tempTestCase\\RecoveredTestCase.testcase");
                      
            if(temp.exists()){
                
                if(JOptionPane.showConfirmDialog(this, "Foi recuperado um caso de teste que não foi salvo com sucesso, deseja recupera-lo?\nCaso escolha não o mesmo será pedido." , "Recuperação de caso de teste", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                
                    FileInputStream restFile = new FileInputStream(temp);
                    ObjectInputStream stream = new ObjectInputStream(restFile);
                    Object objeto = null;
                    // recupera o objeto
                    objeto = stream.readObject();
                    testCase = (TesteCaseTSBean) objeto;
                    stream.close();
                    jComboSistemasTS.setSelectedItem(testCase.getProduct());
                    jTextNameTS.setText(testCase.getTestScriptName());
                    jComboComplexidade.setSelectedItem(testCase.getComplexidade());
                    jCheckBoxAutomatizado.setEnabled(testCase.isAutomatizado());
                    jTextAreaDescriptionTS.setText(testCase.getTestScriptDescription());

                    DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
                    for (int i = 0; i < testCase.getListStep().size(); i++) {
                        model.addRow(new String[]{"Step " + 1, testCase.getListStep().get(i).getDescStep(), testCase.getListStep().get(i).getResultadoStep()});
                        model.setValueAt(false, tabelaSteps.getRowCount() - 1, 3);
                    }
                
                }
                
                temp.delete();
                
            }

        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
        }
    }
}
