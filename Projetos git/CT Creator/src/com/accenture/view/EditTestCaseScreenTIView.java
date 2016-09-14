/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.control.ExcelDAO;
import com.accenture.control.ManipulaDadosSQLite;
import com.accenture.control.Validacao;
import com.accenture.bean.Plano;
import com.accenture.bean.Step;
import com.accenture.util.TextAreaCellEditor;
import com.accenture.util.TextAreaCellRenderer;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.util.Collections;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author raphael.da.silva
 */
public class EditTestCaseScreenTIView extends javax.swing.JInternalFrame {

    ExcelDAO conf = new ExcelDAO();
    private ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
    private int numeroStep = 0;
    private Plano plano = new Plano();
    private Step step = new Step();
    private List<Plano> listPlano = new ArrayList<Plano>();
    private final long tempo = (1000 * 60);
    private MainScreenView gUIPrincipal = new MainScreenView();
    private Border bordaCampo, bordaCombo, bordaTextArea;
    private boolean alteracao;
    private ButtonIconBean icon;

    /**
     * Creates new form GUIMantemCT
     */
    public EditTestCaseScreenTIView() throws SQLException, ClassNotFoundException {

        initComponents();
        icon = new ButtonIconBean();
        this.addIconInButton();
        //Captura borda default dos campos
        bordaCampo = textNomeCT.getBorder();
        bordaCombo = comboCadeia.getBorder();
        bordaTextArea = TextAreaDescricao.getBorder();
        addAsteristicoCamposObrigatorios();
        tabelaPlanos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        alteracao = false;
//        final GUIPrincipal gUIPrincipal = new GUIPrincipal();

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
                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();

//        chamaTelaFiltro();
        ativaCampos(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaPlanos = new javax.swing.JTable();
        bntFiltrar = new javax.swing.JButton();
        bntExcluirPlano = new javax.swing.JButton();
        bntSalvarPlano = new javax.swing.JButton();
        textNomeCT = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaSteps = new javax.swing.JTable();
        textCenario = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        comboFuncionalidade = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        comboSistemaMaster = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        textSistemaEnvolvidos = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        textFornecedor = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        comboTipoRequisito = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        textRequisito = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        comboCriacao = new javax.swing.JComboBox();
        jLabel46 = new javax.swing.JLabel();
        textSubject = new javax.swing.JTextField();
        comboCadeia = new javax.swing.JComboBox();
        textSegmento = new javax.swing.JTextField();
        comboCenarioIntegrado = new javax.swing.JComboBox();
        comboCenarioAutomatizavel = new javax.swing.JComboBox();
        comboType = new javax.swing.JComboBox();
        comboTrg = new javax.swing.JComboBox();
        textQtdSistemas = new javax.swing.JTextField();
        textProduto = new javax.swing.JTextField();
        jLabelComplexidade = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        bntAddStepPadrao = new javax.swing.JButton();
        bntAddStep = new javax.swing.JButton();
        bntDeleteStep = new javax.swing.JButton();
        bntMudaStepSubir = new javax.swing.JButton();
        bntMudaStepDescer = new javax.swing.JButton();
        bntSalvarPlano1 = new javax.swing.JButton();
        labelNomeCt = new javax.swing.JLabel();
        labelCenario = new javax.swing.JLabel();
        labelFuncionalidade = new javax.swing.JLabel();
        labelSistemaMaster = new javax.swing.JLabel();
        labelSistemaEnvolvidos = new javax.swing.JLabel();
        labelFornecedor = new javax.swing.JLabel();
        labelTipoRequisito = new javax.swing.JLabel();
        labelRequisito = new javax.swing.JLabel();
        labelCriacao = new javax.swing.JLabel();
        labelSubject = new javax.swing.JLabel();
        labelProduto = new javax.swing.JLabel();
        labelQtdSistemas = new javax.swing.JLabel();
        labelTrg = new javax.swing.JLabel();
        labelType = new javax.swing.JLabel();
        labelCenarioAuto = new javax.swing.JLabel();
        labelCenarioIntegrado = new javax.swing.JLabel();
        labelSegmento = new javax.swing.JLabel();
        labelCadeia = new javax.swing.JLabel();
        labelDescricao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAreaDescricao = new javax.swing.JTextArea();

        setClosable(true);
        setForeground(java.awt.Color.white);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Manter CT");
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

        tabelaPlanos.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                int row = tabelaPlanos.rowAtPoint(p);
                int column = tabelaPlanos.columnAtPoint(p);
                tabelaPlanos.setToolTipText(String.valueOf(tabelaPlanos.getValueAt(row,column)));
            }//end MouseMoved
        }); // end MouseMotionAdapter
        tabelaPlanos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Caso de Teste"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaPlanos.getTableHeader().setReorderingAllowed(false);
        tabelaPlanos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaPlanosMouseClicked(evt);
            }
        });
        tabelaPlanos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabelaPlanosComponentShown(evt);
            }
        });
        tabelaPlanos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaPlanosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tabelaPlanosKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaPlanos);
        if (tabelaPlanos.getColumnModel().getColumnCount() > 0) {
            tabelaPlanos.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaPlanos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelaPlanos.getColumnModel().getColumn(1).setResizable(false);
        }

        bntFiltrar.setText("Filtro");
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

        bntExcluirPlano.setText("Excluir");
        bntExcluirPlano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntExcluirPlanoMouseClicked(evt);
            }
        });
        bntExcluirPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExcluirPlanoActionPerformed(evt);
            }
        });

        bntSalvarPlano.setText("Salvar");
        bntSalvarPlano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntSalvarPlanoMouseClicked(evt);
            }
        });
        bntSalvarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalvarPlanoActionPerformed(evt);
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
        tabelaSteps.getTableHeader().setReorderingAllowed(false);
        tabelaSteps.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaStepsFocusGained(evt);
            }
        });
        tabelaSteps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaStepsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaStepsMouseEntered(evt);
            }
        });
        tabelaSteps.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tabelaStepsInputMethodTextChanged(evt);
            }
        });
        tabelaSteps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaStepsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaStepsKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tabelaStepsKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaSteps);
        if (tabelaSteps.getColumnModel().getColumnCount() > 0) {
            tabelaSteps.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(0).setMaxWidth(70);
            tabelaSteps.getColumnModel().getColumn(1).setCellEditor(new TextAreaCellEditor());
            tabelaSteps.getColumnModel().getColumn(2).setCellEditor(new TextAreaCellEditor());
            tabelaSteps.getColumnModel().getColumn(3).setMinWidth(0);
            tabelaSteps.getColumnModel().getColumn(3).setMaxWidth(0);
        }
        TableColumn col;
        for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
            col = tabelaSteps.getColumnModel().getColumn(i);
            col.setCellRenderer(new TextAreaCellRenderer());
        }

        jLabel29.setText("CENÁRIO:");

        jLabel30.setText("FUNCIONALIDADE/SERVIÇO:");

        comboFuncionalidade.setAutoscrolls(true);

        jLabel31.setText("SISTEMA MASTER:");

        comboSistemaMaster.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboSistemaMasterFocusLost(evt);
            }
        });

        jLabel32.setText("SISTEMAS ENVOLVIDOS:");

        textSistemaEnvolvidos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textSistemaEnvolvidosFocusLost(evt);
            }
        });

        jLabel33.setText("FORNECEDOR:");

        jLabel35.setText("TIPO REQUISITO:");

        jLabel36.setText("REQUISITO:");

        jLabel47.setText("CRIAÇÃO/ALTERAÇÃO");

        jLabel46.setText("SUBJECT:");

        textSubject.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textSubjectKeyTyped(evt);
            }
        });

        textQtdSistemas.setEnabled(false);
        textQtdSistemas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textQtdSistemasKeyTyped(evt);
            }
        });

        jLabelComplexidade.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelComplexidade.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelComplexidade.setText("N/A");
        jLabelComplexidade.setToolTipText("");

        jLabel41.setText("COMPLEXIDADE:");

        jLabel39.setText("PRODUTO:");

        jLabel42.setText("QTD SISTEMAS:");

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

        jLabel43.setText("CENÁRIO AUTOMATIZÁVEL?");

        jLabel40.setText("CENÁRIO INTEGRADO:");

        jLabel38.setText("SEGMENTO:");

        jLabel37.setText("CADEIA:");

        bntAddStepPadrao.setText("Add Step Padrão");
        bntAddStepPadrao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAddStepPadraoMouseClicked(evt);
            }
        });

        bntAddStep.setText("Add Step");
        bntAddStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAddStepMouseClicked(evt);
            }
        });

        bntDeleteStep.setText("Remover Step");
        bntDeleteStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntDeleteStepMouseClicked(evt);
            }
        });

        bntMudaStepSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntMudaStepSubirMouseClicked(evt);
            }
        });
        bntMudaStepSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMudaStepSubirActionPerformed(evt);
            }
        });

        bntMudaStepDescer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntMudaStepDescerMouseClicked(evt);
            }
        });
        bntMudaStepDescer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMudaStepDescerActionPerformed(evt);
            }
        });

        bntSalvarPlano1.setText("jButton1");

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

        labelRequisito.setForeground(new java.awt.Color(255, 0, 0));
        labelRequisito.setText("*");

        labelCriacao.setForeground(new java.awt.Color(255, 0, 0));
        labelCriacao.setText("*");

        labelSubject.setForeground(new java.awt.Color(255, 0, 0));
        labelSubject.setText("*");

        labelProduto.setForeground(new java.awt.Color(255, 0, 0));
        labelProduto.setText("*");

        labelQtdSistemas.setForeground(new java.awt.Color(255, 0, 0));
        labelQtdSistemas.setText("*");

        labelTrg.setForeground(new java.awt.Color(255, 0, 0));
        labelTrg.setText("*");

        labelType.setForeground(new java.awt.Color(255, 0, 0));
        labelType.setText("*");

        labelCenarioAuto.setForeground(new java.awt.Color(255, 0, 0));
        labelCenarioAuto.setText("*");

        labelCenarioIntegrado.setForeground(new java.awt.Color(255, 0, 0));
        labelCenarioIntegrado.setText("*");

        labelSegmento.setForeground(new java.awt.Color(255, 0, 0));
        labelSegmento.setText("*");

        labelCadeia.setForeground(new java.awt.Color(255, 0, 0));
        labelCadeia.setText("*");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntFiltrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntExcluirPlano)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntSalvarPlano)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCenario, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFuncionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSistemaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSistemaEnvolvidos, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel35)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel48)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(labelNomeCt, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTipoRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textNomeCT, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bntAddStepPadrao)
                                        .addGap(6, 6, 6)
                                        .addComponent(bntAddStep)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bntDeleteStep)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bntMudaStepSubir)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bntMudaStepDescer)
                                        .addGap(261, 261, 261))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel46)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel41)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel39)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel42)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelQtdSistemas, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel45)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelTrg, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel44)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelType, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel43)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelCenarioAuto, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel40)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelCenarioIntegrado, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel38)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel37)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelCadeia, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(textQtdSistemas, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(comboType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(comboCenarioAutomatizavel, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(comboCenarioIntegrado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textSegmento, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(comboTrg, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabelComplexidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(comboCadeia, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(textSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel49)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jScrollPane1)
                                                .addContainerGap())))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(500, 500, 500)
                                .addComponent(bntSalvarPlano1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textCenario, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboFuncionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboSistemaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textSistemaEnvolvidos, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTipoRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(textNomeCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNomeCt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(labelCenario)))
                            .addComponent(textCenario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel30))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboFuncionalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelFuncionalidade)))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(labelSistemaMaster)))
                            .addComponent(comboSistemaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32)
                                    .addComponent(labelSistemaEnvolvidos)))
                            .addComponent(textSistemaEnvolvidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(labelFornecedor)))
                            .addComponent(textFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(comboTipoRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTipoRequisito))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textRequisito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelRequisito))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCriacao)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel49)
                                .addComponent(labelDescricao))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bntFiltrar)
                                .addComponent(bntExcluirPlano)
                                .addComponent(bntSalvarPlano)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelCadeia))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelSegmento))
                                        .addGap(8, 8, 8)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel40)
                                            .addComponent(comboCenarioIntegrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelCenarioIntegrado))
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel43)
                                            .addComponent(labelCenarioAuto))
                                        .addGap(14, 14, 14)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel44)
                                            .addComponent(labelType))
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelTrg)
                                            .addComponent(jLabel45))
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel42)
                                            .addComponent(labelQtdSistemas, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelProduto)))
                                    .addGroup(layout.createSequentialGroup()
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelComplexidade))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelSubject)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bntAddStep)
                                .addComponent(bntDeleteStep)
                                .addComponent(bntMudaStepSubir)
                                .addComponent(bntMudaStepDescer)
                                .addComponent(bntAddStepPadrao))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(bntSalvarPlano1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(19, 19, 19))
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
            comboTipoRequisito.addItem("");
            comboCriacao.addItem("");
            comboCadeia.addItem("");
            comboCenarioIntegrado.addItem("");
            comboCenarioAutomatizavel.addItem("");
            comboType.addItem("");
            comboTrg.addItem("");
            textQtdSistemas.setText("0");
            labelNomeCt.setVisible(false);
            labelCenario.setVisible(false);
            labelFuncionalidade.setVisible(false);
            labelSistemaMaster.setVisible(false);
            labelSistemaEnvolvidos.setVisible(false);
            labelFornecedor.setVisible(false);
            labelTipoRequisito.setVisible(false);
            labelRequisito.setVisible(false);
            labelCriacao.setVisible(false);
            labelSubject.setVisible(false);
            labelProduto.setVisible(false);
            labelQtdSistemas.setVisible(false);
            labelTrg.setVisible(false);
            labelType.setVisible(false);
            labelCenarioAuto.setVisible(false);
            labelCenarioIntegrado.setVisible(false);
            labelSegmento.setVisible(false);
            labelCadeia.setVisible(false);
            labelDescricao.setVisible(false);

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void tabelaPlanosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaPlanosMouseClicked
        if(tabelaSteps.getRowCount()>0){
        // tira a seleção dos steps
        tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
        }
        carregaCampos();
        ativaCampos(true);
        calculaComplexidade();

    }//GEN-LAST:event_tabelaPlanosMouseClicked

    private void tabelaPlanosComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaPlanosComponentShown

    }//GEN-LAST:event_tabelaPlanosComponentShown

    private void tabelaPlanosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaPlanosKeyTyped

    }//GEN-LAST:event_tabelaPlanosKeyTyped

    private void bntFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntFiltrarActionPerformed

    }//GEN-LAST:event_bntFiltrarActionPerformed

    private void bntExcluirPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExcluirPlanoActionPerformed

    }//GEN-LAST:event_bntExcluirPlanoActionPerformed

    private void bntSalvarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalvarPlanoActionPerformed

    }//GEN-LAST:event_bntSalvarPlanoActionPerformed

    private void textNomeCTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textNomeCTFocusLost
        tabelaPlanos.getSelectedRow();

    }//GEN-LAST:event_textNomeCTFocusLost

    private void tabelaStepsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaStepsMouseClicked
        // TODO add your handling code here:

        //        TableColumn col;
        //        for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
        //            col = tabelaSteps.getColumnModel().getColumn(i);
        //            col.setCellEditor(new TextAreaCellEditor());
        //        }
    }//GEN-LAST:event_tabelaStepsMouseClicked

    private void tabelaStepsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaStepsMouseEntered
        // TODO add your handling code here:
        //                TableColumn col;
        //        for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
        //            col = tabelaSteps.getColumnModel().getColumn(i);
        //            col.setCellEditor(new TextAreaCellEditor());
        //        }
    }//GEN-LAST:event_tabelaStepsMouseEntered

    private void tabelaStepsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaStepsFocusGained
        // TODO add your handling code here:
        //        TableColumn col;
        //        for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
        //            col = tabelaSteps.getColumnModel().getColumn(i);
        //            col.setCellEditor(new TextAreaCellEditor());
        //        }
    }//GEN-LAST:event_tabelaStepsFocusGained

    private void tabelaStepsInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tabelaStepsInputMethodTextChanged
        // TODO add your handling code here:
        //        TableColumn col;
        //           for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
        //            col = tabelaSteps.getColumnModel().getColumn(i);
        //            col.setCellRenderer(new TextAreaCellRenderer());
        //        }
    }//GEN-LAST:event_tabelaStepsInputMethodTextChanged

    private void tabelaStepsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaStepsKeyPressed
        // TODO add your handling code here:
        //        TableColumn col;
        //           for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
        //            col = tabelaSteps.getColumnModel().getColumn(i);
        //            col.setCellRenderer(new TextAreaCellRenderer());
        //        }
    }//GEN-LAST:event_tabelaStepsKeyPressed

    private void tabelaStepsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaStepsKeyReleased
        // TODO add your handling code here:
        //        TableColumn col;
        //           for (int i = 0; i < tabelaSteps.getColumnCount(); i++) {
        //            col = tabelaSteps.getColumnModel().getColumn(i);
        //            col.setCellRenderer(new TextAreaCellRenderer());
        //        }
    }//GEN-LAST:event_tabelaStepsKeyReleased

    private void tabelaStepsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaStepsKeyTyped
        // TODO add your handling code here:
        //        bntSalvarPlano.setEnabled(true);
        //        tabelaSteps.setBackground(Color.YELLOW);
    }//GEN-LAST:event_tabelaStepsKeyTyped

    private void textSistemaEnvolvidosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textSistemaEnvolvidosFocusLost
        if (textSistemaEnvolvidos.getText().equals("")) {

            textQtdSistemas.setText("0");
        } else {
            String[] sistemas = textSistemaEnvolvidos.getText().split(";");

            textQtdSistemas.setText(sistemas.length + "");

        }
        calculaComplexidade();

    }//GEN-LAST:event_textSistemaEnvolvidosFocusLost

    private void textSubjectKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSubjectKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textSubjectKeyTyped

    private void textQtdSistemasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textQtdSistemasKeyTyped
        String caracteres = "0987654321";
        if (caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_textQtdSistemasKeyTyped

    private void jLabel45AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel45AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45AncestorAdded

    private void jLabel44AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel44AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel44AncestorAdded

    private void bntAddStepPadraoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAddStepPadraoMouseClicked

        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {

            ChooseStepScreenView dialogStep = null;
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
                    JScrollBar bar = jScrollPane3.getVerticalScrollBar();
                    bar.setValue(bar.getMaximum());
                }
            });
        }
    }//GEN-LAST:event_bntAddStepPadraoMouseClicked

    private void bntAddStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAddStepMouseClicked

        if (tabelaSteps.getRowCount() >= 25) {
            JOptionPane.showMessageDialog(this, "O CT não pode ter mais de 25 Steps.", "Qtd Máxima de Steps", JOptionPane.INFORMATION_MESSAGE);
        } else {

            int i = tabelaSteps.getColumn("Nome Step").getModelIndex();
            Step s = new Step();
            DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

            if (model.getRowCount() == 0) {
                model.addRow(new String[]{"Step " + 1, "<<<parametro>>>", "<<<paramentro>>>", "0"});
//            s.setNomeStep("Step " + 1);
//            s.setDescStep("<<<parametro>>>");
                s.setId(0);
                s.setNomeStep(tabelaSteps.getValueAt(0, 0).toString());
                s.setDescStep(tabelaSteps.getValueAt(0, 1).toString());
                s.setResultadoStep(tabelaSteps.getValueAt(0, 2).toString());

                listPlano.get(tabelaPlanos.getSelectedRow()).getListStep().add(s);

            } else {
                //String numeroStep = model.getValueAt(0, i);
                int numLinhas = model.getRowCount() - 1;
                for (int j = 0; j <= numLinhas; j++) {
                    numeroStep = j + 1;
                }
                numeroStep = numeroStep + 1;

                model.addRow(new String[]{"Step " + numeroStep, "<<<parametro>>>", "<<<paramentro>>>", "0"});

                s.setId(0);
                s.setNomeStep(tabelaSteps.getValueAt(numeroStep - 1, 0).toString());
                s.setDescStep(tabelaSteps.getValueAt(numeroStep - 1, 1).toString());
                s.setResultadoStep(tabelaSteps.getValueAt(numeroStep - 1, 2).toString());

                listPlano.get(tabelaPlanos.getSelectedRow()).getListStep().add(s);

            }
            //        bntSalvarPlano.setEnabled(true);
            try {

                int numLinhas = model.getRowCount();
                for (int j = 0; j < numLinhas; j++) {
                    numeroStep = j + 1;
                    System.out.println("Antes: " + model.getValueAt(j, 0));
                    model.setValueAt("Step " + numeroStep, j, 0);
                    listPlano.get(tabelaPlanos.getSelectedRow()).getListStep().get(j).setNomeStep(tabelaSteps.getValueAt(j, 0).toString());
                    System.out.println("Depois: " + model.getValueAt(j, 0));

                    //                   numeroStep = Integer.parseInt(numStepTemp.substring(5,6));
                }

                calculaComplexidade();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JScrollBar bar = jScrollPane3.getVerticalScrollBar();
                        bar.setValue(bar.getMaximum());
                    }
                });

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + e.getMessage() + e.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

            }
        }

    }//GEN-LAST:event_bntAddStepMouseClicked

    private void bntDeleteStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntDeleteStepMouseClicked
        if (tabelaSteps.getSelectedRow() != -1) {
            Plano plano = new Plano();
            Step step = new Step();
            DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

            Object obj = (tabelaPlanos.getValueAt(tabelaPlanos.getSelectedRow(), 1).toString());
//            step.setNomeStep((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 0)).toString());
//            step.setDescStep((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 1)).toString());
//            step.setResultadoStep((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 2)).toString());
//            step.setId(Integer.parseInt((tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 3)).toString()));
//            String nomeStepSel);ecionado = obj.toString();

            if (JOptionPane.showConfirmDialog(null, "Deseja excluir o " + tabelaSteps.getValueAt(tabelaSteps.getSelectedRow(), 0) + "?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int indeceRemove = tabelaSteps.getSelectedRow();
                tabelaSteps.removeEditor();
                for (int i = 0; i < listPlano.size(); i++) {
                    if (listPlano.get(i).getCasoTeste().equals(obj)) {
                        listPlano.get(i).getListStep().remove(indeceRemove);
                        ((DefaultTableModel) tabelaSteps.getModel()).removeRow(indeceRemove);
                    }
                }

                if (model.getRowCount() == 0) {

                } else {

                    int numLinhas = model.getRowCount();
                    for (int j = 0; j < numLinhas; j++) {
                        numeroStep = j + 1;
                        System.out.println("Antes: " + model.getValueAt(j, 0));
                        model.setValueAt("Step " + numeroStep, j, 0);
                        System.out.println("Depois: " + model.getValueAt(j, 0));

                        for (int i = 0; i < listPlano.size(); i++) {
                            if (listPlano.get(i).getCasoTeste().equals(obj)) {
//                                for(int c = 0; c < listPlano.get(i).getListStep().size(); c++){
                                listPlano.get(i).getListStep().get(j).setNomeStep("Step " + numeroStep);
//                                }
                            }
                        }

                    }

//                    salvaDadosStep();
                }

            } else {

            }

            calculaComplexidade();

        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar Step que deseja excluir.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_bntDeleteStepMouseClicked

    private void bntMudaStepSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepSubirActionPerformed


    }//GEN-LAST:event_bntMudaStepSubirActionPerformed

    private void bntMudaStepDescerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepDescerActionPerformed


    }//GEN-LAST:event_bntMudaStepDescerActionPerformed

    private void bntFiltrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntFiltrarMouseClicked
        FilterTestCaseScreen1View dialogFiltro = null;
        try {
            dialogFiltro = new FilterTestCaseScreen1View(this, null, true);
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenTIView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterScreenTIView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterScreenTIView.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialogFiltro.centralizaJanelaDialogo(this);
        dialogFiltro.setVisible(true);
    }//GEN-LAST:event_bntFiltrarMouseClicked

    private void bntExcluirPlanoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntExcluirPlanoMouseClicked
        tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        if (tabelaSteps.getSelectedRow() == -1) {
            Plano p = new Plano();
            String id = (tabelaPlanos.getValueAt(tabelaPlanos.getSelectedRow(), 0)).toString();
            p.setId(Integer.parseInt(id));
            Object obj = (tabelaPlanos.getValueAt(tabelaPlanos.getSelectedRow(), 1));
            String nomePlanoSelecionado = obj.toString();
            int indeceRemove = tabelaPlanos.getSelectedRow();

            if (JOptionPane.showConfirmDialog(null, "Deseja excluir o CT: " + nomePlanoSelecionado + "?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                try {
                    banco.deletePlano(p);
                    ((DefaultTableModel) tabelaPlanos.getModel()).removeRow(tabelaPlanos.getSelectedRow());
                    tabelaSteps.removeEditor();
                    for (int i = 0; i < listPlano.size(); i++) {
                        if (listPlano.get(i).getCasoTeste().equals(obj)) {
                            listPlano.remove(indeceRemove);
                            limpaCampos();
                            ativaCampos(false);
                            while (model.getRowCount() > 0) {
                                model.removeRow(0);
                            }

                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu o erro SQL: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu o erro grave: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar o CT que deseja excluir.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_bntExcluirPlanoMouseClicked

    private void bntMudaStepSubirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntMudaStepSubirMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
        int numLinhas = model.getRowCount();

        int indeciLinhaSelecionada = tabelaSteps.getSelectedRow();

        tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));;

        if (numLinhas > 0) {

            model.moveRow(indeciLinhaSelecionada, indeciLinhaSelecionada, indeciLinhaSelecionada - 1);

            Collections.swap(listPlano.get(tabelaPlanos.getSelectedRow()).getListStep(), indeciLinhaSelecionada, indeciLinhaSelecionada - 1);

            for (int j = 0; j < numLinhas; j++) {
                numeroStep = j + 1;
                System.out.println("Antes: " + model.getValueAt(j, 0));
                model.setValueAt("Step " + numeroStep, j, 0);
                listPlano.get(tabelaPlanos.getSelectedRow()).getListStep().get(j).setNomeStep("Step " + numeroStep);
                System.out.println("Depois: " + model.getValueAt(j, 0));
                //                   numeroStep = Integer.parseInt(numStepTemp.substring(5,6));

            }

        }

//        carregaPlanoClicado();
    }//GEN-LAST:event_bntMudaStepSubirMouseClicked

    private void bntMudaStepDescerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntMudaStepDescerMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        int numLinhas = model.getRowCount();

        int indeciLinhaSelecionada = tabelaSteps.getSelectedRow();

        tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));

        if (numLinhas > 0) {

            model.moveRow(indeciLinhaSelecionada, indeciLinhaSelecionada, indeciLinhaSelecionada + 1);
            Collections.swap(listPlano.get(tabelaPlanos.getSelectedRow()).getListStep(), indeciLinhaSelecionada, indeciLinhaSelecionada + 1);

            for (int j = 0; j < numLinhas; j++) {
                numeroStep = j + 1;
                System.out.println("Antes: " + model.getValueAt(j, 0));
                model.setValueAt("Step " + numeroStep, j, 0);
                System.out.println("Depois: " + model.getValueAt(j, 0));
                //                   numeroStep = Integer.parseInt(numStepTemp.substring(5,6));

            }

        }
    }//GEN-LAST:event_bntMudaStepDescerMouseClicked

    private void bntSalvarPlanoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntSalvarPlanoMouseClicked
        setBordaPadraoCampos();
        if (atualizaDados() && validaObrigatoriedadeCampos()) {
            JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso!", "Alteração", JOptionPane.INFORMATION_MESSAGE);

        };
    }//GEN-LAST:event_bntSalvarPlanoMouseClicked

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:


    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here: gUIPrincipal.removeTela(this);
