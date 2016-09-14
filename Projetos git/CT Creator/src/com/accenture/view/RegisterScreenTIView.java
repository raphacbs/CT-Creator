/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.control.ExcelDAO;

import com.accenture.control.ManipulaDadosSQLite;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.accenture.view.MainScreenView;
import com.accenture.control.Validacao;
import com.accenture.bean.Plano;
import com.accenture.bean.Step;

import com.accenture.util.TextAreaCellRenderer;
import com.accenture.util.TextAreaCellEditor;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;

import javax.swing.undo.UndoManager;

/**
 *
 * @author raphael.da.silva
 */
public class RegisterScreenTIView extends javax.swing.JInternalFrame {

    String t;
    ExcelDAO conf = new ExcelDAO();
    private ButtonIconBean icon;
    private ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
    private int numeroStep = 0;
    private Plano plano;
    private Step step;
    private Timer timer = new Timer();
    private final long tempo = (1000 * 60);
    private Border bordaCampo, bordaCombo, bordaTextArea;
    private boolean closedSave = false;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            System.out.println("executado às: " + getDataHora());

        }
    };
    private UndoManager undoManager;

    /**
     * Creates new form GUICadCT
     */
    public RegisterScreenTIView() throws IOException, ClassNotFoundException, SQLException {

        initComponents();

        /*
        **Chamando o método de carrega as imagens dos botões
        */
        icon = new ButtonIconBean();
        this.addIconInButton();
        //Inicio CTRL Z e CTRL Y
        undoManager = new UndoManager();
        Document doc = TextAreaDescricao.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {

                System.out.println("Add edit");
                undoManager.addEdit(e.getEdit());

            }
        });

        InputMap im = TextAreaDescricao.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = TextAreaDescricao.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });

        //Fim CTRL Z e CTRL Y
        //Captura borda default dos campos
        bordaCampo = textNomeCT.getBorder();
        bordaCombo = comboCadeia.getBorder();
        bordaTextArea = TextAreaDescricao.getBorder();
        addAsteristicoCamposObrigatorios();

        final Frame GUIPrincipal = new MainScreenView();
        plano = new Plano();
        step = new Step();
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                // carregando os combobox
                List<String> funcionalidade = banco.selectConfALL("TB_FUNCIONALIDADE");
                List<String> criacao = banco.selectConfALL("TB_CRIACAO");
                List<String> sistemaMaster = banco.selectConfALL("TB_SISTEMA_MASTER");
                List<String> tpRequisito = banco.selectConfALL("TB_TP_REQUISITO");
                List<String> cadeia = banco.selectConfALL("TB_CADEIA");
                List<String> cenarioIntegrado = banco.selectConfALL("TB_CENARIO_INTEGRADO");
                List<String> cenarioAutomatizavel = banco.selectConfALL("TB_AUTOMATIZAVEL");
                List<String> type = banco.selectConfALL("TB_TYPE");
                List<String> trg = banco.selectConfALL("TB_TRG");

                //Carrega comboBox criação com os dados da tabela
                int tamanhoCriacao = criacao.size();
                for (int cont = 0; cont < tamanhoCriacao; cont++) {
                    comboCriacao.addItem(criacao.get(cont));
                }

                //Carrega comboBox sistema master com os dados da tabela
                int tamanhoSistemaMaster = sistemaMaster.size();
                for (int cont = 0; cont < tamanhoSistemaMaster; cont++) {
                    comboSistemaMaster.addItem(sistemaMaster.get(cont));
                }

                //Carrega comboBox cadeia com os dados da tabela
                int tamanhoCadeia = cadeia.size();
                for (int cont = 0; cont < tamanhoCadeia; cont++) {
                    comboCadeia.addItem(cadeia.get(cont));
                }

                //Carrega comboBox cenario integrado com os dados da tabela
                int tamanhoCenarioIntegrado = cenarioIntegrado.size();
                for (int cont = 0; cont < tamanhoCenarioIntegrado; cont++) {

                    comboCenarioIntegrado.addItem(cenarioIntegrado.get(cont));
                }
                //Carrega comboBox cenario automatizavel com os dados da tabela
                int tamanhoCenarioAutomatizavel = cenarioAutomatizavel.size();
                for (int cont = 0; cont < tamanhoCenarioAutomatizavel; cont++) {

                    comboCenarioAutomatizavel.addItem(cenarioAutomatizavel.get(cont));
                }

                //Carrega comboBox type com os dados da tabela
                int tamanhoType = type.size();
                for (int cont = 0; cont < tamanhoType; cont++) {

                    comboType.addItem(type.get(cont));
                }

                //Carrega comboBox trg com os dados da tabela
                int tamanhoTrg = trg.size();
                for (int cont = 0; cont < tamanhoTrg; cont++) {

                    comboTrg.addItem(trg.get(cont));
                }

                //Carrega comboBox funcionalidade com os dados da tabela
                int tamanhoFuncionalidade = funcionalidade.size();
                for (int cont = 0; cont < tamanhoFuncionalidade; cont++) {

                    comboFuncionalidade.addItem(funcionalidade.get(cont));
                }

                //Carrega comboBox funcionalidade com os dados da tabela
                int tamanhoTpRequisito = tpRequisito.size();
                for (int cont = 0; cont < tamanhoTpRequisito; cont++) {

                    comboTipoRequisito.addItem(tpRequisito.get(cont));
                }
//                renderTableStep();
                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();
        
        new SwingWorker(){
            
            @Override
             protected Object doInBackground() throws Exception {
                    renderTableStep();
        return null;
        
        
        }
         @Override
            protected void done() {

            }

        }.execute();

