/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.Step;
import com.accenture.bean.SystemBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.log.MyLogger;
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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.TableSelectionModel;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.PropertyConfigurator;
import org.tmatesoft.svn.core.SVNLock;
import org.tmatesoft.svn.core.internal.io.fs.FSLock;

/**
 *
 * @author raphael.da.silva
 */
public class EditScreenTSView extends javax.swing.JInternalFrame {

    private SvnConnectionRN svnRN;
    private TestCaseTSRN testCaseRN;
    private int numeroStep;
    private List<Step> listSteps;
    private int lineSelect = -1;
    private boolean headerColumnAll = false;
    private List<TestCaseTSPropertiesBean> listTestCaseTSPropertiesBean;
    private List<TesteCaseTSBean> listTestCase;
    private String ctbefore = "";
    private List<String> ctBaixados;
    private int lineSelectTableCt = -1;
    private boolean editing = false;
    private String filtro = "";
    private boolean fileDeletedSvn = false;
    String oldName = "";
    private Color c;
    private org.apache.log4j.Logger logger;
    private final static Logger Log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String fase;

    private List<TesteCaseTSBean> listTesteCaseTSBean;
    private TesteCaseTSBean testeCaseCurrent;
    private TesteCaseTSBean testeCaseSelect;
    private int rowBefore;
    private int rowAfter;

    /**
     * Creates new form guiCadTS
     */
    public EditScreenTSView(String fase) {

        this.fase = fase;
        try {
            initComponents();

//             MyLogger.setup();
            Log.setLevel(Level.INFO);
            Properties props = new Properties();
            props.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(props);
            setTitle("Edição de CT " + fase);

            logger = org.apache.log4j.Logger.getLogger(EditScreenTSView.class);

            listSteps = new ArrayList<Step>();
            //listTestCaseTSPropertiesBean = new ArrayList<TestCaseTSPropertiesBean>();
            //listTestCase = new ArrayList<TesteCaseTSBean>();
            //ctBaixados = new ArrayList<String>();
            //svnRN = new SvnConnectionRN(this.fase);
            //bntSalvarCT.setVisible(false);

            blockedFieldBnt(false);
            bntCancelar.setEnabled(true);
            bntFiltrar.setEnabled(true);
            tabelaCt.setEnabled(false);
            addIconInButton();

            new SwingWorker() {

                @Override
                protected Object doInBackground() {
                    renderTableStep();
                    callFilter();
//                    loadComboTS();
                    loadComboTSDB();

                    return null;
                }

                @Override
                protected void done() {

                }

            }.run();
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

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
        jComboSistemasTS = new javax.swing.JComboBox<SystemBean>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextNameTS = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboFaseCR = new javax.swing.JComboBox<String>();
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
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaCt = new javax.swing.JTable();
        bntFiltrar = new javax.swing.JButton();
        bntExcluirCT = new javax.swing.JButton();
        bntSalvarCT = new javax.swing.JButton();
        labelQtdCT = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboComplexidade = new javax.swing.JComboBox<String>();
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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Edição de CT");
        setMinimumSize(new java.awt.Dimension(1150, 33));
        setPreferredSize(new java.awt.Dimension(1150, 650));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jComboSistemasTS.setEditable(true);
        jComboSistemasTS.setEnabled(false);

        jLabel1.setText("Fase:");

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

        jLabel3.setText("Sistema:");

        jComboFaseCR.setEditable(true);
        jComboFaseCR.setEnabled(false);

        jLabel4.setText("Descrição:");

        jTextAreaDescriptionTS.setColumns(20);
        jTextAreaDescriptionTS.setRows(5);
        jTextAreaDescriptionTS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextAreaDescriptionTSKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTextAreaDescriptionTS);

        bntAddStep.setText("Add Step");
        bntAddStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddStepActionPerformed(evt);
            }
        });

        bntDeleteStep.setText("Deletar Step");
        bntDeleteStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntDeleteStepMouseClicked(evt);
            }
        });
        bntDeleteStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntDeleteStepActionPerformed(evt);
            }
        });

        bntMudaStepSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMudaStepSubirActionPerformed(evt);
            }
        });

        bntMudaStepDescer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMudaStepDescerActionPerformed(evt);
            }
        });

        tabelaSteps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Step", "Descrição", "Resultado Esperado", "id", "Todos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaSteps.setColumnSelectionAllowed(true);
        tabelaSteps.setName(""); // NOI18N
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
            tabelaSteps.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabelaSteps.getColumnModel().getColumn(3).setMaxWidth(0);
            tabelaSteps.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(4).setMaxWidth(80);
        }
        tabelaSteps.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor(this));
        tabelaSteps.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor(this));

        bntSalvar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        bntSalvar.setText("Salvar");
        bntSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalvarActionPerformed(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        bntColar.setText("Colar");
        bntColar.setEnabled(false);
        bntColar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntColarActionPerformed(evt);
            }
        });

        bntCopiar.setText("Copiar");
        bntCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCopiarActionPerformed(evt);
            }
        });

        tabelaCt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome CT", "Modificado por", "Data modificação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaCt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabelaCtMouseReleased(evt);
            }
        });
        tabelaCt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaCtKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaCt);
        if (tabelaCt.getColumnModel().getColumnCount() > 0) {
            tabelaCt.getColumnModel().getColumn(0).setMinWidth(70);
            tabelaCt.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabelaCt.getColumnModel().getColumn(0).setMaxWidth(70);
            tabelaCt.getColumnModel().getColumn(1).setMinWidth(330);
            tabelaCt.getColumnModel().getColumn(1).setPreferredWidth(330);
            tabelaCt.getColumnModel().getColumn(1).setMaxWidth(330);
            tabelaCt.getColumnModel().getColumn(2).setMinWidth(110);
            tabelaCt.getColumnModel().getColumn(2).setMaxWidth(110);
            tabelaCt.getColumnModel().getColumn(3).setMinWidth(130);
            tabelaCt.getColumnModel().getColumn(3).setPreferredWidth(130);
            tabelaCt.getColumnModel().getColumn(3).setMaxWidth(130);
        }
        jScrollPane3.setHorizontalScrollBar(new JScrollBar(0));
        tabelaCt.setAutoResizeMode (tabelaCt.AUTO_RESIZE_OFF);
        tabelaCt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCt.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    rowBefore = lse.getFirstIndex();
                    rowAfter = lse.getLastIndex();
                }
            }
        });

        bntFiltrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntFiltrarMouseClicked(evt);
            }
        });
        bntFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntFiltrarActionPerformed(evt);
            }
        });

        bntExcluirCT.setText("Excluir");
        bntExcluirCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExcluirCTActionPerformed(evt);
            }
        });

        bntSalvarCT.setText("Salvar");
        bntSalvarCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntSalvarCTMouseClicked(evt);
            }
        });
        bntSalvarCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalvarCTActionPerformed(evt);
            }
        });

        labelQtdCT.setText("Quantidade de CTs: ");

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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bntAddStep)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(bntFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntExcluirCT)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntSalvarCT))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntDeleteStep)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bntMudaStepSubir)
                                        .addGap(7, 7, 7)
                                        .addComponent(bntMudaStepDescer, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bntCopiar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntColar)))
                                .addGap(0, 297, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jComboSistemasTS, 0, 216, Short.MAX_VALUE)
                                            .addComponent(jComboFaseCR, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jComboComplexidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jCheckBoxAutomatizado)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addGap(10, 10, 10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextNameTS, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1))
                                        .addContainerGap())))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bntSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(labelQtdCT))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntFiltrar)
                            .addComponent(bntExcluirCT)
                            .addComponent(bntSalvarCT))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextNameTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jComboComplexidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboFaseCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxAutomatizado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelQtdCT)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntMudaStepSubir)
                    .addComponent(bntMudaStepDescer)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bntAddStep)
                        .addComponent(bntDeleteStep)
                        .addComponent(bntColar)
                        .addComponent(bntCopiar)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jComboComplexidade.addItem("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntDeleteStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntDeleteStepMouseClicked


    }//GEN-LAST:event_bntDeleteStepMouseClicked

    private void tabelaStepsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaStepsMouseClicked

        int column = tabelaSteps.columnAtPoint(evt.getPoint());
        int row = tabelaSteps.rowAtPoint(evt.getPoint());

        //instancia o model da tabela de steps
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        if (evt.getButton() == MouseEvent.BUTTON3) {
            System.out.println("botão direito!");
            System.out.println("x: " + evt.getX() + " y: " + evt.getY());
            jPopupTabelaStep.show(tabelaSteps, evt.getX(), evt.getY());

        } else if (column != 4) {

            if (true) {
                for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
                    model.setValueAt(false, i, 4);

                }
            }
            //verifica se existe alguma linha selecionada
            if (lineSelect != -1) {
                //caso exista linha selecionada o checkbox e desmarcado 
                model.setValueAt(false, lineSelect, 4);
                lineSelect = tabelaSteps.getSelectedRow();
                //é marcado o checkbox da linha atual
                model.setValueAt(true, lineSelect, 4);
            } else {
                //marca checkbox da linha atual

                model.setValueAt(true, row, 4);
                lineSelect = row;
            }

        }