//         gUIPrincipal.removeTela(this);
//        System.out.println("Clicou no botao fechar");
        if (JOptionPane.showConfirmDialog(this, "Ao sair da tela seus dados serão perdidos, deseja realmente sair?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

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

    private void tabelaPlanosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaPlanosKeyReleased
        // TODO add your handling code here:
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {

            // tira a seleção dos steps
            tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
            carregaCampos();
            ativaCampos(true);
            calculaComplexidade();

        }
    }//GEN-LAST:event_tabelaPlanosKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TextAreaDescricao;
    private javax.swing.JButton bntAddStep;
    private javax.swing.JButton bntAddStepPadrao;
    private javax.swing.JButton bntDeleteStep;
    private javax.swing.JButton bntExcluirPlano;
    private javax.swing.JButton bntFiltrar;
    private javax.swing.JButton bntMudaStepDescer;
    private javax.swing.JButton bntMudaStepSubir;
    private javax.swing.JButton bntSalvarPlano;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
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
    private javax.swing.JTable tabelaPlanos;
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
    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void chamaTelaFiltro() {
        FilterTestCaseScreen1View dialogFiltro = null;
        try {
            dialogFiltro = new FilterTestCaseScreen1View(this, null, true);
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenTIView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterScreenTIView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterScreenTIView.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialogFiltro.centralizaJanelaDialogo(this);
        dialogFiltro.setVisible(true);
    }

    public void atualizaTabelaPlano(List<Plano> listPlano) {
        DefaultTableModel model = (DefaultTableModel) tabelaPlanos.getModel();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        this.listPlano = listPlano;
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        if (this.listPlano.size() == 0) {
            ativaCampos(false);
            limpaCampos();
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }
        }
        for (int i = 0; i < listPlano.size(); i++) {
            model.addRow(new String[]{String.valueOf(listPlano.get(i).getId()), listPlano.get(i).getCasoTeste()});
            System.out.println(listPlano.get(i).getCasoTeste());
        }

    }

    public void carregaCampos() {
        Object obj = (tabelaPlanos.getValueAt(tabelaPlanos.getSelectedRow(), 0));
        int id = Integer.parseInt(obj.toString());
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        for (int i = 0; i < listPlano.size(); i++) {
            if (id == listPlano.get(i).getId()) {
                textNomeCT.setText(listPlano.get(i).getCasoTeste());
                comboCriacao.setSelectedItem(listPlano.get(i).getCriacaoAlteracao());
                textSubject.setText(listPlano.get(i).getSubject());
                comboTrg.setSelectedItem(listPlano.get(i).getTrg());
                comboType.setSelectedItem(listPlano.get(i).getType());
                comboCenarioAutomatizavel.setSelectedItem(listPlano.get(i).getCenarioAutomatizavel());
                textQtdSistemas.setText(listPlano.get(i).getQtdSistemas() + "");
                TextAreaDescricao.setText(listPlano.get(i).getDescCasoTeste());
                textCenario.setText(listPlano.get(i).getCenarioTeste());
                textRequisito.setText(listPlano.get(i).getRequisito());
                comboTipoRequisito.setSelectedItem(listPlano.get(i).getTpRequisito());
                textFornecedor.setText(listPlano.get(i).getFornecedor());
                textSistemaEnvolvidos.setText(listPlano.get(i).getSistemasEnvolvidos());
                comboSistemaMaster.setSelectedItem(listPlano.get(i).getSistemaMaster());
                comboCenarioIntegrado.setSelectedItem(listPlano.get(i).getCenarioIntegrado());
                comboFuncionalidade.setSelectedItem(listPlano.get(i).getFuncionalidade());
                textProduto.setText(listPlano.get(i).getProduto());
                textSegmento.setText(listPlano.get(i).getSegmento());
                comboCadeia.setSelectedItem(listPlano.get(i).getCadeia());

                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }

                for (int j = 0; j < listPlano.get(i).getListStep().size(); j++) {
                    model.addRow(new String[]{String.valueOf(listPlano.get(i).getListStep().get(j).getNomeStep()), listPlano.get(i).getListStep().get(j).getDescStep(),
                        listPlano.get(i).getListStep().get(j).getResultadoStep(), String.valueOf(listPlano.get(i).getListStep().get(j).getId())});
                }
            }
        }
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

    public void setTabelaSteps(List<Step> listStep) {
        Step s = new Step();
        //model da tabela step
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        for (int i = 0; i < listStep.size(); i++) {
            //add nova linha na tabela
            model.addRow(new String[]{"Step " + (i + 1), listStep.get(i).getDescStep(), listStep.get(i).getResultadoStep(), "0"});

            s.setId(0);
            s.setNomeStep(tabelaSteps.getValueAt(0, 0).toString());
            s.setDescStep(tabelaSteps.getValueAt(0, 1).toString());
            s.setResultadoStep(tabelaSteps.getValueAt(0, 2).toString());

            listPlano.get(tabelaPlanos.getSelectedRow()).getListStep().add(s);

        }

        //ordena a numeração dos steps
        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            System.out.println("Antes: " + model.getValueAt(j, 0));
            model.setValueAt("Step " + numeroStep, j, 0);
            listPlano.get(tabelaPlanos.getSelectedRow()).getListStep().get(j).setNomeStep(tabelaSteps.getValueAt(j, 0).toString());
            System.out.println("Depois: " + model.getValueAt(j, 0));
        }

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

    public boolean atualizaDados() {
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();

        Object obj = (tabelaPlanos.getValueAt(tabelaPlanos.getSelectedRow(), 1).toString());

        for (int i = 0; i < listPlano.size(); i++) {
            if (listPlano.get(i).getCasoTeste().equals(obj)) {

                listPlano.get(i).setDescCasoTeste(TextAreaDescricao.getText());
                listPlano.get(i).setCenarioTeste(textCenario.getText());
                listPlano.get(i).setFornecedor(textFornecedor.getText());
                listPlano.get(i).setCasoTeste(textNomeCT.getText());
                listPlano.get(i).setProduto(textProduto.getText());
                listPlano.get(i).setQtdSistemas(Integer.parseInt(textQtdSistemas.getText()));
                listPlano.get(i).setRequisito(textRequisito.getText());
                listPlano.get(i).setSegmento(textSegmento.getText());
                listPlano.get(i).setSistemasEnvolvidos(textSistemaEnvolvidos.getText());
                listPlano.get(i).setSubject(textSubject.getText());
                listPlano.get(i).setCadeia(comboCadeia.getSelectedItem().toString());
                listPlano.get(i).setCenarioAutomatizavel(comboCenarioAutomatizavel.getSelectedItem().toString());
                listPlano.get(i).setCenarioIntegrado(comboCenarioIntegrado.getSelectedItem().toString());
                listPlano.get(i).setCriacaoAlteracao(comboCriacao.getSelectedItem().toString());
                listPlano.get(i).setFuncionalidade(comboFuncionalidade.getSelectedItem().toString());
                listPlano.get(i).setSistemaMaster(comboSistemaMaster.getSelectedItem().toString());
                listPlano.get(i).setTpRequisito(comboTipoRequisito.getSelectedItem().toString());
                listPlano.get(i).setTrg(comboTrg.getSelectedItem().toString());
                listPlano.get(i).setType(comboType.getSelectedItem().toString());

                try {
                    // salva ct no banco de dados

                    banco.updatePlano(listPlano.get(i));
                    //exclui os steps

//                   banco.deleteStep(listPlano.get(i).getListStep().get(i).getIdPlano());
                    // tira a seleção dos steps
                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
                    //inseres novos steps no banco de dados
                    for (int j = 0; j < model.getRowCount(); j++) {
                        Step s = new Step();
                        Plano p = listPlano.get(i);
                        if (Integer.parseInt(tabelaSteps.getValueAt(j, 3).toString()) == 0) {
                            s.setNomeStep(tabelaSteps.getValueAt(j, 0).toString());
                            s.setDescStep(tabelaSteps.getValueAt(j, 1).toString());
                            s.setResultadoStep(tabelaSteps.getValueAt(j, 2).toString());
                            s.setIdPlano(listPlano.get(tabelaPlanos.getSelectedRow()).getId());
                            p.setStep(s);

//                            listPlano.get(i).getListStep().add(s);
                            banco.insertStep(p);
                            int id = banco.getStepMaiorId(p);
                            listPlano.get(i).getListStep().get(j).setId(id);
                            tabelaSteps.setValueAt(id, j, 3);

                        }
                    }
                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
                    //atualiza os steps no banco
                    for (int j = 0; j < listPlano.get(i).getListStep().size(); j++) {
                        listPlano.get(i).getListStep().get(j).setDescStep(tabelaSteps.getValueAt(j, 1).toString());
                        listPlano.get(i).getListStep().get(j).setNomeStep(tabelaSteps.getValueAt(j, 0).toString());
                        listPlano.get(i).getListStep().get(j).setResultadoStep(tabelaSteps.getValueAt(j, 2).toString());
                        listPlano.get(i).getListStep().get(j).setIdPlano(listPlano.get(tabelaPlanos.getSelectedRow()).getId());
                        System.out.println("Posição " + j + ": " + tabelaSteps.getValueAt(j, 3).toString());
                        banco.updateStep(listPlano.get(i).getListStep().get(j));

                    }

                    //exclui do banco os cts excluidos
                    banco.deleteStepExcluido(listPlano.get(i).getListStep());

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + e.getMessage() + e.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }
        }
        return true;
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

    public void ativaCampos(boolean ativa) {
        if (ativa) {
            TextAreaDescricao.setEnabled(ativa);
            textCenario.setEnabled(ativa);
            textFornecedor.setEnabled(ativa);
            textNomeCT.setEnabled(ativa);
            textProduto.setEnabled(ativa);
//            textQtdSistemas.setEnabled(ativa);
            textRequisito.setEnabled(ativa);
            textSegmento.setEnabled(ativa);
            textSistemaEnvolvidos.setEnabled(ativa);
            textSubject.setEnabled(ativa);
            comboCadeia.setEnabled(ativa);
            comboCenarioAutomatizavel.setEnabled(ativa);
            comboCenarioIntegrado.setEnabled(ativa);
            comboCriacao.setEnabled(ativa);
            comboFuncionalidade.setEnabled(ativa);
            comboSistemaMaster.setEnabled(ativa);
            comboTipoRequisito.setEnabled(ativa);
            comboTrg.setEnabled(ativa);
            comboType.setEnabled(ativa);
            bntAddStep.setEnabled(ativa);
            bntAddStepPadrao.setEnabled(ativa);
            bntDeleteStep.setEnabled(ativa);
            bntExcluirPlano.setEnabled(ativa);
            bntMudaStepDescer.setEnabled(ativa);
            bntMudaStepSubir.setEnabled(ativa);
            bntSalvarPlano.setEnabled(ativa);
        } else {
            TextAreaDescricao.setEnabled(ativa);
            textCenario.setEnabled(ativa);
            textFornecedor.setEnabled(ativa);
            textNomeCT.setEnabled(ativa);
            textProduto.setEnabled(ativa);
            textQtdSistemas.setEnabled(ativa);
            textRequisito.setEnabled(ativa);
            textSegmento.setEnabled(ativa);
            textSistemaEnvolvidos.setEnabled(ativa);
            textSubject.setEnabled(ativa);
            comboCadeia.setEnabled(ativa);
            comboCenarioAutomatizavel.setEnabled(ativa);
            comboCenarioIntegrado.setEnabled(ativa);
            comboCriacao.setEnabled(ativa);
            comboFuncionalidade.setEnabled(ativa);
            comboSistemaMaster.setEnabled(ativa);
            comboTipoRequisito.setEnabled(ativa);
            comboTrg.setEnabled(ativa);
            comboType.setEnabled(ativa);
            bntAddStep.setEnabled(ativa);
            bntAddStepPadrao.setEnabled(ativa);
            bntDeleteStep.setEnabled(ativa);
            bntExcluirPlano.setEnabled(ativa);
            bntMudaStepDescer.setEnabled(ativa);
            bntMudaStepSubir.setEnabled(ativa);
            bntSalvarPlano.setEnabled(ativa);
        }
    }

    public void limpaCampos() {

        TextAreaDescricao.setText("");
        textCenario.setText("");
        textFornecedor.setText("");
        textNomeCT.setText("");
        textProduto.setText("");
        textQtdSistemas.setText("");
        textRequisito.setText("");
        textSegmento.setText("");
        textSistemaEnvolvidos.setText("");
        textSubject.setText("");
        comboCadeia.setSelectedItem("");
        comboCenarioAutomatizavel.setSelectedItem("");
        comboCenarioIntegrado.setSelectedItem("");
        comboCriacao.setSelectedItem("");
        comboFuncionalidade.setSelectedItem("");
        comboSistemaMaster.setSelectedItem("");
        comboTipoRequisito.setSelectedItem("");
        comboTrg.setSelectedItem("");
        comboType.setSelectedItem("");
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

    public void addIconInButton() {

        bntAddStep.setIcon(icon.getIconBntAddNewStep());
        bntAddStepPadrao.setIcon(icon.getIconBntAddStepDefault());
        bntDeleteStep.setIcon(icon.getIconBntRemoveStep());
        bntExcluirPlano.setIcon(icon.getIconBntCacelActionMinimum());
        bntMudaStepDescer.setIcon(icon.getIconBntMoveStepBottom());
        bntMudaStepSubir.setIcon(icon.getIconBntMoveStepTop());
        bntFiltrar.setIcon(icon.getIconBntFilter());
        bntSalvarPlano.setIcon(icon.getIconBntSaveMinimum());
    }

}