//        final long tempo = (1000 * 60);
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//
//            @Override
//            public void run() {
//                System.out.println("executado às: "+getDataHora());
//                
//            }
//        };
        timer.scheduleAtFixedRate(timerTask, tempo, tempo);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaSteps = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        textCenario = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        comboFuncionalidade = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        comboSistemaMaster = new javax.swing.JComboBox();
        textSistemaEnvolvidos = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        textFornecedor = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        textRequisito = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        textSegmento = new javax.swing.JTextField();
        comboCadeia = new javax.swing.JComboBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        textProduto = new javax.swing.JTextField();
        comboCenarioIntegrado = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        comboTipoRequisito = new javax.swing.JComboBox();
        jLabel42 = new javax.swing.JLabel();
        textQtdSistemas = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        comboCenarioAutomatizavel = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        comboType = new javax.swing.JComboBox();
        jLabel45 = new javax.swing.JLabel();
        comboTrg = new javax.swing.JComboBox();
        jLabel46 = new javax.swing.JLabel();
        textSubject = new javax.swing.JTextField();
        comboCriacao = new javax.swing.JComboBox();
        bntAddStep = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        bntDeleteStep = new javax.swing.JButton();
        bntMudaStepSubir = new javax.swing.JButton();
        bntMudaStepDescer = new javax.swing.JButton();
        textNomeCT = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabelComplexidade = new javax.swing.JLabel();
        bntAddStepPadrao = new javax.swing.JButton();
        labelNomeCt = new javax.swing.JLabel();
        labelCenario = new javax.swing.JLabel();
        labelFuncionalidade = new javax.swing.JLabel();
        labelSistemaMaster = new javax.swing.JLabel();
        labelSistemaEnvolvidos = new javax.swing.JLabel();
        labelFornecedor = new javax.swing.JLabel();
        labelTipoRequisito = new javax.swing.JLabel();
        labelCriacao = new javax.swing.JLabel();
        labelRequisito = new javax.swing.JLabel();
        labelSubject = new javax.swing.JLabel();
        labelCadeia = new javax.swing.JLabel();
        labelSegmento = new javax.swing.JLabel();
        labelCenarioIntegrado = new javax.swing.JLabel();
        labelCenarioAuto = new javax.swing.JLabel();
        labelType = new javax.swing.JLabel();
        labelTrg = new javax.swing.JLabel();
        labelQtdSistemas = new javax.swing.JLabel();
        labelProduto = new javax.swing.JLabel();
        labelDescricao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAreaDescricao = new javax.swing.JTextArea();
        bntCancelar = new javax.swing.JButton();
        bntSalvarPlano1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Caso de Teste TI");
        setPreferredSize(new java.awt.Dimension(1200, 650));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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

        tabelaSteps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome Step", "Descrição", "Resultado Esperado", "COD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false
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
        jScrollPane6.setViewportView(tabelaSteps);
        tabelaSteps.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tabelaSteps.getColumnModel().getColumnCount() > 0) {
            tabelaSteps.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(0).setMaxWidth(100);
            tabelaSteps.getColumnModel().getColumn(1).setResizable(false);
            tabelaSteps.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor());
            tabelaSteps.getColumnModel().getColumn(2).setResizable(false);
            tabelaSteps.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor());
            tabelaSteps.getColumnModel().getColumn(3).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(3).setMaxWidth(0);
        }
        //TableColumn col;
        //           for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
            //            col = tabelaSteps.getColumnModel().getColumn(i);
            //            col.setCellRenderer(new TextAreaCellRenderer());
            //        }

        jLabel29.setText("CENÁRIO:");

        jLabel30.setText("FUNCIONALIDADE/SERVIÇO:");

        comboFuncionalidade.setAutoscrolls(true);

        jLabel31.setText("SISTEMA MASTER:");

        comboSistemaMaster.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboSistemaMasterFocusLost(evt);
            }
        });

        textSistemaEnvolvidos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textSistemaEnvolvidosFocusLost(evt);
            }
        });

        jLabel32.setText("SISTEMAS ENVOLVIDOS:");

        jLabel33.setText("FORNECEDOR:");

        jLabel35.setText("TIPO REQUISITO:");

        jLabel36.setText("REQUISITO:");

        jLabel37.setText("CADEIA:");

        jLabel38.setText("SEGMENTO:");

        jLabel39.setText("PRODUTO:");

        jLabel40.setText("CENÁRIO INTEGRADO:");

        jLabel42.setText("QTD SISTEMAS:");

        textQtdSistemas.setEnabled(false);
        textQtdSistemas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textQtdSistemasKeyTyped(evt);
            }
        });

        jLabel43.setText("CENÁRIO AUTOMATIZÁVEL?");

        jLabel44.setText("TYPE:");
        jLabel44.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel44AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel45.setText("TRG:");
        jLabel45.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel45AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel46.setText("SUBJECT:");

        textSubject.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textSubjectKeyTyped(evt);
            }
        });

        bntAddStep.setText("Add Step");
        bntAddStep.setActionCommand("Add Step");
        bntAddStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAddStepMouseClicked(evt);
            }
        });

        jLabel47.setText("CRIAÇÃO/ALTERAÇÃO");

        bntDeleteStep.setText("Deletar Step");
        bntDeleteStep.setActionCommand("");
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

        bntMudaStepSubir.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                bntMudaStepSubirMouseMoved(evt);
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

        textNomeCT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textNomeCT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textNomeCTFocusLost(evt);
            }
        });

        jLabel48.setText("NOME CT:");

        jLabel49.setText("DESCRIÇÃO:");

        jLabel41.setText("COMPLEXIDADE:");

        jLabelComplexidade.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelComplexidade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelComplexidade.setText("N/A.");
        jLabelComplexidade.setToolTipText("");

        bntAddStepPadrao.setText("Add Step Padrão");
        bntAddStepPadrao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAddStepPadraoMouseClicked(evt);
            }
        });
        bntAddStepPadrao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddStepPadraoActionPerformed(evt);
            }
        });

        labelNomeCt.setForeground(new java.awt.Color(255, 0, 0));
        labelNomeCt.setText("*");

        labelCenario.setForeground(new java.awt.Color(255, 0, 0));
        labelCenario.setText("*");

        labelFuncionalidade.setForeground(new java.awt.Color(255, 0, 0));
        labelFuncionalidade.setText("*");

        labelSistemaMaster.setForeground(new java.awt.Color(255, 0, 0));
        labelSistemaMaster.setText("*");

        labelSistemaEnvolvidos.setForeground(new java.awt.Color(255, 0, 0));
        labelSistemaEnvolvidos.setText("*");

        labelFornecedor.setForeground(new java.awt.Color(255, 0, 0));
        labelFornecedor.setText("*");

        labelTipoRequisito.setForeground(new java.awt.Color(255, 0, 0));
        labelTipoRequisito.setText("*");

        labelCriacao.setForeground(new java.awt.Color(255, 0, 0));
        labelCriacao.setText("*");

        labelRequisito.setForeground(new java.awt.Color(255, 0, 0));
        labelRequisito.setText("*");

        labelSubject.setForeground(new java.awt.Color(255, 0, 0));
        labelSubject.setText("*");

        labelCadeia.setForeground(new java.awt.Color(255, 0, 0));
        labelCadeia.setText("*");

        labelSegmento.setForeground(new java.awt.Color(255, 0, 0));
        labelSegmento.setText("*");

        labelCenarioIntegrado.setForeground(new java.awt.Color(255, 0, 0));
        labelCenarioIntegrado.setText("*");

        labelCenarioAuto.setForeground(new java.awt.Color(255, 0, 0));
        labelCenarioAuto.setText("*");

        labelType.setForeground(new java.awt.Color(255, 0, 0));
        labelType.setText("*");

        labelTrg.setForeground(new java.awt.Color(255, 0, 0));
        labelTrg.setText("*");

        labelQtdSistemas.setForeground(new java.awt.Color(255, 0, 0));
        labelQtdSistemas.setText("*");

        labelProduto.setForeground(new java.awt.Color(255, 0, 0));
        labelProduto.setText("*");

        labelDescricao.setForeground(new java.awt.Color(255, 0, 0));
        labelDescricao.setText("*");

        TextAreaDescricao.setColumns(20);
        TextAreaDescricao.setRows(5);
        jScrollPane1.setViewportView(TextAreaDescricao);
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        SpellChecker.registerDictionaries(null, null);
        SpellChecker.register(TextAreaDescricao);
        TextAreaDescricao.setLineWrap(true);
        TextAreaDescricao.setDragEnabled(true);
        TextAreaDescricao.setWrapStyleWord(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNomeCt, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCenario, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFuncionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSistemaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSistemaEnvolvidos, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTipoRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(comboSistemaMaster, javax.swing.GroupLayout.Alignment.LEADING, 0, 209, Short.MAX_VALUE)
                                                .addComponent(comboFuncionalidade, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textCenario, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(textFornecedor, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(textSistemaEnvolvidos, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(comboTipoRequisito, javax.swing.GroupLayout.Alignment.LEADING, 0, 209, Short.MAX_VALUE))
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(textSubject, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(comboCriacao, javax.swing.GroupLayout.Alignment.LEADING, 0, 209, Short.MAX_VALUE)
                                                .addComponent(textRequisito, javax.swing.GroupLayout.Alignment.LEADING)))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(bntAddStepPadrao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(bntAddStep)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntDeleteStep)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntMudaStepSubir)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntMudaStepDescer))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel37)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelCadeia, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel38)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel40)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelCenarioIntegrado, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel43)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelCenarioAuto, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel44)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelType, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel45)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelTrg, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel42)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelQtdSistemas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel39)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel41))
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(textQtdSistemas, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboCenarioAutomatizavel, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboCenarioIntegrado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(textSegmento, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboTrg, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabelComplexidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                            .addComponent(comboCadeia, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(textProduto)))))
                            .addComponent(textNomeCT))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(textNomeCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(labelNomeCt)
                    .addComponent(labelDescricao))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(labelCenario)))
                            .addComponent(textCenario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel30))
                            .addComponent(comboFuncionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFuncionalidade))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(labelSistemaMaster)))
                            .addComponent(comboSistemaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32)
                                    .addComponent(labelSistemaEnvolvidos)))
                            .addComponent(textSistemaEnvolvidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(labelFornecedor)))
                            .addComponent(textFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(comboTipoRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTipoRequisito))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelRequisito))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCriacao))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSubject)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelCadeia))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelSegmento))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(8, 8, 8)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel40)
                                                .addComponent(comboCenarioIntegrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(11, 11, 11)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel43)
                                                .addComponent(labelCenarioAuto))
                                            .addGap(14, 14, 14)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel44)
                                                .addComponent(labelType))
                                            .addGap(11, 11, 11)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel45)
                                                .addComponent(labelTrg))
                                            .addGap(17, 17, 17)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel42)
                                                .addComponent(labelQtdSistemas))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(textProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelProduto)))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(labelCenarioIntegrado))))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(comboCadeia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(62, 62, 62)
                                    .addComponent(comboCenarioAutomatizavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(8, 8, 8)
                                    .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(8, 8, 8)
                                    .addComponent(comboTrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(8, 8, 8)
                                    .addComponent(textQtdSistemas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(7, 7, 7)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelComplexidade)))))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntAddStep)
                    .addComponent(bntDeleteStep)
                    .addComponent(bntMudaStepSubir)
                    .addComponent(bntMudaStepDescer)
                    .addComponent(bntAddStepPadrao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        textCenario.getDocument().addDocumentListener(
            new DocumentListener(){

                @Override
                public void insertUpdate(DocumentEvent e) {

                    //                bntSalvarPlano1.setEnabled(true);

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    //                bntSalvarPlano1.setEnabled(true);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    bntSalvarPlano1.setEnabled(true);
                    textCenario.setBackground(Color.blue);
                    System.out.println("Teste");
                }

            });
            comboFuncionalidade.addItem("");
            comboSistemaMaster.addItem("");
            comboCadeia.addItem("");
            comboCenarioIntegrado.addItem("");
            comboTipoRequisito.addItem("");
            textQtdSistemas.setText("0");
            comboCenarioAutomatizavel.addItem("");
            comboType.addItem("");
            comboTrg.addItem("");
            comboCriacao.addItem("");
            textCenario.getDocument().addDocumentListener(
                new DocumentListener(){

                    @Override
                    public void insertUpdate(DocumentEvent e) {

                        //                bntSalvarPlano1.setEnabled(true);

                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        //                bntSalvarPlano1.setEnabled(true);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        bntSalvarPlano1.setEnabled(true);
                        textCenario.setBackground(Color.blue);
                        System.out.println("Teste");
                    }

                });
                labelNomeCt.setVisible(false);
                labelCenario.setVisible(false);
                labelFuncionalidade.setVisible(false);
                labelSistemaMaster.setVisible(false);
                labelSistemaEnvolvidos.setVisible(false);
                labelFornecedor.setVisible(false);
                labelTipoRequisito.setVisible(false);
                labelCriacao.setVisible(false);
                labelRequisito.setVisible(false);
                labelSubject.setVisible(false);
                labelCadeia.setVisible(false);
                labelSegmento.setVisible(false);
                labelCenarioIntegrado.setVisible(false);
                labelCenarioAuto.setVisible(false);
                labelType.setVisible(false);
                labelTrg.setVisible(false);
                labelQtdSistemas.setVisible(false);
                labelProduto.setVisible(false);
                labelDescricao.setVisible(false);

                bntCancelar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
                bntCancelar.setText("Cancelar");
                bntCancelar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        bntCancelarActionPerformed(evt);
                    }
                });

                bntSalvarPlano1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
                bntSalvarPlano1.setText("Salvar");
                bntSalvarPlano1.setBorder(null);
                bntSalvarPlano1.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        bntSalvarPlano1MouseClicked(evt);
                    }
                });
                bntSalvarPlano1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        bntSalvarPlano1ActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(398, Short.MAX_VALUE)
                        .addComponent(bntSalvarPlano1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(398, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bntSalvarPlano1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    public int getNumeroStep() {
        return numeroStep;
    }

    public void setNumeroStep(int numeroStep) {
        this.numeroStep = numeroStep;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public String getValorTextAreaDescricao() {
        return TextAreaDescricao.getText();
    }

    public void setValorTextAreaDescricao(String valor) {
        this.TextAreaDescricao.setText(valor);
    }

    public String getValorComboCadeia() {
        return comboCadeia.getSelectedItem().toString();
    }

    public void setValorComboCadeia(String valor) {
        this.comboCadeia.setSelectedItem(valor);
    }

    public String getValorComboCenarioAutomatizavel() {
        return comboCenarioAutomatizavel.getSelectedItem().toString();
    }

    public void setValorComboCenarioAutomatizavel(String valor) {
        this.comboCenarioAutomatizavel.setSelectedItem(valor);
    }

    public JComboBox getValorComboCenarioIntegrado() {
        return comboCenarioIntegrado;
    }

    public void setValorComboCenarioIntegrado(String valor) {
        this.comboCenarioIntegrado.setSelectedItem(valor);
    }

    public String getValorComboCriacao() {
        return comboCriacao.getSelectedItem().toString();
    }

    public void setValorComboCriacao(String valor) {
        this.comboCriacao.setSelectedItem(valor);
    }

    public String getValorComboFuncionalidade() {
        return comboFuncionalidade.getSelectedItem().toString();
    }

    public void setValorComboFuncionalidade(String valor) {
        this.comboFuncionalidade.setSelectedItem(valor);
    }

    public String getValorComboSistemaMaster() {
        return comboSistemaMaster.getSelectedItem().toString();
    }

    public void setValorComboSistemaMaster(String valor) {
        this.comboSistemaMaster.setSelectedItem(valor);
    }

    public String getValorComboTipoRequisito() {
        return comboTipoRequisito.getSelectedItem().toString();
    }

    public void setValorComboTipoRequisito(String valor) {
        this.comboTipoRequisito.setSelectedItem(valor);
    }

    public String getValorComboTrg() {
        return comboTrg.getSelectedItem().toString();
    }

    public void setValorComboTrg(String valor) {
        this.comboTrg.setSelectedItem(valor);
    }

    public String getValorComboType() {
        return comboType.getSelectedItem().toString();
    }

    public void setValorComboType(String valor) {
        this.comboType.setSelectedItem(valor);
    }

    public List<Step> getValorTabelaSteps() {
        List<Step> stepList = new ArrayList<Step>();
        Step s = new Step();
        //model da tabela step
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            s.setDescStep(model.getValueAt(i, 0).toString());
            s.setNomeStep(model.getValueAt(i, 1).toString());
            s.setResultadoStep(model.getValueAt(i, 2).toString());

            stepList.add(i, s);
        }

        return stepList;
    }

    public void setTabelaSteps(List<Step> listStep) {
        //model da tabela step
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        for (int i = 0; i < listStep.size(); i++) {
            //add nova linha na tabela
            model.addRow(new String[]{"Step " + (i + 1), listStep.get(i).getDescStep(), listStep.get(i).getResultadoStep(), ""});

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

    public String getValorTextCenario() {
        return textCenario.getText();
    }

    public void setValorTextCenario(String valor) {
        this.textCenario.setText(valor);
    }

    public String getValorTextFornecedor() {
        return textFornecedor.getText();
    }

    public void setValorTextFornecedor(String valor) {
        this.textFornecedor.setText(valor);
    }

    public String getValorTextNomeCT() {
        return textNomeCT.getText();
    }

    public void setValorTextNomeCT(String valor) {
        this.textNomeCT.setText(valor);
    }

    public String getValorTextProduto() {
        return textProduto.getText();
    }

    public void setValorTextProduto(String valor) {
        this.textProduto.setText(valor);
    }

    public String getValorTextQtdSistemas() {
        return textQtdSistemas.getText();
    }

    public void setValorTextQtdSistemas(String valor) {
        this.textQtdSistemas.setText(valor);
    }

    public String getValorTextRequisito() {
        return textRequisito.getText();
    }

    public void setValorTextRequisito(String valor) {
        this.textRequisito.setText(valor);
    }

    public String getValorTextSegmento() {
        return textSegmento.getText();
    }

    public void setValorTextSegmento(String valor) {
        this.textSegmento.setText(valor);
    }

    public String getValorTextSistemaEnvolvidos() {
        return textSistemaEnvolvidos.getText();
    }

    public void setValorTextSistemaEnvolvidos(String valor) {
        this.textSistemaEnvolvidos.setText(valor);
    }

    public String getValorTextSubject() {
        return textSubject.getText();
    }

    public void setValorTextSubject(String valor) {
        this.textSubject.setText(valor);
    }

    private void textSistemaEnvolvidosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textSistemaEnvolvidosFocusLost
        if (textSistemaEnvolvidos.getText().equals("")) {

            textQtdSistemas.setText("0");
        } else {
            String[] sistemas = textSistemaEnvolvidos.getText().split(";");

            textQtdSistemas.setText(sistemas.length + "");

        }
        calculaComplexidade();
    }//GEN-LAST:event_textSistemaEnvolvidosFocusLost

    private void jLabel44AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel44AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel44AncestorAdded

    private void jLabel45AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel45AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45AncestorAdded

    private void bntAddStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAddStepMouseClicked

        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //model da tabela step
            DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
            //add nova linha na tabela
            model.addRow(new String[]{"Step " + 1, "<<<parametro>>>", "<<<paramentro>>>", ""});

            //ordena a numeração dos steps
            int numLinhas = model.getRowCount();
            for (int j = 0; j < numLinhas; j++) {
                numeroStep = j + 1;
                System.out.println("Antes: " + model.getValueAt(j, 0));
                model.setValueAt("Step " + numeroStep, j, 0);
                System.out.println("Depois: " + model.getValueAt(j, 0));

            }

            calculaComplexidade();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JScrollBar bar = jScrollPane6.getVerticalScrollBar();
                    bar.setValue(bar.getMaximum());
                }
            });
        }


    }//GEN-LAST:event_bntAddStepMouseClicked

    private void bntDeleteStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntDeleteStepMouseClicked
        if (tabelaSteps.getSelectedRow() != -1) {

            Step stepDelete = new Step();
            Object obj = (tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 0));
            stepDelete.setNomeStep((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 0)).toString());
            stepDelete.setDescStep((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 1)).toString());
            stepDelete.setResultadoStep((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 2)).toString());