//        tabelaSteps.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor());
//        tabelaSteps.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor());

    }//GEN-LAST:event_tabelaStepsMouseClicked

    private void jTextNameTSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextNameTSFocusLost


    }//GEN-LAST:event_jTextNameTSFocusLost

    private void bntFiltrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntFiltrarMouseClicked

    }//GEN-LAST:event_bntFiltrarMouseClicked

    private void bntFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntFiltrarActionPerformed
        callFilter();
    }//GEN-LAST:event_bntFiltrarActionPerformed

    private void bntExcluirCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExcluirCTActionPerformed
//        if (JOptionPane.showConfirmDialog(null, "Confirma a exclusão do CT " + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getTestCaseName() + "?", "CT Creator", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//            fileDeletedSvn = true;
//            deleteCT(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRow()).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), "CT EXCLUIDO VIA CT CREATOR");
////            updateProperties(listTestCaseTSPropertiesBean.get(0).getSystem());
//            if (tabelaCt.getRowCount() > 0) {
//                tabelaCt.addRowSelectionInterval(0, 0);
//            }
//            fileDeletedSvn = false;
//            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        }
        if (JOptionPane.showConfirmDialog(null, "Confirma a exclusão do CT " + testeCaseSelect.getTestScriptName() + "?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

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
                    delete();
                    blockedFieldBnt(true);
                    return null;
                }

                @Override
                protected void done() {
                    aguarde.dispose();
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }.execute();
        }


    }//GEN-LAST:event_bntExcluirCTActionPerformed

    private void bntSalvarCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntSalvarCTMouseClicked

    }//GEN-LAST:event_bntSalvarCTMouseClicked

    private void bntSalvarCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalvarCTActionPerformed

    }//GEN-LAST:event_bntSalvarCTActionPerformed

    private void tabelaCtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaCtKeyReleased
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN && getContentPane().getCursor().getType() != Cursor.WAIT_CURSOR)) {
//            int line = lineSelectTableCt;
//            if (isEditing()) {
//                if (JOptionPane.showConfirmDialog(null, "Deseja selecionar outro CT? Suas modificações atuais seram perdidas!", "CT Creator", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//
//                    c = tabelaCt.getSelectionBackground();
//                    lineSelect = -1;
//                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                    updateProperties(listTestCaseTSPropertiesBean.get(0).getSystem());
//                    lineSelect = -1;
//                    try {
//                        new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + jComboSistemasTS.getSelectedItem().toString() + "\\" + listTestCaseTSPropertiesBean.get(line).getDirEntry().getName()).delete();
//                    } catch (IOException ex) {
//                        logger.error("Erro ao deletar arquivo", ex);
//                    }
//                    loadCT2();
//                    setEditing(false);
//
//                } else {
//                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
//                    tabelaCt.removeRowSelectionInterval(0, tabelaCt.getRowCount() - 1);
//                    tabelaCt.addRowSelectionInterval(lineSelectTableCt, lineSelectTableCt);
//
//                }
//            }
            verifyEdition();
        }


    }//GEN-LAST:event_tabelaCtKeyReleased

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        try {
            if (lineSelectTableCt != -1) {

                svnRN = new SvnConnectionRN(this.fase);
                svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());

            }
            testCaseRN.deleteDir(this.hashCode() + "");
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            this.dispose();
            logger.error("Erro SVN ao fechar a janela: ", ex);
            exceptionSVN(ex.getMessage());
            this.dispose();
        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void bntMudaStepSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepSubirActionPerformed
        moveUp();
    }//GEN-LAST:event_bntMudaStepSubirActionPerformed

    private void bntSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalvarActionPerformed

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
                    //editCtSVN();
                    saveTestCaseBD(rowAfter);
                    loadTableCtDB(testCaseRN.getTesteCaseTSBeanBySystemBeanBD((SystemBean) jComboSistemasTS.getSelectedItem()));
                    loadCTDB();
                    blockedFieldBnt(true);
                    return null;
                }

                @Override
                protected void done() {
                    aguarde.dispose();
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }.execute();
        } else {
            JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: O nome do caso de teste tem de 5 à 80 caracteres."
                    + "\nSolução: Altere o nome caso de teste de 5 à 80 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bntSalvarActionPerformed

    private void tabelaCtMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCtMouseReleased
//        if (evt.getClickCount() == 1 && getContentPane().getCursor().getType() != Cursor.WAIT_CURSOR) {
//            int line = lineSelectTableCt;
//            if (isEditing()) {
//
//                if (JOptionPane.showConfirmDialog(null, "Deseja selecionar outro CT? Suas modificações atuais seram perdidas!", "CT Creator", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//
//                    c = tabelaCt.getSelectionBackground();
//                    lineSelect = -1;
//                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                    updateProperties(listTestCaseTSPropertiesBean.get(0).getSystem());
//                    lineSelect = -1;
//                    try {
//                        new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + jComboSistemasTS.getSelectedItem().toString() + "\\" + listTestCaseTSPropertiesBean.get(line).getDirEntry().getName()).delete();
//                    } catch (IOException ex) {
//                        logger.error("Erro ao deletar arquivo", ex);
//                    }
//                    loadCT2();
//                    setEditing(false);
//
//                } else {
//                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
//                    tabelaCt.removeRowSelectionInterval(0, tabelaCt.getRowCount() - 1);
//                    tabelaCt.addRowSelectionInterval(lineSelectTableCt, lineSelectTableCt);
//
//                }
//
//            } else {
//
//                c = tabelaCt.getSelectionBackground();
////                try {
////                    if(line != -1)
////                    new File(new SVNPropertiesVOBean().getFolderTemplocal() + this.hashCode() + "\\" + jComboSistemasTS.getSelectedItem().toString() + "\\" + listTestCaseTSPropertiesBean.get(line).getDirEntry().getName()).delete();
////                } catch (IOException ex) {
////                    logger.error("Erro ao deletar arquivo", ex);
////                }
//                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                updateProperties(listTestCaseTSPropertiesBean.get(0).getSystem());
//                loadCT2();
//
//            }

//        }
        if (evt.getClickCount() == 1 && getContentPane().getCursor().getType() != Cursor.WAIT_CURSOR) {
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
                    verifyEdition();
                    blockedFieldBnt(true);
                    return null;
                }

                @Override
                protected void done() {
                    aguarde.dispose();
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }.execute();
        
        
        
        
           
        }
    }//GEN-LAST:event_tabelaCtMouseReleased

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
//        if (JOptionPane.showConfirmDialog(this, "Ao sair da tela seus dados serão perdidos, deseja realmente sair?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//
//            try {
//                if (lineSelectTableCt != -1) {
//
//                    svnRN = new SvnConnectionRN(this.fase);
//                    svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
//
//                }
//                testCaseRN.deleteDir(this.hashCode() + "");
//            } catch (SVNException ex) {
//                Log.log(Level.SEVERE, "ERROR", ex);
//                logger.error("Erro SVN", ex);
//                exceptionSVN(ex.getMessage());
//            } catch (IOException ex) {
//                Log.log(Level.SEVERE, "ERROR", ex);
//                logger.error("Erro ", ex);
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            }
//        }
        verifyChangeToClose();
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntAddStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddStepActionPerformed
        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tabelaSteps.editingStopped(null);
            //model da tabela step
            DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
            //add nova linha na tabela
            model.addRow(new String[]{"Step " + 1, "<<<parametro>>>", "<<<paramentro>>>", "0"});
            model.setValueAt(false, tabelaSteps.getRowCount() - 1, 4);

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
        //CR 15129 - INICIO
        setEditing(true);
        //CR 15129 - FIM
    }//GEN-LAST:event_bntAddStepActionPerformed

    private void bntDeleteStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDeleteStepActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = tabelaSteps.getRowCount();
        int qtdSelecionados = 0;

        String nomeStepSelecionado = null;

        for (int i = 0; i < countRow; i++) {
            if (tabelaSteps.getValueAt(i, 4) != null && model.getValueAt(i, 4).toString().equals("true")) {
                qtdSelecionados++;
                nomeStepSelecionado = model.getValueAt(i, 0).toString();

            }
        }

        if (qtdSelecionados > 0) {

            if (qtdSelecionados == 1) {

                if (JOptionPane.showConfirmDialog(this, "Deseja excluir o " + nomeStepSelecionado + "?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
                        if (tabelaSteps.getValueAt(i, 4) != null && model.getValueAt(i, 4).toString().equals("true")) {
                            tabelaSteps.editingStopped(null);
                            model.removeRow(i);

                        }
                    }

                } else {
                    //exclusão cancelada
                }

            } else if (JOptionPane.showConfirmDialog(this, "Deseja excluir os steps selecionados?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (int i = countRow - 1; i >= 0; i--) {
                    if (tabelaSteps.getValueAt(i, 4) != null && model.getValueAt(i, 4).toString().equals("true")) {
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
        //CR 15129 - INICIO
        setEditing(true);
        //CR 15129 - FIM
    }//GEN-LAST:event_bntDeleteStepActionPerformed

    private void bntMudaStepDescerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepDescerActionPerformed
        moveDown();
    }//GEN-LAST:event_bntMudaStepDescerActionPerformed

    private void bntCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCopiarActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = tabelaSteps.getRowCount();

        tabelaSteps.editingStopped(null);
        listSteps.clear();
        for (int i = 0; i < countRow; i++) {
            Step s = new Step();
            if (tabelaSteps.getValueAt(i, 4) != null && model.getValueAt(i, 4).toString().equals("true")) {
                s.setNomeStep(model.getValueAt(i, 0).toString());
                s.setDescStep(model.getValueAt(i, 1).toString());
                s.setResultadoStep(model.getValueAt(i, 2).toString());
                listSteps.add(s);
            }
        }

        bntColar.setEnabled(true);
    }//GEN-LAST:event_bntCopiarActionPerformed

    private void bntColarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntColarActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = tabelaSteps.getRowCount();

        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < listSteps.size(); i++) {
                model.addRow(new Object[]{"Step " + 1, listSteps.get(i).getDescStep(), listSteps.get(i).getResultadoStep(), "0", false});

                //ordena a numeração dos steps
                int numLinhas = model.getRowCount();
                for (int j = 0; j < numLinhas; j++) {
                    numeroStep = j + 1;
                    model.setValueAt("Step " + numeroStep, j, 0);
                }
            }

        }
        setEditing(true);
    }//GEN-LAST:event_bntColarActionPerformed

    private void jTextNameTSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNameTSKeyReleased
        setEditing(true);


    }//GEN-LAST:event_jTextNameTSKeyReleased

    private void jTextAreaDescriptionTSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAreaDescriptionTSKeyReleased
        setEditing(true);
    }//GEN-LAST:event_jTextAreaDescriptionTSKeyReleased

    private void jTextNameTSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNameTSKeyTyped
        if (evt.getKeyChar() == '(' || evt.getKeyChar() == ')' || evt.getKeyChar() == '\\' || evt.getKeyChar() == '/') {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextNameTSKeyTyped

    private void jCheckBoxAutomatizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAutomatizadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxAutomatizadoActionPerformed
    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void loadComboTS() {

        try {
            testCaseRN = new TestCaseTSRN(this.fase);
            ArrayList systems = testCaseRN.systemsTestCase();
            ArrayList fases = testCaseRN.faseCRTestCase();
            ArrayList complexidades = testCaseRN.complexidade();
            for (int i = 0; i < systems.size(); i++) {
//                jComboSistemasTS.addItem(systems.get(i).toString());
            }
            for (int i = 0; i < fases.size(); i++) {
                jComboFaseCR.addItem(fases.get(i).toString());
            }

            for (int i = 0; i < complexidades.size(); i++) {
                jComboComplexidade.addItem(complexidades.get(i).toString());
            }
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            logger.error("Erro SVN", ex);
            exceptionSVN(ex.getMessage());
        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadComboTSDB() {

        try {
            testCaseRN = new TestCaseTSRN();
            List<SystemBean> systems = testCaseRN.getSystemsBD();

            for (int i = 0; i < systems.size(); i++) {
                jComboSistemasTS.addItem(systems.get(i));
            }

            ArrayList fases = testCaseRN.faseCRTestCase();
            ArrayList complexidades = testCaseRN.complexidade();

            for (int i = 0; i < fases.size(); i++) {
                jComboFaseCR.addItem(fases.get(i).toString());
            }

            for (int i = 0; i < complexidades.size(); i++) {
                jComboComplexidade.addItem(complexidades.get(i).toString());
            }
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void refreshTableCT() {

        List<TesteCaseTSBean> list = testCaseRN.getTesteCaseTSBeanBySystemBeanBD((SystemBean) jComboSistemasTS.getSelectedItem());

        //tabelaCt.getModel().getRowCount()
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

    private void editCtSVN() {
        try {
            oldName = listTestCase.get(0).getTestScriptName();
            boolean existeCT = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date(System.currentTimeMillis());

            List<Step> listStep = new ArrayList<Step>();

            if (lineSelectTableCt != -1) {
                new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "//" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "//" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName()).delete();
            }

            testCaseRN = new TestCaseTSRN(this.fase);
            svnRN = new SvnConnectionRN(this.fase);
            listTestCase.get(0).setTestScriptName(jTextNameTS.getText());
            listTestCase.get(0).setProduct(jComboSistemasTS.getSelectedItem().toString());
            listTestCase.get(0).setComplexidade(jComboComplexidade.getSelectedItem().toString());
            listTestCase.get(0).setTestScriptDescription(jTextAreaDescriptionTS.getText() + "\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>");
            listTestCase.get(0).setDataPlanejada(d);
            tabelaSteps.editingStopped(null);
            for (int cont = 0; cont < tabelaSteps.getRowCount(); cont++) {
                Step step = new Step();
                step.setNomeStep((tabelaSteps.getValueAt(cont, 0)).toString());
                step.setDescStep((tabelaSteps.getValueAt(cont, 1)).toString());
                step.setResultadoStep((tabelaSteps.getValueAt(cont, 2)).toString());
                listStep.add(step);
            }
            listTestCase.get(0).setTestCaseProperties(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRow()));
            listTestCase.get(0).setListStep(listStep);

            listTestCase.get(0).setAutomatizado(jCheckBoxAutomatizado.isSelected());

            List<TestCaseTSPropertiesBean> listProperties = new SvnConnectionRN(this.fase).search(testCaseRN.getTsDao().getTestCase().getProduct(), jTextNameTS.getText());
            if (listProperties.isEmpty()) {
                svnRN.updateCt(jComboSistemasTS.getSelectedItem().toString(), oldName.toUpperCase(), listTestCase.get(0).getTestScriptName().toUpperCase(), listTestCase.get(0), this.hashCode(), this.fase);
                updateProperties(listTestCase.get(0).getProduct());

//                svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                //atualizando tabela com os dados do ct 
//                tabelaCt.setValueAt(jTextNameTS.getText().toUpperCase(), tabelaCt.getSelectedRow(), 1);
//                String nameTC = tabelaCt.getValueAt(lineSelectTableCt, 0) + "-" + tabelaCt.getValueAt(lineSelectTableCt, 1);
//                updateProperties(listTestCase.get(0).getProduct(), nameTC, lineSelectTableCt);
//                tabelaCt.setValueAt(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getAuthor(), lineSelectTableCt, 2);
//                sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                String dataFormatada = sdf.format(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getDate());
//                tabelaCt.setValueAt(dataFormatada, lineSelectTableCt, 3);
                svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                JOptionPane.showMessageDialog(null, "Caso de teste salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

                setEditing(false);

            } else {
                for (int i = 0; i < listProperties.size(); i++) {
                    String s = listProperties.get(i).getTestCaseName();
                    if (s.equalsIgnoreCase(jTextNameTS.getText())) {
                        JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: Já existe um caso de teste com o mesmo nome na base de dados."
                                + "\nSolução: Altere o nome caso de teste ou vá para a tela edição para editar-lo.", "Infomarção", JOptionPane.WARNING_MESSAGE);
                        blockedFieldBnt(true);
                        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }

        } catch (SVNException ex) {
            try {

//                exceptionSVN(ex.getMessage());
                System.out.println(ex.getMessage());
                Log.info("Tentando salvar CT após erro.");
                Log.info("Tentando salvar CT após erro.");
                testCaseRN.deleteDir(jComboSistemasTS.getSelectedItem().toString());
                Log.info("Pasta do sistema deletada");
                Log.info("Início do método: svnRN.checkOutEmpytFolder(jComboSistemasTS.getSelectedItem().toString(), this.hashCode()) ");
                Log.info("Início do método: svnRN.checkOutEmpytFolder(jComboSistemasTS.getSelectedItem().toString(), this.hashCode()");
                svnRN.checkOutEmpytFolder(jComboSistemasTS.getSelectedItem().toString(), this.hashCode(), this.fase);
                Log.info("Fim do método: svnRN.checkOutEmpytFolder(jComboSistemasTS.getSelectedItem().toString(), this.hashCode()) ");
                Log.info("início do método: new TestCaseTSRN().writerSheet(jComboSistemasTS.getSelectedItem().toString(), listTestCase.get(0).getTestCaseProperties().getDirEntry().getName(), listTestCase.get(0),this.hashCode());");
                new TestCaseTSRN(this.fase).writerSheet(jComboSistemasTS.getSelectedItem().toString(), listTestCase.get(0).getTestCaseProperties().getDirEntry().getName(), listTestCase.get(0), this.hashCode());
                Log.info("Parâmetros: ");
                Log.info("SISTEMA: " + jComboSistemasTS.getSelectedItem().toString());
                Log.info("NOME CT: " + listTestCase.get(0).getTestScriptName().toUpperCase());
                Log.info("DIRETÓRIO: " + this.hashCode());
                Log.info("Fim do método: new TestCaseTSRN().writerSheet(jComboSistemasTS.getSelectedItem().toString(), listTestCase.get(0).getTestScriptName().toUpperCase(), listTestCase.get(0),this.hashCode());");
                Log.info("Início do método: svnRN.addFileSVN(jComboSistemasTS.getSelectedItem().toString(), this.hashCode()) ");
                svnRN.addFileSVN(jComboSistemasTS.getSelectedItem().toString(), this.hashCode());
                Log.info("Fim do método: svnRN.addFileSVN(jComboSistemasTS.getSelectedItem().toString(), this.hashCode()) ");
                blockedFieldBnt(true);
                Log.info("Atualizando tabela");
                updateProperties(listTestCase.get(0).getProduct());
                Log.info("Tabela atualizada");
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                JOptionPane.showMessageDialog(null, "Caso de teste salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                setEditing(false);
                lineSelectTableCt = tabelaCt.getSelectedRow();
                Log.info("Lockar arquivo");
                svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                Log.info("Arquivo Lockado.");

                Log.log(Level.SEVERE, "Erro SVN - ANTES DA 2ª TENTATIVA", ex);
            } catch (SVNException ex1) {
                Log.log(Level.SEVERE, "ERROR", ex);
                logger.error("Erro na segunda tentativa: ", ex);
            } catch (IOException ex1) {
                Log.log(Level.SEVERE, "ERROR", ex);
                logger.error("Erro na segunda tentativa: ", ex);
            }
        } catch (IOException ex) {
            logger.error("Erro ", ex);
            Log.log(Level.SEVERE, "ERROR", ex);
            exceptionSVN(ex.getMessage());
            blockedFieldBnt(true);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            logger.error("Erro ", ex);
            Log.log(Level.SEVERE, "ERROR", ex);
            exceptionSVN(ex.getMessage());
            blockedFieldBnt(true);
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

        DefaultTableModel modelo = (DefaultTableModel) tabelaSteps.getModel();

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
        bntAddStep.setIcon(iconBean.getIconBntAddNewStep());
        bntDeleteStep.setIcon(iconBean.getIconBntRemoveStep());
        bntMudaStepSubir.setIcon(iconBean.getIconBntMoveStepTop());
        bntMudaStepDescer.setIcon(iconBean.getIconBntMoveStepBottom());
        bntCancelar.setIcon(iconBean.getIconBntCacelAction());
        bntSalvar.setIcon(iconBean.getIconBntSaveMinimum());
        bntFiltrar.setIcon(iconBean.getIconBntFilter());
        bntExcluirCT.setIcon(iconBean.getIconBntRemoveCt());
        bntSalvarCT.setIcon(iconBean.getIconBntSaveMinimum());
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
        bntFiltrar.setEnabled(b);
        bntExcluirCT.setEnabled(b);
        bntSalvarCT.setEnabled(b);
    }

    private void moveUp() {
        tabelaSteps.editingStopped(null);
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int countRow = model.getRowCount();
        int qtdSelect = 0;
        List<Integer> lines = new ArrayList<Integer>();
        for (int i = 0; i < countRow; i++) {
            if (((Boolean) tabelaSteps.getValueAt(i, 4)).booleanValue() ? true : false) {
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
            if (((Boolean) tabelaSteps.getValueAt(i, 4)).booleanValue() ? true : false) {
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
            logger.error("Erro SVN", ex);
            exceptionSVN(ex.getMessage());
        } catch (IOException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void copyClipboardStep() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        int qtdSelecionados = 0;
        int countRow = tabelaSteps.getRowCount();

        StringBuilder sb = new StringBuilder();
        String space = " ";
        Step s = new Step();

        for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
            sb.append("| " + tabelaSteps.getModel().getValueAt(i, 0) + " | " + tabelaSteps.getModel().getValueAt(i, 1) + " | " + tabelaSteps.getModel().getValueAt(i, 1) + " |");

        }

        StringSelection selection = new StringSelection(sb.toString());
        clipboard.setContents(selection, null);
    }

    public void loadTableCt(List<TestCaseTSPropertiesBean> listPropertiesBean) {
        try {
            DefaultTableModel model = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            if (fileDeletedSvn == false && lineSelectTableCt != -1 && listTestCaseTSPropertiesBean.size() != 0) {
                svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
            }
            listTestCaseTSPropertiesBean.clear();

            listTestCaseTSPropertiesBean = listPropertiesBean;

            Collections.sort(listTestCaseTSPropertiesBean, new Comparator<TestCaseTSPropertiesBean>() {
                @Override
                public int compare(TestCaseTSPropertiesBean t, TestCaseTSPropertiesBean t1) {
                    return t.getDirEntry().getName().compareTo(t1.getDirEntry().getName());
                }
            });

            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }

            if (listTestCaseTSPropertiesBean.size() == 0) {
                blockedFieldBnt(false);
                bntFiltrar.setEnabled(true);
//            limpaCampos();
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }
            }

            String nameCT = "";
            String id = "";
            String modifyBy = "";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (int i = 0; i < listTestCaseTSPropertiesBean.size(); i++) {
//            id = listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().replace(".xlsx","").substring(0,listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().indexOf("-"));
//            nameCT = listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().replace(".xlsx","").substring(listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().indexOf("-")+1);
                id = listPropertiesBean.get(i).getTesteCaseId();
                nameCT = listPropertiesBean.get(i).getTestCaseName();
                modifyBy = listTestCaseTSPropertiesBean.get(i).getDirEntry().getAuthor();
                System.out.println("CAPTURANDO URL: " + listTestCaseTSPropertiesBean.get(i).getDirEntry().getURL());
                String dataFormatada = sdf.format(listTestCaseTSPropertiesBean.get(i).getDirEntry().getDate());

                model.addRow(new String[]{id, nameCT, modifyBy, dataFormatada});
//            System.out.println(listStep.get(i).getCasoTeste());
            }
            tabelaCt.setEnabled(true);
//            if(lineSelectTableCt != -1) 
//            svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
//            lineSelectTableCt = -1;
            fileDeletedSvn = false;
            labelQtdCT.setText("Quantidade de CTs: " + tabelaCt.getRowCount());
        } catch (SVNException ex) {
            logger.error("Erro SVN ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadTableCtDB(List<TesteCaseTSBean> listTesteCaseTSBean) {
        try {
            DefaultTableModel model = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            boolean isFirst = false;

            this.listTesteCaseTSBean = listTesteCaseTSBean;

            

//            while (model.getRowCount() > 0) {
//                model.removeRow(0);
//            }
            model.setRowCount(0);

            if (this.listTesteCaseTSBean.size() == 0) {
                blockedFieldBnt(false);
                bntFiltrar.setEnabled(true);
//            limpaCampos();
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }
            }
            
//            if (model.getRowCount() == 0) {
//                isFirst = true;
//            }

            String nameCT = "";
            String id = "";
            String modifyBy = "";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Nome CT");
            header.add("Modificado por");
            header.add("Data modificação");

            for (int i = 0; i < this.listTesteCaseTSBean.size(); i++) {
                id = this.listTesteCaseTSBean.get(i).getId() + "";
                nameCT = this.listTesteCaseTSBean.get(i).getTestScriptName();
                modifyBy = this.listTesteCaseTSBean.get(i).getModifiedBy();

                String dataFormatada = sdf.format(this.listTesteCaseTSBean.get(i).getModifyDate());

                //model.addRow(new String[]{id, nameCT, modifyBy, dataFormatada});
                Vector<Object> row = new Vector<Object>();

                row.add(id);
                row.add(nameCT);
                row.add(modifyBy);
                row.add(dataFormatada);

                data.add(row);

            }

            model.setDataVector(data, header);
            
            Thread.sleep(2000);
            TableColumnModel columnModel = tabelaCt.getColumnModel();
            Dimension tableSize = tabelaCt.getSize();
            columnModel.getColumn(0).setPreferredWidth((int) (tableSize.getWidth() * 0.10));
            columnModel.getColumn(1).setPreferredWidth((int) (tableSize.getWidth() * 0.70));
            columnModel.getColumn(2).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(3).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            
            if (model.getRowCount() == 0) {
                isFirst = true;
            }

            if (!isFirst) {
                tabelaCt.setRowSelectionInterval(this.rowAfter, this.rowAfter);
            }

            tabelaCt.setEnabled(true);

            labelQtdCT.setText("Quantidade de CTs: " + tabelaCt.getRowCount());
        } catch (Exception ex) {
            logger.error("Erro", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void callFilter() {
        FilterTestCaseScreenTSView1 dialogFiltro = null;
        try {
            dialogFiltro = new FilterTestCaseScreenTSView1(this, null, true, this.fase);
        } catch (IOException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        dialogFiltro.centralizaJanelaDialogo(this);
        dialogFiltro.setVisible(true);
    }

    public void loadFields(TesteCaseTSBean testCase) {
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        jTextNameTS.setText(testCase.getTestScriptName());
        jComboFaseCR.setSelectedItem(testCase.getFase());
        switch (testCase.getComplexidade()) {

            case "Muito Alta":
                jComboComplexidade.setSelectedItem("Muito Alto");
                break;

            case "Alto":
                jComboComplexidade.setSelectedItem("Alto");
                break;

            case "Media":
                jComboComplexidade.setSelectedItem("Medio");
                break;

            case "M\\u00E9dia":
                jComboComplexidade.setSelectedItem("Medio");
                break;

            case "Medio":
                jComboComplexidade.setSelectedItem("Medio");
                break;

            case "Baixa":
                jComboComplexidade.setSelectedItem("Baixo");
                break;
            case "Muito Baixa":
                jComboComplexidade.setSelectedItem("Muito Baixo");
                break;

            default:
                jComboComplexidade.setSelectedItem(testCase.getComplexidade());
        }
// jComboComplexidade.setSelectedItem(testCase.getComplexidade());
        switch (testCase.getProduct()) {
            case "SBL63":
                jComboSistemasTS.setSelectedItem("Siebel 6.3");
                break;

            case "SBL8":
                jComboSistemasTS.setSelectedItem("Siebel 8");
                break;

            case "SBL MKT":
                jComboSistemasTS.setSelectedItem("Siebel MKT");
                break;

            default:
                jComboSistemasTS.setSelectedItem(testCase.getProduct());
        }

        jTextAreaDescriptionTS.setText(testCase.getTestScriptDescription().replace("\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>", ""));

        for (int i = 0; i < testCase.getListStep().size(); i++) {
            model.addRow(new String[]{"Step " + 1, testCase.getListStep().get(i).getDescStep(), testCase.getListStep().get(i).getResultadoStep()});
            model.setValueAt(false, tabelaSteps.getRowCount() - 1, 3);
        }

        //ordena a numeração dos steps
        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            model.setValueAt("Step " + numeroStep, j, 0);
        }

        //campo automatizado
        jCheckBoxAutomatizado.setSelected(testCase.isAutomatizado());

    }

    public void loadFieldsDB(TesteCaseTSBean testCase) {
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        jTextNameTS.setText(testCase.getTestScriptName());
        jComboFaseCR.setSelectedItem(testCase.getFase());
        jComboComplexidade.setSelectedItem(testCase.getComplexidade());
        for (int i = 0; i < jComboSistemasTS.getItemCount(); i++) {
            if (testCase.getIdSystem() == jComboSistemasTS.getItemAt(i).getId()) {
                jComboSistemasTS.setSelectedIndex(i);
            }
        }

        jTextAreaDescriptionTS.setText(testCase.getTestScriptDescription().replace("\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>", ""));

        for (int i = 0; i < testCase.getListStep().size(); i++) {
            model.addRow(new String[]{"Step " + 1, testCase.getListStep().get(i).getDescStep(), testCase.getListStep().get(i).getResultadoStep(), testCase.getListStep().get(i).getId() + ""});
            model.setValueAt(false, tabelaSteps.getRowCount() - 1, 4);
        }

        //ordena a numeração dos steps
        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            model.setValueAt("Step " + numeroStep, j, 0);
        }

        //campo automatizado
        jCheckBoxAutomatizado.setSelected(testCase.isAutomatizado());

    }

    private void loadCT() {
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            // verifica se a linha clicada é a que está selecionada
            if (!ctbefore.equals(modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString())) {

                if (tabelaSteps.getRowCount() > 0) {
                    // tira a seleção dos steps
                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
                }
                //apaga linhas da tabela steps
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }

                if (lineSelectTableCt != -1) {
                    new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName()).delete();
                }
                //captra linha selecioanda
                lineSelectTableCt = tabelaCt.getSelectedRow();

                //Verifica se é o primeiro click na tabela e se o ct selecionado está bloqueado
                if (ctbefore.equals("")) {

                    svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.fase);
                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                    listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                    loadFields(listTestCase.get(0));
                    blockedFieldBnt(true);
                    ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                    ctBaixados.add(ctbefore);
                } else if (isDonwloadCt(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getTestCaseName())) {
                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                    listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                    loadFields(listTestCase.get(0));
                    blockedFieldBnt(true);
                    ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                    ctBaixados.add(ctbefore);
                } else {
                    svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.fase);
//                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelect).getDirEntry().getURL());
//                    listTestCase = new TestCaseTSRN().readSheet(new SVNPropertiesVOBean().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelect).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelect).getDirEntry().getName());
                    if (svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem())) {
                        SVNLock lock = svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem());

                        if (lock.getOwner().equals(SVNPropertiesVOBean.getInstance().getUser())) {
                            listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                            loadFields(listTestCase.get(0));
                            blockedFieldBnt(true);
                            ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                            ctBaixados.add(ctbefore);
                        } else {
                            JOptionPane.showMessageDialog(null, "O CT selecionado está bloqueado pelo usuário " + lock.getOwner(), "SVN", JOptionPane.WARNING_MESSAGE);
                            new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName()).delete();
                        }

                    } else {
                        svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                        listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                        loadFields(listTestCase.get(0));
                        blockedFieldBnt(true);
                        ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                        ctBaixados.add(ctbefore);

                    }

                }

                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (SVNException ex) {
            logger.error("Erro SVN ", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            logger.error("Erro ", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            logger.error("Erro ", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void loadCT2() {
        try {

            tabelaCt.setSelectionBackground(c);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            // verifica se a linha clicada é a que está selecionada

            if (!ctbefore.equals(modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString())) {

                if (tabelaSteps.getRowCount() > 0) {
                    // tira a seleção dos steps
                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
                }
                //apaga linhas da tabela steps
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }
                logger.info("Verificando se CT Selecionado está locado.");
                if (lineSelectTableCt != -1 && !isCtLocked()) {
                    svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
//                    new File(new SVNPropertiesVOBean().getFolderTemplocal()+this.hashCode()+"\\"+ listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName()).delete();
                    testCaseRN.deleteDir(this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem());
                }
                //captra linha selecioanda
                lineSelectTableCt = tabelaCt.getSelectedRow();
//                if (isDonwloadCt(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getTestCaseName())) {
//                    downloadCtSvn();
//                } else {
                logger.info("Início do método:svnRN.importBySvnForLocalFolder(new SVNPropertiesVOBean().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.hashCode())");
                svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.hashCode(), this.fase);
                logger.info("Parâmetros: \nPasta temp: " + SVNPropertiesVOBean.getInstance().getFolderTemplocal() + "\nSistema: " + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\nCT: " + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                logger.info("Fim do método:svnRN.importBySvnForLocalFolder(new SVNPropertiesVOBean().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.hashCode())");
                downloadCtSvn();
//                }
                int ctSeleciondado = tabelaCt.getSelectedRow() + 1;
                labelQtdCT.setText("Selecionado " + ctSeleciondado + " de " + tabelaCt.getRowCount());
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (SVNException ex) {

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            logger.error("Erro SVN", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            logger.error("Erro", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    private boolean isDonwloadCt(String fileName) {
        boolean donwload = false;

        for (int i = 0; i < ctBaixados.size(); i++) {
            if (fileName.equals(ctBaixados.get(i))) {
                donwload = true;
            }
        }

        return donwload;
    }

    public void cleanFields() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        if (tabelaSteps.getRowCount() > 0) {
            // tira a seleção dos steps
            tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
        }

        while (modelStep.getRowCount() > 0) {
            modelStep.removeRow(0);
        }
        jTextNameTS.setText("");
        jComboFaseCR.setSelectedItem("");
//        jComboSistemasTS.setSelectedItem("");
        jTextAreaDescriptionTS.setText("");
    }

    private boolean isCtLocked() {
        boolean locked = false;
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        try {
            logger.info("Início do método : svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode()))");
            logger.info("hashCode tela: " + this.hashCode());
            logger.info("CT selecionado: " + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
            if (svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode())) {
                logger.info("Fim do método : svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode()))");
                logger.info("Início do método : svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode())");
                SVNLock lock = svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode());
                logger.info("Fim do método : svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode())");
                logger.info("CT bloqueado por :" + lock.getOwner());
                if (lock.getOwner().equals(SVNPropertiesVOBean.getInstance().getUser())) {
                    locked = false;
                } else {
                    locked = true;
//                    JOptionPane.showMessageDialog(null, "O CT selecionado está bloqueado pelo usuário " + lock.getOwner(), "SVN", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                locked = false;

            }
// Variables declaration - do not modify
        } catch (IOException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);

        } catch (SVNException ex) {

            logger.error("Erro SVN ", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);

        }

        return locked;
    }

    private void downloadCtSvn() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        try {
//            svnRN.importBySvnForLocalFolder(new SVNPropertiesVOBean().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
//                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelect).getDirEntry().getURL());
//                    listTestCase = new TestCaseTSRN().readSheet(new SVNPropertiesVOBean().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelect).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelect).getDirEntry().getName());
            if (svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode())) {
                SVNLock lock = svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), this.hashCode());

                if (lock.getOwner().equals(SVNPropertiesVOBean.getInstance().getUser())) {
                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                    listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                    loadFields(listTestCase.get(0));
                    blockedFieldBnt(true);
                    ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
//                    ctBaixados.add(ctbefore);
                } else {
                    blockedFieldBnt(false);
                    tabelaCt.setEnabled(true);
                    bntCancelar.setEnabled(true);
                    bntFiltrar.setEnabled(true);

                    listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                    new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName()).delete();
                    loadFields(listTestCase.get(0));
                    JOptionPane.showMessageDialog(null, "O CT selecionado está bloqueado pelo usuário " + lock.getOwner() + ", não é possível edita-lo.", "SVN", JOptionPane.WARNING_MESSAGE);
//                    tabelaCt.setSelectionBackground(Color.red);
                    lineSelectTableCt = -1;
                }
            } else {

                svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                loadFields(listTestCase.get(0));
                blockedFieldBnt(true);
                ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
//                ctBaixados.add(ctbefore);
            }
// Variables declaration - do not modify
        } catch (IOException ex) {
            logger.error("Erro  ", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            logger.error("Erro SVN ", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public boolean save(TesteCaseTSBean testCase) {
        boolean save = false;
        testCase.setFase(jComboFaseCR.getSelectedItem().toString());
        testCase.setProduct(jComboSistemasTS.getSelectedItem().toString());
        testCase.setTestScriptName(jTextNameTS.getText());
        testCase.setStepDescription(jTextAreaDescriptionTS.getText());

        if (jTextNameTS.getText().length() >= 15 && jTextNameTS.getText().length() <= 55) {
            final Frame GUIPrincipal = new MainScreenView();
            final JInternalFrame ji = this;

            new SwingWorker() {
                JDialog aguarde = new WaitScreenView((JFrame) GUIPrincipal, true, ji);

                @Override
                protected Object doInBackground() throws Exception, SVNException, IOException {
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    aguarde.setLocationRelativeTo(GUIPrincipal);
//                    aguarde.setVisible(true);
                    blockedFieldBnt(false);
                    editCtSVN();

                    return null;
                }

                @Override
                protected void done() {
                    aguarde.dispose();
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }.execute();
        } else {
            JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: O nome do caso de teste tem menos de 15 caracteres."
                    + "\nSolução: Altere o nome caso de teste de 15 à 55 caracteres.", "Alerta", JOptionPane.WARNING_MESSAGE);
        }

        return save;
    }

    private void updateProperties(String system) {
        try {
            SvnConnectionRN svn = new SvnConnectionRN(this.fase);
            int selectionActual = tabelaCt.getSelectedRow();
            logger.info("Método updateProperties : verificando se o nome foi alterado");
            if (!oldName.equalsIgnoreCase(jTextNameTS.getText())) {
                fileDeletedSvn = true;
                logger.info("Método updateProperties : flag fileDeletedSvn setado com true");
            }
            logger.info("Método updateProperties : Realizando busca de CTs ");
            List<TestCaseTSPropertiesBean> listTemp = svn.search(system, getFiltro());
            logger.info("Método updateProperties : Lista " + listTemp);
            logger.info("Método updateProperties : Iniciando carga na tabela de CTs");
            loadTableCt(listTemp);
            logger.info("Método updateProperties : carga na tabela de CTs");
            logger.info("Método updateProperties : linha atual selecionada " + selectionActual);
            tabelaCt.addRowSelectionInterval(selectionActual, selectionActual);
            fileDeletedSvn = false;
            logger.info("Método updateProperties : flag fileDeletedSvn setado com false");
//            tabelaCt.setValueAt(listTemp.get(lineSelectTableCt).getDirEntry().getAuthor(), lineSelectTableCt, 2);
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            String dataFormatada = sdf.format(listTemp.get(lineSelectTableCt).getDirEntry().getDate());
//            tabelaCt.setValueAt(dataFormatada, lineSelectTableCt, 3);
//            listTestCaseTSPropertiesBean.add(position, listTemp.get(0));
        } catch (SVNException ex) {
            logger.error("Erro SVN", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void updatePropertiesDelete(String system) {
        try {
            SvnConnectionRN svn = new SvnConnectionRN(this.fase);

            List<TestCaseTSPropertiesBean> listTemp = svn.search(system, getFiltro());
            int selectionActual = lineSelectTableCt;
            loadTableCt(listTemp);
            fileDeletedSvn = true;
//            tabelaCt.addRowSelectionInterval(selectionActual, selectionActual);
//            tabelaCt.setValueAt(listTemp.get(lineSelectTableCt).getDirEntry().getAuthor(), lineSelectTableCt, 2);
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            String dataFormatada = sdf.format(listTemp.get(lineSelectTableCt).getDirEntry().getDate());
//            tabelaCt.setValueAt(dataFormatada, lineSelectTableCt, 3);
//            listTestCaseTSPropertiesBean.add(position, listTemp.get(0));
        } catch (SVNException ex) {
            logger.error("Erro SVN", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            logger.error("Erro ", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    private void deleteCT(String folder, String fileName, String msn) {

        try {
            svnRN.delete(folder, fileName, msn, this.fase);
            updatePropertiesDelete(folder);
            cleanFields();
        } catch (SVNException ex) {
            logger.error("Erro SVN", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAddStep;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntColar;
    private javax.swing.JButton bntCopiar;
    private javax.swing.JButton bntDeleteStep;
    private javax.swing.JButton bntExcluirCT;
    private javax.swing.JButton bntFiltrar;
    private javax.swing.JButton bntMudaStepDescer;
    private javax.swing.JButton bntMudaStepSubir;
    private javax.swing.JButton bntSalvar;
    private javax.swing.JButton bntSalvarCT;
    private javax.swing.JCheckBox jCheckBoxAutomatizado;
    private javax.swing.JComboBox<String> jComboComplexidade;
    private javax.swing.JComboBox<String> jComboFaseCR;
    private javax.swing.JComboBox<SystemBean> jComboSistemasTS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPopupMenu jPopupTabelaStep;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaDescriptionTS;
    private javax.swing.JTextField jTextNameTS;
    private javax.swing.JLabel labelQtdCT;
    private javax.swing.JTable tabelaCt;
    private javax.swing.JTable tabelaSteps;
    // End of variables declaration//GEN-END:variables

    private void verifyEdition() {

        if (rowBefore != rowAfter) {
            if (isChangeTC()) {
                if (JOptionPane.showConfirmDialog(this, "Deseja salvar suas alterações?", "Informação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    saveTestCaseBD(rowAfter);
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                        loadCTDB();
                }
                loadTableCtDB(testCaseRN.getTesteCaseTSBeanBySystemBeanBD((SystemBean) jComboSistemasTS.getSelectedItem()));

                loadCTDB();
//                    else{
//                        tabelaCt.getSelectionModel().setSelectionInterval(rowBefore, rowBefore);
//                    }
            } else {
                loadTableCtDB(testCaseRN.getTesteCaseTSBeanBySystemBeanBD((SystemBean) jComboSistemasTS.getSelectedItem()));

                loadCTDB();
            }
        } else {
            loadTableCtDB(testCaseRN.getTesteCaseTSBeanBySystemBeanBD((SystemBean) jComboSistemasTS.getSelectedItem()));

            loadCTDB();
        }
    }

    private void verifyChangeToClose() {
        //if (rowBefore != rowAfter) {
        if (isChangeTC()) {
            if (JOptionPane.showConfirmDialog(this, "Deseja salvar suas alterações?", "Informação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                saveTestCaseBD(rowAfter);
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            this.dispose();

        } else {
            this.dispose();
        }
//        } else {
//            this.dispose();
//        }
    }

    public void loadCTDB() {
        blockedFieldBnt(false);
        try {

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));

            //compare cts
            DefaultTableModel model = (DefaultTableModel) tabelaCt.getModel();

            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();

            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }
            // int row = tabelaCt.getSelectedRow();

            int id = Integer.parseInt((String) model.getValueAt(rowAfter, 0));

            testeCaseSelect = testCaseRN.getTesteCaseTSBeanById(id);
            loadFieldsDB(testeCaseSelect);

        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            blockedFieldBnt(true);

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    private boolean isChangeTC() {
        boolean change = false;
        TesteCaseTSBean tcEditing = getEditingTestCase(this.rowBefore);

        if (!tcEditing.getComplexidade().equals(testeCaseSelect.getComplexidade())) {
            return true;
        }
        if (!tcEditing.getTestScriptName().equals(testeCaseSelect.getTestScriptName())) {
            return true;
        }
        if (!tcEditing.getProduct().equals(testeCaseSelect.getProduct())) {
            return true;
        }
        if (!tcEditing.getTestScriptDescription().equalsIgnoreCase(testeCaseSelect.getTestScriptDescription())) {
            return true;
        }
        if (!tcEditing.isAutomatizado() == testeCaseSelect.isAutomatizado()) {
            return true;
        }
        if (!tcEditing.getTestScriptName().equals(testeCaseSelect.getTestScriptName())) {
            return true;
        }
        if (!tcEditing.getFase().equals(testeCaseSelect.getFase())) {
            return true;
        }
        if (!(tcEditing.getListStep().size() == testeCaseSelect.getListStep().size())) {
            return true;
        };

        for (int i = 0; i < tcEditing.getListStep().size(); i++) {
            if (!tcEditing.getListStep().get(i).getDescStep().equals(testeCaseSelect.getListStep().get(i).getDescStep())) {
                return true;
            }

            if (!tcEditing.getListStep().get(i).getResultadoStep().equals(testeCaseSelect.getListStep().get(i).getResultadoStep())) {
                return true;
            }

        }
        return change;

    }

    private TesteCaseTSBean getEditingTestCase(int row) {

        TesteCaseTSBean tc = new TesteCaseTSBean();
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());

        tc.setTestScriptName(jTextNameTS.getText());
        tc.setProduct(jComboSistemasTS.getSelectedItem().toString());
        tc.setComplexidade(jComboComplexidade.getSelectedItem().toString());
        tc.setTestScriptDescription(jTextAreaDescriptionTS.getText() + "\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>");
        tc.setDataPlanejada(d);
        tc.setListStep(new ArrayList<Step>());
        tc.setAutomatizado(jCheckBoxAutomatizado.isSelected());
        tc.setFase(jComboFaseCR.getSelectedItem().toString());
        tc.setTestScriptName(jTextNameTS.getText());
        tc.setIdSystem(((SystemBean) jComboSistemasTS.getSelectedItem()).getId());
        int id = Integer.parseInt(modelCT.getValueAt(row, 0).toString());
        tc.setId(id);

        tc.setCreateDate(testeCaseSelect.getCreateDate());
        tc.setCreatedBy(testeCaseSelect.getCreatedBy());

        tabelaSteps.editingStopped(null);
        for (int cont = 0; cont < tabelaSteps.getRowCount(); cont++) {
            Step step = new Step();
            step.setNomeStep((tabelaSteps.getValueAt(cont, 0)).toString());
            step.setDescStep((tabelaSteps.getValueAt(cont, 1)).toString());
            step.setResultadoStep((tabelaSteps.getValueAt(cont, 2)).toString());
            int idStep = Integer.parseInt(tabelaSteps.getValueAt(cont, 3).toString());
            step.setId(idStep);
            tc.getListStep().add(step);
        }

        return tc;

    }

    public void messageError(String error) {
        JOptionPane.showMessageDialog(null, error, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void messageInfo(String info) {
        JOptionPane.showMessageDialog(null, info, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public void messageWarnnig(String warnning) {
        JOptionPane.showMessageDialog(null, warnning, "Alerta", JOptionPane.WARNING_MESSAGE);
    }

    public int getRowBefore() {
        return rowBefore;
    }

    public void setRowBefore(int rowBefore) {
        this.rowBefore = rowBefore;
    }

    public int getRowAfter() {
        return rowAfter;
    }

    public void setRowAfter(int rowAfter) {
        this.rowAfter = rowAfter;
    }

    private void saveTestCaseBD(int row) {

        TesteCaseTSBean tc = getEditingTestCase(row);
        TestCaseTSRN caseTSRN = new TestCaseTSRN();
        tc = caseTSRN.saveTestCaseTSBD(tc);
        if (tc != null) {
            testeCaseSelect = tc;
            messageInfo("CT salvo com sucesso!");
            rowBefore = 0;
            rowAfter = 0;
        } else {

            messageError("Erro ao salvar CT.");

        }

    }

    private void delete() {
        if (testCaseRN.delete(testeCaseSelect)) {
            messageInfo("CT excluir com sucesso!");
            List<TesteCaseTSBean> listTestCaseTSPropertiesBean = testCaseRN.getTesteCaseTSBeanBySystemBeanBD(((SystemBean) jComboSistemasTS.getSelectedItem()));
            cleanFields();
            loadTableCtDB(listTestCaseTSPropertiesBean);
        } else {
            messageError("Erro ao excluir o CT");
        }
    }

}