//            stepDelete.setId(Integer.parseInt((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 3)).toString()));
            String nomeStepSelecionado = obj.toString();

            if (JOptionPane.showConfirmDialog(this, "Deseja excluir o " + nomeStepSelecionado + "?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int indeceRemove = tabelaSteps.getSelectedRow();
                tabelaSteps.removeEditor();
                ((DefaultTableModel) tabelaSteps.getModel()).removeRow(indeceRemove);

                DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
                //                    bntSalvarPlano.setEnabled(true);
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

            } else {

            }

        } else {
            JOptionPane.showMessageDialog(this, "Favor selecionar Step que deseja excluir.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }

        calculaComplexidade();

    }//GEN-LAST:event_bntDeleteStepMouseClicked

    private void bntMudaStepSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepSubirActionPerformed

        // model da tabela step
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        //captura o número de linhas da tabela
        int numLinhas = model.getRowCount();
        //captura linha selecionada
        int indeciLinhaSelecionada = tabelaSteps.getSelectedRow();

        //interrompe a edição da celula
        tabelaSteps.editingStopped(null);

        //verifica se existe mais de uma linha
        if (numLinhas > 0) {

            //método move a linha
            model.moveRow(indeciLinhaSelecionada, indeciLinhaSelecionada, indeciLinhaSelecionada - 1);
            //organiza steps
            organizaNumeracaoStep(numLinhas, model);
        }

        tabelaSteps.clearSelection();


    }//GEN-LAST:event_bntMudaStepSubirActionPerformed

    private void bntMudaStepDescerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepDescerActionPerformed
        // model da tabela step
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        //captura o número de linhas da tabela
        int numLinhas = model.getRowCount();
        //captura linha selecionada
        int indeciLinhaSelecionada = tabelaSteps.getSelectedRow();

        //interrompe a edição da celula
        tabelaSteps.editingStopped(null);

        //verifica se existe mais de uma linha
        if (numLinhas > 0) {

            //método move a linha
            model.moveRow(indeciLinhaSelecionada, indeciLinhaSelecionada, indeciLinhaSelecionada + 1);
            //organiza steps
            organizaNumeracaoStep(numLinhas, model);
        }

        tabelaSteps.clearSelection();


    }//GEN-LAST:event_bntMudaStepDescerActionPerformed

    private void textNomeCTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textNomeCTFocusLost
//        Plano p = new Plano();
//        Plano p2 = new Plano();
//        List<Plano> listPlano = new ArrayList<Plano>();
//
//        p.setCasoTeste(textNomeCT.getText().toString());
//
//        try {
//            p2 = banco.verificaSeCtExiste(p);
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//
//        if (p2.getCasoTeste() == null) {
//            p2.setCasoTeste(" ");
//        }
//
//        if (p2.getCasoTeste().equals(p.getCasoTeste())) {
//            if (JOptionPane.showConfirmDialog(this, "O CT que você está tentando criar já existe, deseja editalo? ", "CT Duplicado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//                try {
//                    p = banco.getPorCasoTeste(p);
//                    listPlano.add(p);
//
//                } catch (SQLException ex) {
//                    showMessageDialogErr("ERRO: " + ex.getMessage(), "Erro");
//                } catch (ClassNotFoundException ex) {
//                    showMessageDialogErr("ERRO: " + ex.getMessage(), "Erro");
//                }
//            }
//        }

        // inicio - defeito 3
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {

                Plano p = new Plano();
                Plano p2 = new Plano();
                List<Plano> listPlano = new ArrayList<Plano>();

                p.setCasoTeste(textNomeCT.getText().toString());

                try {
                    p2 = banco.verificaSeCtExiste(p);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

                if (p2.getCasoTeste() == null) {
                    p2.setCasoTeste(" ");
                }

                if (p2.getCasoTeste().equals(p.getCasoTeste())) {
                    if (JOptionPane.showConfirmDialog(null, "O CT que você está tentando criar já existe, deseja edita-lo? ", "CT Duplicado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            String sql = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO AND CASO_TESTE LIKE '" + p.getCasoTeste() + "'";
                            listPlano = banco.getCTsPorQuery(sql);

                            atualizaTabelaPlanosGUIMatem(listPlano);

                        } catch (SQLException ex) {
                            showMessageDialogErr("ERRO: " + ex.getMessage(), "Erro");
                        } catch (ClassNotFoundException ex) {
                            showMessageDialogErr("ERRO: " + ex.getMessage(), "Erro");
                        }
                    }
                }

//                criaJanelaTelaCadCT();
                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();

        // fim defeito 3

    }//GEN-LAST:event_textNomeCTFocusLost

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed

        if (JOptionPane.showConfirmDialog(this, "Ao sair da tela seus dados serão perdidos, deseja realmente sair?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
        }

    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntSalvarPlano1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntSalvarPlano1MouseClicked
        final Frame GUIPrincipal = new MainScreenView();

        RegisterScreenTIView teste;
        plano.setCasoTeste(textNomeCT.getText());

        new SwingWorker() {
            JDialog aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                setBordaPadraoCampos();

                DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

                if (validaObrigatoriedadeCampos() && validaQtdStep() && !verificaSeCTExiste(plano)) {
                    salvaDados();
                    salvaDadosStep();

                    showMessageDialogInf("CT salvo com sucesso!", "Cadastro de CT");
                    fechaJanelaInterna();
                }
                return null;
            }

            @Override
            protected void done() {
                aguarde.dispose();
                
            }

        }.execute();


    }//GEN-LAST:event_bntSalvarPlano1MouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing

        if (!closedSave && JOptionPane.showConfirmDialog(this, "Ao sair da tela seus dados serão perdidos, deseja realmente sair?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
        }

    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed

    }//GEN-LAST:event_formInternalFrameClosed

    private void textQtdSistemasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textQtdSistemasKeyTyped
        String caracteres = "0987654321";
        if (caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_textQtdSistemasKeyTyped

    private void textSubjectKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSubjectKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textSubjectKeyTyped

    private void bntAddStepPadraoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAddStepPadraoMouseClicked
        ChooseStepScreenView dialogStep = null;
        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                dialogStep = new ChooseStepScreenView(this, null, true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            dialogStep.centralizaJanela(this);
            dialogStep.setVisible(true);

            calculaComplexidade();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JScrollBar bar = jScrollPane6.getVerticalScrollBar();
                    bar.setValue(bar.getMaximum());
                }
            });
        }
    }//GEN-LAST:event_bntAddStepPadraoMouseClicked

    private void comboSistemaMasterFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboSistemaMasterFocusLost
        textSistemaEnvolvidos.setText(comboSistemaMaster.getSelectedItem().toString());
        if (textSistemaEnvolvidos.getText().equals("")) {

            textQtdSistemas.setText("0");
        } else {
            String[] sistemas = textSistemaEnvolvidos.getText().split(";");

            textQtdSistemas.setText(sistemas.length + "");

        }
        calculaComplexidade();
    }//GEN-LAST:event_comboSistemaMasterFocusLost

    private void bntSalvarPlano1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalvarPlano1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bntSalvarPlano1ActionPerformed

    private void bntDeleteStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDeleteStepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bntDeleteStepActionPerformed

    private void bntMudaStepSubirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntMudaStepSubirMouseMoved
        // TODO add your handling code here:
        bntMudaStepSubir.setToolTipText("Subir step");
    }//GEN-LAST:event_bntMudaStepSubirMouseMoved

    private void bntAddStepPadraoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddStepPadraoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bntAddStepPadraoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TextAreaDescricao;
    private javax.swing.JButton bntAddStep;
    private javax.swing.JButton bntAddStepPadrao;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntDeleteStep;
    private javax.swing.JButton bntMudaStepDescer;
    private javax.swing.JButton bntMudaStepSubir;
    private javax.swing.JButton bntSalvarPlano1;
    private javax.swing.JComboBox comboCadeia;
    private javax.swing.JComboBox comboCenarioAutomatizavel;
    private javax.swing.JComboBox comboCenarioIntegrado;
    private javax.swing.JComboBox comboCriacao;
    private javax.swing.JComboBox comboFuncionalidade;
    private javax.swing.JComboBox comboSistemaMaster;
    private javax.swing.JComboBox comboTipoRequisito;
    private javax.swing.JComboBox comboTrg;
    private javax.swing.JComboBox comboType;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabelComplexidade;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelCadeia;
    private javax.swing.JLabel labelCenario;
    private javax.swing.JLabel labelCenarioAuto;
    private javax.swing.JLabel labelCenarioIntegrado;
    private javax.swing.JLabel labelCriacao;
    private javax.swing.JLabel labelDescricao;
    private javax.swing.JLabel labelFornecedor;
    private javax.swing.JLabel labelFuncionalidade;
    private javax.swing.JLabel labelNomeCt;
    private javax.swing.JLabel labelProduto;
    private javax.swing.JLabel labelQtdSistemas;
    private javax.swing.JLabel labelRequisito;
    private javax.swing.JLabel labelSegmento;
    private javax.swing.JLabel labelSistemaEnvolvidos;
    private javax.swing.JLabel labelSistemaMaster;
    private javax.swing.JLabel labelSubject;
    private javax.swing.JLabel labelTipoRequisito;
    private javax.swing.JLabel labelTrg;
    private javax.swing.JLabel labelType;
    private javax.swing.JTable tabelaSteps;
    private javax.swing.JTextField textCenario;
    private javax.swing.JTextField textFornecedor;
    private javax.swing.JTextField textNomeCT;
    private javax.swing.JTextField textProduto;
    private javax.swing.JTextField textQtdSistemas;
    private javax.swing.JTextField textRequisito;
    private javax.swing.JTextField textSegmento;
    private javax.swing.JTextField textSistemaEnvolvidos;
    private javax.swing.JTextField textSubject;
    // End of variables declaration//GEN-END:variables
    EditStepDefaultScreenView guiJanelaStepPadrao;

    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public boolean salvaDados() {
//        Plano plano = new Plano();
        Step step = new Step();

        try {
            if (comboCriacao != null) {
                plano.setCriacaoAlteracao(comboCriacao.getSelectedItem().toString());
            } else {
                plano.setCriacaoAlteracao(comboCriacao.getSelectedItem().toString());
            }

            plano.setSubject(textSubject.getText());
            plano.setTrg(comboTrg.getSelectedItem().toString());
            if (comboTrg != null) {

            }
            plano.setCasoTeste(textNomeCT.getText());
            plano.setType(comboType.getSelectedItem().toString());
            plano.setCenarioAutomatizavel(comboCenarioAutomatizavel.getSelectedItem().toString());
            plano.setQtdSistemas(Integer.parseInt(textQtdSistemas.getText()));
            plano.setDescCasoTeste(TextAreaDescricao.getText());
            plano.setCenarioTeste(textCenario.getText());
            plano.setTpRequisito(comboTipoRequisito.getSelectedItem().toString());
            plano.setRequisito(textRequisito.getText());
            plano.setFornecedor(textFornecedor.getText());
            plano.setSistemasEnvolvidos(textSistemaEnvolvidos.getText());
            plano.setSistemaMaster(comboSistemaMaster.getSelectedItem().toString());
            plano.setCenarioIntegrado(comboCenarioIntegrado.getSelectedItem().toString());
            plano.setFuncionalidade(comboFuncionalidade.getSelectedItem().toString());
            plano.setProduto(textProduto.getText());
            plano.setSegmento(textSegmento.getText());
            plano.setCadeia(comboCadeia.getSelectedItem().toString());

            banco.insertPlano(plano);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + e.getMessage() + e.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean salvaDadosStep() {
//        Plano plano = new Plano();
//        Step step = new Step();

        try {
            Plano idPlano = new Plano();

            System.out.println("nome CT: " + plano.getCasoTeste());

            idPlano = banco.verificaSeCtExiste(plano);
            step.setIdPlano(idPlano.getId());
            System.out.println("ID_PANO_DO_STEP: " + step.getIdPlano());

            tabelaSteps.editingStopped(null);

            for (int cont = 0; cont < tabelaSteps.getRowCount(); cont++) {
                step.setNomeStep((tabelaSteps.getValueAt(cont, 0)).toString());
                step.setDescStep((tabelaSteps.getValueAt(cont, 1)).toString());
                step.setResultadoStep((tabelaSteps.getValueAt(cont, 2)).toString());
//                step.setId(Integer.parseInt((tabelaSteps.getValueAt(cont, 3)).toString()));
                Validacao val = new Validacao(tabelaSteps.getValueAt(cont, 1).toString());
                plano.setStep(step);
                banco.insertStep(plano);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + e.getMessage() + e.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void criaCT() {
        plano.setCriacaoAlteracao("");
        plano.setSubject("");
        plano.setTrg("");
        plano.setType("");
        plano.setCenarioAutomatizavel("");
        plano.setQtdSistemas(0);
        plano.setDescCasoTeste("");
        plano.setCenarioTeste("");
        plano.setRequisito("");
        plano.setTpRequisito("");
        plano.setFornecedor("");
        plano.setSistemasEnvolvidos("");
        plano.setSistemaMaster("");
        plano.setCenarioIntegrado("");
        plano.setFuncionalidade("");
        plano.setProduto("");
        plano.setSegmento("");
        plano.setCadeia("");

        comboCriacao.setSelectedItem("");
        textSubject.setText("");
        comboTrg.setSelectedItem("");
        comboType.setSelectedItem("");
        comboCenarioAutomatizavel.setSelectedItem("");
        textQtdSistemas.setText("");
        TextAreaDescricao.setText("");
        textCenario.setText("");
        textRequisito.setText("");
        comboTipoRequisito.setSelectedItem("");
        textFornecedor.setText("");
        textSistemaEnvolvidos.setText("");
        comboSistemaMaster.setSelectedItem("");
        comboCenarioIntegrado.setSelectedItem("");
        comboFuncionalidade.setSelectedItem("");
        textProduto.setText("");
        textSegmento.setText("");
        comboCadeia.setSelectedItem("");

        DefaultTableModel modelSteps = (DefaultTableModel) tabelaSteps.getModel();

        if (modelSteps.getRowCount() != 0) {
            int numLinhas = modelSteps.getRowCount();
            for (int i = 0; i <= modelSteps.getRowCount(); i++) {
                //                model.removeRow(0);
                modelSteps.setRowCount(0);
            }

        }

        try {
            int idProximoPlano = banco.selectProximoIdPlano();

//            DefaultTableModel modelPlanos = (DefaultTableModel) tabelaPlanos.getModel();
//            modelPlanos.addRow(new String[]{"", "New CT " + idProximoPlano});
            step.setNomeStep("Step 1");
            step.setDescStep("<<<parametro>>>");
            step.setResultadoStep("<<<parametro>>>");

            modelSteps.addRow(new String[]{"Step 1", step.getDescStep() + "", step.getResultadoStep() + ""});

            step.setIdPlano(idProximoPlano);
            plano.setCasoTeste("New CT " + idProximoPlano);
            plano.setStep(step);

            banco.insertPlano(plano);
            banco.insertStep(plano);

            textNomeCT.setText(plano.getCasoTeste());

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getDataHora() {

        // cria um StringBuilder
        StringBuilder sb = new StringBuilder();

        // cria um GregorianCalendar que vai conter a hora atual
        GregorianCalendar d = new GregorianCalendar();

        //anexa do StringBuilder os dados da data
        sb.append(d.get(GregorianCalendar.DAY_OF_MONTH));
        sb.append("/");
        sb.append(d.get(GregorianCalendar.MONTH) + 1);
        sb.append("/");
        sb.append(d.get(GregorianCalendar.YEAR));
        sb.append(" - ");
        // anexa do StringBuilder os dados da hora
        sb.append(d.get(GregorianCalendar.HOUR_OF_DAY));
        sb.append(":");
        sb.append(d.get(GregorianCalendar.MINUTE));
        sb.append(":");
        sb.append(d.get(GregorianCalendar.SECOND));

        // retorna a String do StringBuilder
        return sb.toString();

    }

    private void salvaCT() {
        System.out.println("Campos OK");
    }

    public void setBordaPadraoCampos() {

        textNomeCT.setBorder(bordaCampo);
        comboFuncionalidade.setBorder(bordaCombo);
        comboSistemaMaster.setBorder(bordaCombo);
        textSistemaEnvolvidos.setBorder(bordaCampo);
        textFornecedor.setBorder(bordaCampo);
        textCenario.setBorder(bordaCampo);
        comboTipoRequisito.setBorder(bordaCombo);
        textRequisito.setBorder(bordaCampo);
        comboCriacao.setBorder(bordaCombo);
        comboCadeia.setBorder(bordaCombo);
        textSegmento.setBorder(bordaCampo);
        comboCenarioIntegrado.setBorder(bordaCombo);
        comboCenarioAutomatizavel.setBorder(bordaCombo);
        comboType.setBorder(bordaCombo);
        comboTrg.setBorder(bordaCombo);
        TextAreaDescricao.setBorder(bordaTextArea);
        textQtdSistemas.setBorder(bordaCampo);
        textProduto.setBorder(bordaCampo);
        textSubject.setBorder(bordaCampo);

    }

    private boolean validaObrigatoriedadeCampos() {
        List<String> listCamposObrigatorios = new ArrayList<String>();
        boolean obrigatorio = false;
        try {
            Validacao validacao = new Validacao();
            listCamposObrigatorios = validacao.campoObrigatorios();

            for (int i = 0; i < listCamposObrigatorios.size(); i++) {

                switch (listCamposObrigatorios.get(i)) {
                    case "CASO_TESTE":
                        if (textNomeCT.getText().equals("")) {
                            textNomeCT.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "FUNCIONALIDADE_SERVICO":
                        if (comboFuncionalidade.getSelectedItem().equals("")) {
                            comboFuncionalidade.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "SISTEMA_MASTER":
                        if (comboSistemaMaster.getSelectedItem().equals("")) {
                            comboSistemaMaster.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }

                        break;

                    case "SISTEMAS_ENVOLVIDOS":
                        if (textSistemaEnvolvidos.getText().equals("")) {
                            textSistemaEnvolvidos.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "FORNECEDOR":
                        if (textFornecedor.getText().equals("")) {
                            textFornecedor.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "CENARIO_TESTE":
                        if (textCenario.getText().equals("")) {
                            textCenario.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "TP_REQUISITO":
                        if (comboTipoRequisito.getSelectedItem().equals("")) {
                            comboTipoRequisito.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "REQUISITO":
                        if (textRequisito.getText().equals("")) {
                            textRequisito.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "CRIACAO_ALTERACAO":
                        if (comboCriacao.getSelectedItem().equals("")) {
                            comboCriacao.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "CADEIA":
                        if (comboCadeia.getSelectedItem().equals("")) {
                            comboCadeia.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "SEGMENTO":
                        if (textSegmento.getText().equals("")) {
                            textSegmento.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "CENARIO_INTEGRADO":
                        if (comboCenarioIntegrado.getSelectedItem().equals("")) {
                            comboCenarioIntegrado.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "CENARIO_AUTOMATIZAVEL":
                        if (comboCenarioAutomatizavel.getSelectedItem().equals("")) {
                            comboCenarioAutomatizavel.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "TP_TYPE":
                        if (comboType.getSelectedItem().equals("")) {
                            comboType.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "TRG":
                        if (comboTrg.getSelectedItem().equals("")) {
                            comboTrg.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "DESCRICAO_CASO_TESTE":
                        if (TextAreaDescricao.getText().equals("")) {
                            TextAreaDescricao.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "QTD_SISTEMAS":
                        if (textQtdSistemas.getText().equals("")) {
                            textQtdSistemas.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "PRODUTO":
                        if (textProduto.getText().equals("")) {
                            textProduto.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                    case "SUBJECT":
                        if (textSubject.getText().equals("")) {
                            textSubject.setBorder(new LineBorder(Color.red));
                            obrigatorio = true;
                        }
                        break;

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        if (obrigatorio) {
            JOptionPane.showMessageDialog(null, "O(s) campo(s) em destaque tem o preenchimento obrigatório", "Campos Obrigatórios", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }

    }

    private void addAsteristicoCamposObrigatorios() {
        List<String> listCamposObrigatorios = new ArrayList<String>();
        boolean obrigatorio = false;
        try {
            Validacao validacao = new Validacao();
            listCamposObrigatorios = validacao.campoObrigatorios();

            for (int i = 0; i < listCamposObrigatorios.size(); i++) {

                switch (listCamposObrigatorios.get(i)) {
                    case "CASO_TESTE":
                        labelNomeCt.setVisible(true);
                        break;

                    case "FUNCIONALIDADE_SERVICO":
                        labelFuncionalidade.setVisible(true);
                        break;

                    case "SISTEMA_MASTER":
                        labelSistemaMaster.setVisible(true);

                        break;

                    case "SISTEMAS_ENVOLVIDOS":
                        labelSistemaEnvolvidos.setVisible(true);
                        break;

                    case "FORNECEDOR":
                        labelFornecedor.setVisible(true);
                        break;

                    case "CENARIO_TESTE":
                        labelCenario.setVisible(true);
                        break;

                    case "TP_REQUISITO":
                        labelTipoRequisito.setVisible(true);
                        break;

                    case "REQUISITO":
                        labelRequisito.setVisible(true);
                        break;

                    case "CRIACAO_ALTERACAO":
                        labelCriacao.setVisible(true);
                        break;

                    case "CADEIA":
                        labelCadeia.setVisible(true);
                        break;

                    case "SEGMENTO":
                        labelSegmento.setVisible(true);
                        break;

                    case "CENARIO_INTEGRADO":
                        labelCenarioIntegrado.setVisible(true);
                        break;

                    case "CENARIO_AUTOMATIZAVEL":
                        labelCenarioAuto.setVisible(true);
                        break;

                    case "TP_TYPE":
                        labelType.setVisible(true);
                        break;

                    case "TRG":
                        labelTrg.setVisible(true);
                        break;

                    case "DESCRICAO_CASO_TESTE":
                        labelDescricao.setVisible(true);
                        break;

                    case "QTD_SISTEMAS":
                        labelQtdSistemas.setVisible(true);
                        break;

                    case "PRODUTO":
                        labelProduto.setVisible(true);
                        break;

                    case "SUBJECT":
                        labelSubject.setVisible(true);
                        break;

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

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

    private boolean validaQtdStep() {
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        if (model.getRowCount() >= 1) {
            return true;
        } else {

            JOptionPane.showMessageDialog(null, "O CT deve possuir no mínimo 1(um) step ", "Validação", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private boolean verificaSeCTExiste(Plano p) {
        Plano p2 = new Plano();

        p.setCasoTeste(textNomeCT.getText().toString());

        try {
            p2 = banco.verificaSeCtExiste(p);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        if (p2.getCasoTeste() == null) {
            p2.setCasoTeste(" ");
        }

        if (p2.getCasoTeste().equals(p.getCasoTeste())) {
            JOptionPane.showMessageDialog(this, "O CT já existe na base de dados local!", "Validação", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    private void fechaJanelaInterna() throws PropertyVetoException {
        closedSave = true;
        this.setClosed(true);
        this.dispose();
    }

    private void showMessageDialogInf(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessageDialogErr(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    private void criaJanelaTelaStepPadrao() throws IOException, ClassNotFoundException, SQLException {

        guiJanelaStepPadrao = new EditStepDefaultScreenView();
        this.getParent().add(guiJanelaStepPadrao);

        guiJanelaStepPadrao.centralizaJanela();

        guiJanelaStepPadrao.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        guiJanelaStepPadrao.setVisible(true);

    }

    public void calculaComplexidade() {
        System.out.println(textQtdSistemas.getText());
        int qtdSistemas = Integer.parseInt(textQtdSistemas.getText());
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int qtdSteps = model.getRowCount();

        if (qtdSistemas == 0 && qtdSteps == 0) {
            jLabelComplexidade.setText("N/A");
        } else if (qtdSistemas == 1 && qtdSteps >= 1 && qtdSteps <= 4) {
            jLabelComplexidade.setText("Baixa");
            jLabelComplexidade.setIcon(icon.getIconPriorityLow());
        } else if ((qtdSistemas <= 1 && qtdSteps >= 5 && qtdSteps <= 11) || (qtdSistemas > 1 && qtdSteps > 0 && qtdSteps <= 11)) {
            jLabelComplexidade.setText("Média");
            jLabelComplexidade.setIcon(icon.getIconPriorityMedium());
        } else if (qtdSistemas >= 1 && qtdSteps >= 12 && qtdSteps <= 25) {
            jLabelComplexidade.setText("Alta");
            jLabelComplexidade.setIcon(icon.getIconPriorityHight());
        } else {
            jLabelComplexidade.setText("NOK");
        }
    }
    //inicio - defeito 3
    EditTestCaseScreenTIView guiMantemCt;

    private void criaJanelaTelaManterCT() throws IOException, ClassNotFoundException, SQLException {

        guiMantemCt = new EditTestCaseScreenTIView();
        this.getParent().add(guiMantemCt);

        guiMantemCt.centralizaJanela();

        guiMantemCt.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        carregaCamposTelaCTCad(guiJanelaCadCT);
        guiMantemCt.setVisible(true);
        this.dispose();

    }

    public void atualizaTabelaPlanosGUIMatem(List<Plano> listPlano) throws SQLException, ClassNotFoundException, IOException {
        criaJanelaTelaManterCT();
        guiMantemCt.atualizaTabelaPlano(listPlano);
        this.dispose();
    }
    // fim defeito 3
   
    /*
    ** Método para adicionar os ícones aos botões da tela.
    */
    public void addIconInButton(){
        
        bntAddStepPadrao.setIcon(icon.getIconBntAddStepDefault());
        bntAddStep.setIcon(icon.getIconBntAddNewStep());
        bntDeleteStep.setIcon(icon.getIconBntRemoveStep());
        bntMudaStepSubir.setIcon(icon.getIconBntMoveStepTop());
        bntMudaStepDescer.setIcon(icon.getIconBntMoveStepBottom());
        bntCancelar.setIcon(icon.getIconBntCacelAction());
        bntSalvarPlano1.setIcon(icon.getIconBntConfirmAction());
    }
    
    public void renderTableStep(){
        
        TableColumn col;
           for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
            col = tabelaSteps.getColumnModel().getColumn(i);
            col.setCellRenderer(new TextAreaCellRenderer());
        }
        
    }

}
