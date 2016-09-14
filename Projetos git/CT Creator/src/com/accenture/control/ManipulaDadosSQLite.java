/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.CasoTesteTemp;
import com.accenture.bean.Plano;
import com.accenture.bean.Step;
import com.accenture.bean.StepPadrao;
import com.accenture.bean.UsuarioALM;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Raphael
 */
public class ManipulaDadosSQLite {

    private Connection conn;
    private Statement stm;
    static String dir = System.getProperty("user.home");
    private boolean retorno = false;
    private static final String fileBanco = "C:\\FastPlan\\bdCreatorCT";

    private final String CRIA_TABELA_CT_EXPORTAR = "CREATE TABLE if not exists `TB_CT_EXPORTAR` (\n"
            + "	`NOME_CT`	TEXT UNIQUE,\n"
            + "	`DUPLICADO`	INTEGER,\n"
            + "	`SOBRESCREVER`	INTEGER,\n"
            + "	`LINHA_PLANILHA`	INTEGER,\n"
            + "	PRIMARY KEY(NOME_CT)\n"
            + ");";

    private final String CRIA_TABELA_USUARIO_ALM = "CREATE TABLE if not exists `TB_USUARIO_ALM` (\n"
            + "	`ID_USUARIO`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`USUARIO`	TEXT UNIQUE,\n"
            + "	`SENHA`	TEXT\n"
            + ");";

    private final String CRIA_TABELA_CAMPOS_OBRIGATORIOS = "CREATE TABLE if not exists `TB_CAMPOS_OBRIGATORIOS` (\n"
            + "	`COD_CAMPO`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`NOME_CAMPO`	TEXT UNIQUE,\n"
            + "	`OBRIGATORIO`	INTEGER\n"
            + ");";

    private final String CRIA_TABELA_CT_IMPORTAR = "CREATE TABLE if not exists `TB_CT_IMPORTAR` (\n"
            + "	`NOME_CT`	TEXT UNIQUE,\n"
            + "	`DUPLICADO`	INTEGER,\n"
            + "	`SOBRESCREVER`	INTEGER,\n"
            + "	`LINHA_PLANILHA`	INTEGER,\n"
            + "	PRIMARY KEY(NOME_CT)\n"
            + ");";

    private final String CRIA_VERSAO_CARGA = "CREATE TABLE if not exists `TB_VERSAO_CARGA` (\n"
            + "	`COD_CARGA`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_VERSAO`	TEXT NOT NULL UNIQUE,\n"
            + "	`DT_CARGA`	TEXT NOT NULL\n"
            + ");";

    private final String CRIA_TABELA_COMPLEXIDADE = "CREATE TABLE if not exists `TB_COMPLEXIDADE` (\n"
            + "	`COD_COMPLEXIDADE`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_COMPLEXIDADE`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_AUTOMATIZAVEL = "CREATE TABLE if not exists `TB_AUTOMATIZAVEL` (\n"
            + "	`COD_AUTOMATIZAVEL`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_AUTOMATIZAVEL`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_TYPE = "CREATE TABLE if not exists `TB_TYPE` (\n"
            + "	`COD_TYPE`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_TYPE`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_TRG = "CREATE TABLE if not exists `TB_TRG` (\n"
            + "	`COD_TRG`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_TRG`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_CADEIA = "CREATE TABLE if not exists `TB_CADEIA` (\n"
            + "	`COD_CADEIA`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_CADEIA`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_CRIACAO = "CREATE TABLE if not exists `TB_CRIACAO` (\n"
            + "	`COD_CRIACAO`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_CRIACAO`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_TP_REQUISITO = "CREATE TABLE if not exists `TB_TP_REQUISITO` (\n"
            + "	`COD_TP_REQUISITO`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_TP_REQUISITO`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_SISTEMA_MASTER = "CREATE TABLE if not exists `TB_SISTEMA_MASTER` (\n"
            + "	`COD_SISTEMA_MASTER`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_SISTEMA_MASTER`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_FUNCIONALIDADE = "CREATE TABLE if not exists `TB_FUNCIONALIDADE` (\n"
            + "	`COD_FUNCIONALIDADE`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_FUNCIONALIDADE`	TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_CENARIO_INTEGRADO = "CREATE TABLE if not exists `TB_CENARIO_INTEGRADO` (\n"
            + "	`COD_CENARIO_INTEGRADO`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	`DESC_CENARIO_INTEGRADO` TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_CASO_TESTE_TEMP = "CREATE TABLE if not exists `TB_CASO_TESTE_TEMP` (\n"
            + "	`CELULA_PLANILHA`	INTEGER NOT NULL,\n"
            + "	`DESC_CASO_TESTE` TEXT NOT NULL UNIQUE\n"
            + ");";

    private final String CRIA_TABELA_PLANOS = "create table if not exists TB_PLANOS\n"
            + "(\n"
            + "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	FUNCIONALIDADE_SERVICO VARCHAR(100),\n"
            + "	SISTEMA_MASTER VARCHAR(100),\n"
            + "	SISTEMAS_ENVOLVIDOS VARCHAR(100),\n"
            + "	FORNECEDOR VARCHAR(100),\n"
            + "	TP_REQUISITO VARCHAR(100),\n"
            + "	REQUISITO VARCHAR(100),\n"
            + "	CENARIO_TESTE VARCHAR(500),\n"
            + "	CASO_TESTE VARCHAR(500) UNIQUE NOT NULL,\n"
            + "	CADEIA VARCHAR(250),\n"
            + "	SEGMENTO VARCHAR(250),\n"
            + "	PRODUTO VARCHAR(250),\n"
            + "	CENARIO_INTEGRADO VARCHAR(250),\n"
            + "	QTD_SISTEMAS INTEGER,\n"
            + "	CENARIO_AUTOMATIZAVEL VARCHAR(20),\n"
            + "	TP_TYPE VARCHAR(250),\n"
            + "	TRG VARCHAR(250),\n"
            + "	SUBJECT VARCHAR(250),\n"
            + "	CRIACAO_ALTERACAO VARCHAR(50),\n"
            + "	NOME_ARQUIVO VARCHAR(250),\n"
            + "	DESCRICAO_CASO_TESTE VARCHAR(800)\n"
            + ")";
    private final String CRIA_TABELA_STEPS = "create table if not exists TB_STEPS\n"
            + "(\n"
            + "	ID_STEP INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	NOME_STEP VARCHAR(50),\n"
            + "	ID_PLANO INTEGER,\n"
            + "	DESC_STEP VARCHAR(1000),\n"
            + "	RESULTADO_ESPERADO VARCHAR(1000),\n"
            + "	FOREIGN KEY(ID_PLANO) REFERENCES TB_PLANOS(ID)\n"
            + "	\n"
            + ");";

    private final String CRIA_TABELA_STEP_PADRAO = "create table if not exists TB_STEP_PADRAO\n"
            + "(\n"
            + "	ID_STEP_PADRAO INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "	DESC_STEP VARCHAR(1000) NOT NULL,\n"
            + "	RESULTADO_ESPERADO VARCHAR(1000) NOT NULL,\n"
            + "	TIPO_STEP_PADRAO VARCHAR(100) NOT NULL,\n"
            + "	SISTEMA VARCHAR(100) NOT NULL,\n"
            + "	VERSAO REAL NOT NULL\n"
            + "	\n"
            + ");";

    private final String SELECT_PLANO_STEP = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = ? AND P.ID = S.ID_PLANO";
    private final String SELECT_ALL_PLANOS = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO ORDER BY CASO_TESTE ASC";
    private final String INSERT_PLANO = "insert into TB_PLANOS(FUNCIONALIDADE_SERVICO, SISTEMA_MASTER, SISTEMAS_ENVOLVIDOS,FORNECEDOR,TP_REQUISITO,REQUISITO,CENARIO_TESTE,CASO_TESTE, DESCRICAO_CASO_TESTE, "
            + "CADEIA, SEGMENTO, PRODUTO, CENARIO_INTEGRADO, QTD_SISTEMAS, CENARIO_AUTOMATIZAVEL, TP_TYPE , TRG , SUBJECT, CRIACAO_ALTERACAO, NOME_ARQUIVO) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String INSERT_VERSAO_CARGA = "insert into TB_VERSAO_CARGA(DESC_VERSAO,DT_CARGA ) VALUES(?, DateTime('now'))";

    private final String INSERT_STEP = "insert into TB_STEPS(NOME_STEP, DESC_STEP, RESULTADO_ESPERADO, ID_PLANO) "
            + "VALUES(?, ?, ?, ?)";
    private final String SELECT_ALL_STEP_PADRAO = "SELECT * FROM TB_STEP_PADRAO";
    private final String INSERT_STEP_PADRAO = "INSERT INTO TB_STEP_PADRAO(DESC_STEP, RESULTADO_ESPERADO, TIPO_STEP_PADRAO, SISTEMA,  VERSAO  ) VALUES (?, ?, ?, ? , ?)";
    private final String DELETE_STEP_PADRAO = "DELETE * FROM TB_STEP_PADRAO WHERE DESC_STEP = ? AND RESULTADO_ESPERADO = ?;";
    private final String UPDATE_STEP_PADRAO = "UPDATE TB_STEP_PADRAO SET DESC_STEP = ? AND RESULTADO_ESPERADO = ?;";
    private final String INSERT_CONF = "INSERT INTO ? ( ? ) VALUES( ? )";

    private final String INSERT_TB_CASO_TESTE_TEMP = "INSERT INTO TB_CASO_TESTE_TEMP ( CELULA_PLANILHA, DESC_CASO_TESTE ) VALUES( ?, ? )";
    private final String UPDATE_TB_CT_EXPORTAR = "update TB_CT_EXPORTAR\n"
            + "set DUPLICADO = ?, SOBRESCREVER = ?, LINHA_PLANILHA = ?\n"
            + "where NOME_CT = ?;";

    private final String UPDATE_TB_CT_IMPORTAR = "update TB_CT_IMPORTAR\n"
            + "set DUPLICADO = ?, SOBRESCREVER = ?, LINHA_PLANILHA = ?\n"
            + "where NOME_CT = ?;";

    private final String INSERT_TB_CT_EXPORTAR = "INSERT INTO TB_CT_EXPORTAR ( NOME_CT, DUPLICADO, SOBRESCREVER, LINHA_PLANILHA ) VALUES( ?, ?, ?, ? )";

    private final String INSERT_TB_CT_IMPORTAR = "INSERT INTO TB_CT_IMPORTAR ( NOME_CT, DUPLICADO, SOBRESCREVER, LINHA_PLANILHA ) VALUES( ?, ?, ?, ? )";

    private final String DELETE_TB_CT_IMPORTAR = "DELETE * FROM TB_CT_IMPORTAR";

    private final String DELETE_PLANO_STEP = "DELETE FROM TB_PLANOS WHERE ID = ?;"; // DELETE FROM TB_STEPS WHERE ID_PLANO = ?;";
    private final String DELETE_STEP = "DELETE FROM TB_STEPS WHERE ID_PLANO = ?;";

    private final String SELECT_ID_PLANO = "SELECT ID FROM TB_PLANOS where CASO_TESTE = ? ";
    private final String SELECT_PLANO_POR_CONDICAO = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = ? AND  P.FUNCIONALIDADE_SERVICO = ?\n"
            + "AND P.SISTEMA_MASTER = ? AND P.SISTEMAS_ENVOLVIDOS = ? AND P.FORNECEDOR = ? AND P.TP_REQUISITO = ?\n"
            + "AND P.REQUISITO = ?  AND P.CENARIO_TESTE = ? AND P.CASO_TESTE = ? AND P.CADEIA = ?\n"
            + "AND P.SEGMENTO = ? AND P.PRODUTO = ? AND P.CENARIO_INTEGRADO = ? AND P.QTD_SISTEMAS = ?\n"
            + "AND P.CENARIO_AUTOMATIZAVEL = ? AND P.TP_TYPE = ? AND P.TRG = ? AND P.SUBJECT = ?\n"
            + "AND P.CRIACAO_ALTERACAO = ? AND P.NOME_ARQUIVO = ? AND P.DESCRICAO_CASO_TESTE = ?\n"
            + "AND S.ID_STEP = ? AND S.NOME_STEP = ? AND S.ID_PLANO = ? AND S.DESC_STEP = ?\n"
            + "AND S.RESULTADO_ESPERADO = ?  AND P.ID = S.ID_PLANO";

    private final String SELECT_PLANO_POR_ID = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = ? AND P.ID = S.ID_PLANO ORDER BY CAST(SUBSTR(NOME_STEP,6,7) as INTEGER) ASC;";
    private final String SELECT_MENOR_ID_PLANO = "SELECT * FROM TB_PlANOS, TB_STEPS order by id asc limit 1";
    private final String SELECT_CASO_TESTE = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO AND caso_teste like '% ? %' ORDER BY ID ASC;";
    private final String SELECT_PROXIMO_ID_PLANO = "select sum(seq+1) from sqlite_sequence where name = 'TB_PLANOS'";
    private final String UPDATE_TB_PLANOS = "update TB_PLANOS\n"
            + "set FUNCIONALIDADE_SERVICO = ?,  SISTEMA_MASTER = ?, SISTEMAS_ENVOLVIDOS = ?, FORNECEDOR = ?, TP_REQUISITO= ?, REQUISITO = ?, CENARIO_TESTE = ?, CASO_TESTE = ?, DESCRICAO_CASO_TESTE = ?, CADEIA = ?,SEGMENTO = ?,  PRODUTO = ?,  CENARIO_INTEGRADO = ?,  QTD_SISTEMAS = ?,  CENARIO_AUTOMATIZAVEL = ?,  TP_TYPE  = ?,  TRG  = ?,  SUBJECT = ?,  CRIACAO_ALTERACAO = ?,  NOME_ARQUIVO = ?\n"
            + "where id = ?;";
    private final String UPDATE_TB_STEPS = "update TB_STEPS\n"
            + "set NOME_STEP = ?,  DESC_STEP = ?,  RESULTADO_ESPERADO = ?\n"
            + "where ID_PLANO = ? AND ID_step = ?";

    private final String INSERT_CAMPO_NOME_CT = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('CASO_TESTE', 0);";
    private final String INSERT_CAMPO_FUNCIONALIDADE = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('FUNCIONALIDADE_SERVICO', 0);";
    private final String INSERT_CAMPO_MASTER = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('SISTEMA_MASTER', 0);";
    private final String INSERT_CAMPO_ENVOLVIDOS = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('SISTEMAS_ENVOLVIDOS', 0);";
    private final String INSERT_CAMPO_FORNECEDOR = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('FORNECEDOR', 0);";
    private final String INSERT_CAMPO_CENARIO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('CENARIO_TESTE', 0);";
    private final String INSERT_CAMPO_TPREQUISITO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('TP_REQUISITO', 0);";
    private final String INSERT_CAMPO_REQUISITO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('REQUISITO', 0);";
    private final String INSERT_CAMPO_CRIACAO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('CRIACAO_ALTERACAO', 0);";
    private final String INSERT_CAMPO_CADEIA = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('CADEIA', 0);";
    private final String INSERT_CAMPO_SEGMENTO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('SEGMENTO', 0);";
    private final String INSERT_CAMPO_INTEGRADO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('CENARIO_INTEGRADO', 0);";
    private final String INSERT_CAMPO_AUTO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('CENARIO_AUTOMATIZAVEL', 0);";
    private final String INSERT_CAMPO_TYPE = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('TP_TYPE', 0);";
    private final String INSERT_CAMPO_TRG = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('TRG', 0);";
    private final String INSERT_CAMPO_DESCRICAO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('DESCRICAO_CASO_TESTE', 0);";
    private final String INSERT_CAMPO_QTDSISTEMAS = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('QTD_SISTEMAS', 0);";
    private final String INSERT_CAMPO_PRODUTO = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('PRODUTO', 0);";
    private final String INSERT_CAMPO_SUBJECT = "INSERT OR IGNORE INTO TB_CAMPOS_OBRIGATORIOS (NOME_CAMPO, OBRIGATORIO) VALUES ('SUBJECT', 0);";
    private final String INSERT_TB_USUARIO_ALM = "INSERT INTO TB_USUARIO_ALM (USUARIO, SENHA) VALUES (? , ?);";

    public ManipulaDadosSQLite() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + fileBanco);
        this.stm = this.conn.createStatement();

    }

    public ManipulaDadosSQLite(boolean inicia) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + fileBanco);
        this.stm = this.conn.createStatement();
        iniciaBanco();

    }

    public Statement getConexao() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + fileBanco);
        this.stm = this.conn.createStatement();
        return stm;
    }

    public final void iniciaBanco() throws SQLException {

        this.stm.executeUpdate(CRIA_TABELA_PLANOS);
        this.stm.executeUpdate(CRIA_TABELA_STEPS);
        this.stm.executeUpdate(CRIA_TABELA_STEP_PADRAO);
        this.stm.executeUpdate(CRIA_TABELA_COMPLEXIDADE);
        this.stm.executeUpdate(CRIA_TABELA_AUTOMATIZAVEL);
        this.stm.executeUpdate(CRIA_TABELA_CADEIA);
        this.stm.executeUpdate(CRIA_TABELA_CENARIO_INTEGRADO);
        this.stm.executeUpdate(CRIA_TABELA_CRIACAO);
        this.stm.executeUpdate(CRIA_TABELA_SISTEMA_MASTER);
        this.stm.executeUpdate(CRIA_TABELA_TP_REQUISITO);
        this.stm.executeUpdate(CRIA_TABELA_TRG);
        this.stm.executeUpdate(CRIA_TABELA_TYPE);
        this.stm.executeUpdate(CRIA_TABELA_FUNCIONALIDADE);
        this.stm.executeUpdate(CRIA_VERSAO_CARGA);
        this.stm.executeUpdate(CRIA_TABELA_CASO_TESTE_TEMP);
        this.stm.executeUpdate(CRIA_TABELA_CT_EXPORTAR);
        this.stm.executeUpdate(CRIA_TABELA_CT_IMPORTAR);
        this.stm.executeUpdate(CRIA_TABELA_CAMPOS_OBRIGATORIOS);
        this.stm.executeUpdate(INSERT_CAMPO_NOME_CT);
        this.stm.executeUpdate(INSERT_CAMPO_FUNCIONALIDADE);
        this.stm.executeUpdate(INSERT_CAMPO_MASTER);
        this.stm.executeUpdate(INSERT_CAMPO_ENVOLVIDOS);
        this.stm.executeUpdate(INSERT_CAMPO_FORNECEDOR);
        this.stm.executeUpdate(INSERT_CAMPO_CENARIO);
        this.stm.executeUpdate(INSERT_CAMPO_TPREQUISITO);
        this.stm.executeUpdate(INSERT_CAMPO_REQUISITO);
        this.stm.executeUpdate(INSERT_CAMPO_CRIACAO);
        this.stm.executeUpdate(INSERT_CAMPO_CADEIA);
        this.stm.executeUpdate(INSERT_CAMPO_SEGMENTO);
        this.stm.executeUpdate(INSERT_CAMPO_INTEGRADO);
        this.stm.executeUpdate(INSERT_CAMPO_AUTO);
        this.stm.executeUpdate(INSERT_CAMPO_TYPE);
        this.stm.executeUpdate(INSERT_CAMPO_TRG);
        this.stm.executeUpdate(INSERT_CAMPO_DESCRICAO);
        this.stm.executeUpdate(INSERT_CAMPO_QTDSISTEMAS);
        this.stm.executeUpdate(INSERT_CAMPO_PRODUTO);
        this.stm.executeUpdate(INSERT_CAMPO_SUBJECT);
        this.stm.executeUpdate(CRIA_TABELA_USUARIO_ALM);

    }

    public List<String> getCamposObrigatorios() throws SQLException {
        List<String> listCamposObrigatorios = new ArrayList<String>();
        String sql = "SELECT NOME_CAMPO FROM TB_CAMPOS_OBRIGATORIOS WHERE OBRIGATORIO = 1;";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            listCamposObrigatorios.add(rs.getString("NOME_CAMPO"));
        }

        ps.close();
        return listCamposObrigatorios;

    }

    public void atualizaCamposObrigatorios(String nome, int valor) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("update TB_CAMPOS_OBRIGATORIOS"
                + " set OBRIGATORIO = ? where NOME_CAMPO = ? ;");

        ps.setInt(1, valor);
        ps.setString(2, nome);

        ps.executeUpdate();

        ps.close();

    }

    public boolean insertUsuarioAlm(UsuarioALM usuarioALM) throws SQLException {

        String sql = "DELETE FROM TB_USUARIO_ALM";
        PreparedStatement psDelete = conn.prepareStatement(sql);
        psDelete.executeUpdate();
        PreparedStatement ps = conn.prepareStatement(INSERT_TB_USUARIO_ALM);
        //loop para varrer toda lista

        ps.setString(1, usuarioALM.getUsuario());
        ps.setString(2, usuarioALM.getSenha());

        ps.execute();

        ps.close();

        return retorno;
    }

    public UsuarioALM getUsuarioALM() throws SQLException {

        UsuarioALM usuarioALM = new UsuarioALM();

        String sql = "SELECT * FROM TB_USUARIO_ALM;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            usuarioALM.setSenha(resultSet.getString("SENHA"));
            usuarioALM.setUsuario(resultSet.getString("USUARIO"));

        }
        ps.close();
        return usuarioALM;
    }

    public boolean insertCasoTesteExportar(List<CasoTesteTemp> casoTesteTemp) throws SQLException {

        String sql = "DELETE FROM TB_CT_EXPORTAR";
        PreparedStatement psDelete = conn.prepareStatement(sql);
        psDelete.executeUpdate();
        PreparedStatement ps = conn.prepareStatement(INSERT_TB_CT_EXPORTAR);
        //loop para varrer toda lista
        for (int i = 0; i < casoTesteTemp.size(); i++) {

            ps.setString(1, casoTesteTemp.get(i).getCasoTeste());
            ps.setInt(2, casoTesteTemp.get(i).getDuplicado());
            ps.setInt(3, casoTesteTemp.get(i).getSobrescrever());
            ps.setInt(4, casoTesteTemp.get(i).getCelula());
            ps.execute();
        }

        ps.close();

        return retorno;
    }

    public boolean insertCasoTesteImportar(List<CasoTesteTemp> casoTesteTemp) throws SQLException {

        String sql = "DELETE FROM TB_CT_IMPORTAR";
        PreparedStatement psDelete = conn.prepareStatement(sql);
        psDelete.executeUpdate();
        PreparedStatement ps = conn.prepareStatement(INSERT_TB_CT_IMPORTAR);

        //loop para varrer toda lista
        for (int i = 0; i < casoTesteTemp.size(); i++) {

            ps.setString(1, casoTesteTemp.get(i).getCasoTeste());
            ps.setInt(2, casoTesteTemp.get(i).getDuplicado());
            ps.setInt(3, casoTesteTemp.get(i).getSobrescrever());
            ps.setInt(4, casoTesteTemp.get(i).getCelula());
            ps.execute();
        }

        ps.close();

        return retorno;
    }

    public void updateCasoTesteImportar(CasoTesteTemp temp) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(UPDATE_TB_CT_IMPORTAR);

        ps.setInt(1, temp.getDuplicado());
        ps.setInt(2, temp.getSobrescrever());
        ps.setInt(3, temp.getCelula());
        ps.setString(4, temp.getCasoTeste());

        ps.executeUpdate();

        ps.close();

    }

    public boolean updateCasoTesteImportar2(CasoTesteTemp temp) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("update TB_CT_IMPORTAR"
                + " set DUPLICADO = ?, SOBRESCREVER = ?, LINHA_PLANILHA = ? " + " where DUPLICADO = ? ;");

        ps.setInt(1, temp.getDuplicado());
        ps.setInt(2, temp.getSobrescrever());
        ps.setInt(3, temp.getCelula());
        ps.setInt(4, temp.getDuplicado());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    public boolean updateCasoTesteExportar(CasoTesteTemp temp) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(UPDATE_TB_CT_EXPORTAR);

        ps.setInt(1, temp.getDuplicado());
        ps.setInt(2, temp.getSobrescrever());
        ps.setInt(3, temp.getCelula());
        ps.setString(4, temp.getCasoTeste());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    public List<CasoTesteTemp> getCasoTesteImportar() throws SQLException {

        List<CasoTesteTemp> listCasoTesteTemp = new ArrayList<CasoTesteTemp>();

        String sql = "SELECT * FROM TB_CT_IMPORTAR WHERE SOBRESCREVER = 1;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            CasoTesteTemp ct = new CasoTesteTemp();
            ct.setCasoTeste(resultSet.getString(1));
            ct.setDuplicado(resultSet.getInt(2));
            ct.setSobrescrever(resultSet.getInt(3));
            ct.setCelula(resultSet.getInt(4));

            listCasoTesteTemp.add(ct);

        }
        ps.close();
        return listCasoTesteTemp;
    }

    public List<CasoTesteTemp> getCasoTesteExportarSobrescrever() throws SQLException {

        List<CasoTesteTemp> listCasoTesteTemp = new ArrayList<CasoTesteTemp>();

        String sql = "SELECT * FROM TB_CT_EXPORTAR WHERE DUPLICADO = 1 AND SOBRESCREVER = 1;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            CasoTesteTemp ct = new CasoTesteTemp();
            ct.setCasoTeste(resultSet.getString(1));
            ct.setCelula(resultSet.getInt(4));

            listCasoTesteTemp.add(ct);

        }

        return listCasoTesteTemp;
    }

    public List<CasoTesteTemp> getCasoTesteImportarSobrescrever() throws SQLException {

        List<CasoTesteTemp> listCasoTesteTemp = new ArrayList<CasoTesteTemp>();

        String sql = "SELECT * FROM TB_CT_EXPORTAR WHERE DUPLICADO = 1 AND SOBRESCREVER = 1;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            CasoTesteTemp ct = new CasoTesteTemp();
            ct.setCasoTeste(resultSet.getString(1));
            ct.setCelula(resultSet.getInt(4));

            listCasoTesteTemp.add(ct);

        }

        return listCasoTesteTemp;
    }

    public List<CasoTesteTemp> getCasoTesteExportar() throws SQLException {

        List<CasoTesteTemp> listCasoTesteTemp = new ArrayList<CasoTesteTemp>();

        String sql = "SELECT * FROM TB_CT_EXPORTAR WHERE DUPLICADO = 0 AND SOBRESCREVER = 0;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            CasoTesteTemp ct = new CasoTesteTemp();
            ct.setCasoTeste(resultSet.getString(1));
            ct.setCelula(resultSet.getInt(4));

            listCasoTesteTemp.add(ct);

        }

        return listCasoTesteTemp;
    }

    public boolean insertCasoTesteTemp(List<CasoTesteTemp> casoTesteTemp) throws SQLException {

        //apaga a tabela tb_caso_teste_temp
        String sql = "DELETE FROM TB_CASO_TESTE_TEMP";
        PreparedStatement psDelete = conn.prepareStatement(sql);
        psDelete.executeUpdate();
        //loop para varrer toda lista
        for (int i = 0; i < casoTesteTemp.size(); i++) {
            PreparedStatement ps = conn.prepareStatement(INSERT_TB_CASO_TESTE_TEMP);
            ps.setInt(1, casoTesteTemp.get(i).getCelula());
            ps.setString(2, casoTesteTemp.get(i).getCasoTeste());
            ps.execute();
        }

        return retorno;
    }

    public List<CasoTesteTemp> getCasoTesteTemp() throws SQLException {

        List<CasoTesteTemp> listCasoTesteTemp = new ArrayList<CasoTesteTemp>();

        String sql = "SELECT * FROM TB_CASO_TESTE_TEMP;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            CasoTesteTemp ct = new CasoTesteTemp();
            ct.setCelula(resultSet.getInt(1));
            ct.setCasoTeste(resultSet.getString(2));
            listCasoTesteTemp.add(ct);

        }

        return listCasoTesteTemp;
    }

    public int getMaiorIdCasoTesteTemp() throws SQLException, ClassNotFoundException {
        int maiorId;
        String sql = "SELECT MAX(CELULA_PLANILHA ) FROM TB_CASO_TESTE_TEMP;";

//                stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        maiorId = resultSet.getInt(1);

        ps.close();

        if (maiorId <= 6) {
            maiorId = 7;
            return maiorId;
        }

        return maiorId + 25;
    }

    public boolean getCasoTesteTempBoolean(String casoTeste) throws SQLException {
        boolean retorno = false;
        List<CasoTesteTemp> listCasoTesteTemp = new ArrayList<CasoTesteTemp>();
        CasoTesteTemp ct = new CasoTesteTemp();

        String sql = "SELECT * FROM TB_CASO_TESTE_TEMP WHERE desc_caso_teste like '%" + casoTeste + "%';";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        if (!resultSet.getString(2).equals(null)) {
            retorno = true;
        }

        resultSet.close();

//            while (resultSet.next()) {
//                ct.setCelula(resultSet.getInt(1));
//                ct.setCasoTeste(resultSet.getString(2));
//                listCasoTesteTemp.add(ct);
//
//            }
        return retorno;
    }

    public boolean verificaCargaConf() throws SQLException {
        boolean retorno = false;
        int qtd = 0;

        String sql = "SELECT count(*) as qtd FROM TB_VERSAO_CARGA;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            qtd = resultSet.getInt("qtd");
        }
        ps.close();

        if (qtd >= 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean insertVersaCarga(String versao) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM TB_VERSAO_CARGA";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.execute();
        ps.close();

        PreparedStatement ps1 = conn.prepareStatement(INSERT_VERSAO_CARGA);
        ps1.setString(1, versao);

        retorno = ps1.execute();
        ps1.close();

        return retorno;
    }

    public boolean insertPlano(Plano plano) throws ClassNotFoundException {
        try {

            stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(INSERT_PLANO);
            ps.setString(1, plano.getFuncionalidade());
            ps.setString(2, plano.getSistemaMaster());
            ps.setString(3, plano.getSistemasEnvolvidos());
            ps.setString(4, plano.getFornecedor());
            ps.setString(5, plano.getTpRequisito());
            ps.setString(6, plano.getRequisito());
            ps.setString(7, plano.getCenarioTeste());
            ps.setString(8, plano.getCasoTeste());
            ps.setString(9, plano.getDescCasoTeste());
            ps.setString(10, plano.getCadeia());
            ps.setString(11, plano.getSegmento());
            ps.setString(12, plano.getProduto());
            ps.setString(13, plano.getCenarioIntegrado());
            ps.setInt(14, plano.getQtdSistemas());
            ps.setString(15, plano.getCenarioAutomatizavel());
            ps.setString(16, plano.getType());
            ps.setString(17, plano.getTrg());
            ps.setString(18, plano.getSubject());
            ps.setString(19, plano.getCriacaoAlteracao());
            ps.setString(20, plano.getNomeArquivo());

            retorno = ps.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return retorno;

    }

    public boolean deletePlano(Plano plano) throws SQLException, ClassNotFoundException {

        try {
//                stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(DELETE_PLANO_STEP);
            ps.setInt(1, plano.getId());
//                ps.setInt(2, plano.getId());

            ps.executeUpdate();

            ps = conn.prepareStatement("DELETE FROM TB_STEPS WHERE ID_PLANO = ?;");
            ps.setInt(1, plano.getId());
            ps.executeUpdate();

            ps.close();
            retorno = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            retorno = false;
        }

        return retorno;
    }

    public int selectProximoIdPlano() throws SQLException, ClassNotFoundException {
        int proximoIdPlano;

        try {
//                stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(SELECT_PROXIMO_ID_PLANO);
            ResultSet resultSet = ps.executeQuery();
            proximoIdPlano = resultSet.getInt(1);

            ps.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return proximoIdPlano;
    }

    public Plano selectMenorIdPlano() throws SQLException, ClassNotFoundException {
        int menorIdPlano;
        Plano p = new Plano();

        try {
//                stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(SELECT_MENOR_ID_PLANO);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                Step stepTemp = new Step();
                p.setId(resultSet.getInt("ID"));
                p.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
                p.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
                p.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
                p.setFornecedor(resultSet.getString("FORNECEDOR"));
                p.setTpRequisito(resultSet.getString("TP_REQUISITO"));
                p.setRequisito(resultSet.getString("REQUISITO"));
                p.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
                p.setCasoTeste(resultSet.getString("CASO_TESTE"));
                p.setCadeia(resultSet.getString("CADEIA"));
                p.setSegmento(resultSet.getString("SEGMENTO"));
                p.setProduto(resultSet.getString("PRODUTO"));
                p.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
                p.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
                p.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
                p.setType(resultSet.getString("TP_TYPE"));
                p.setTrg(resultSet.getString("TRG"));
                p.setSubject(resultSet.getString("SUBJECT"));
                p.setCriacaoAlteracao(resultSet.getNString("CRIACAO_ALTERACAO"));
                p.setNomeArquivo(resultSet.getNString("NOME_ARQUIVO"));
                p.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));
                stepTemp.setId(resultSet.getInt("ID_STEP"));
                stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
                stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
                stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
                stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
                p.setStep(stepTemp);

            }

            ps.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return p;
    }

    public List<StepPadrao> getAllStepPadrao() throws SQLException, ClassNotFoundException {
        List<StepPadrao> listStep = new ArrayList<StepPadrao>();

        stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL_STEP_PADRAO);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            StepPadrao stepTemp = new StepPadrao();

            stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
            stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
            stepTemp.setId(resultSet.getInt("ID_STEP_PADRAO"));
            stepTemp.setTipoStepPadrao(resultSet.getString("TIPO_STEP_PADRAO"));
            stepTemp.setSistema(resultSet.getString("SISTEMA"));
            stepTemp.setVersao(resultSet.getDouble("VERSAO"));

            listStep.add(stepTemp);

        }

        return listStep;
    }

    public double getVersaoStepPadrao() throws SQLException, ClassNotFoundException {
        List<StepPadrao> listStep = new ArrayList<StepPadrao>();

        stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL_STEP_PADRAO);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            StepPadrao stepTemp = new StepPadrao();

            stepTemp.setVersao(resultSet.getDouble("VERSAO"));

            listStep.add(stepTemp);

        }

        return listStep.get(0).getVersao();
    }

    public boolean updateStepPadrao(Step step) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(UPDATE_STEP_PADRAO);

        ps.setString(1, step.getDescStep());
        ps.setString(2, step.getResultadoStep());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    public boolean getCountStepPadrao() throws SQLException {

        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS QTD FROM TB_STEP_PADRAO");
        int qtd = 0;
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            qtd = resultSet.getInt("QTD");
        }
        ps.close();
        
        if(qtd >= 1){
            return true;
        }else{
            return false;
        }

        
    }

    public boolean insertStepPadrao(StepPadrao step) throws SQLException {

        String sql = "DELETE FROM TB_STEP_PADRAO";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.execute();
        ps.close();

        ps = conn.prepareStatement(INSERT_STEP_PADRAO);

        ps.setString(1, step.getDescStep());
        ps.setString(2, step.getResultadoStep());
        ps.setString(3, step.getTipoStepPadrao());
        ps.setString(4, step.getSistema());
        ps.setDouble(5, step.getVersao());

        ps.execute();

        ps.close();

        return true;
    }

    public boolean insertStepPadrao(List<StepPadrao> listStepPadrao) throws SQLException {

        String sql = "DELETE FROM TB_STEP_PADRAO";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.execute();
        ps.close();

        ps = conn.prepareStatement(INSERT_STEP_PADRAO);

        for (int i = 0; i < listStepPadrao.size(); i++) {
            ps.setString(1, listStepPadrao.get(i).getDescStep());
            ps.setString(2, listStepPadrao.get(i).getResultadoStep());
            ps.setString(3, listStepPadrao.get(i).getTipoStepPadrao());
            ps.setString(4, listStepPadrao.get(i).getSistema());
            ps.setDouble(5, listStepPadrao.get(i).getVersao());

            ps.execute();

        }
        ps.close();

        return true;
    }

    public List<Plano> getCTsPorQuery(String query) throws SQLException, ClassNotFoundException {
        List<Plano> listPlano = new ArrayList<Plano>();

        stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet resultSet = ps.executeQuery();
        int temp = 0;
        while (resultSet.next()) {

            Plano planoTemp = new Plano();
            Step stepTemp = new Step();
            List<Step> listStep = new ArrayList<Step>();
            planoTemp.setId(resultSet.getInt("ID"));
//            temp = resultSet.getInt("ID");
            planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
            planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
            planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
            planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
            planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
            planoTemp.setRequisito(resultSet.getString("REQUISITO"));
            planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
            planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
            planoTemp.setCadeia(resultSet.getString("CADEIA"));
            planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
            planoTemp.setProduto(resultSet.getString("PRODUTO"));
            planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
            planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
            planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
            planoTemp.setType(resultSet.getString("TP_TYPE"));
            planoTemp.setTrg(resultSet.getString("TRG"));
            planoTemp.setSubject(resultSet.getString("SUBJECT"));
            planoTemp.setCriacaoAlteracao(resultSet.getString("CRIACAO_ALTERACAO"));
            planoTemp.setNomeArquivo(resultSet.getString("NOME_ARQUIVO"));
            planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));

            listStep = getStepPorPlano(planoTemp);
//            while (resultSet.next() && temp == resultSet.getInt("ID")) {
//                stepTemp.setId(resultSet.getInt("ID_STEP"));
//                stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
//                stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
//                stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
//                stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
//                listStep.add(stepTemp);
//                temp = resultSet.getInt("ID_PLANO");
//            }

            planoTemp.setListStep(listStep);
            if (temp != resultSet.getInt("ID")) {

                listPlano.add(planoTemp);

                temp = resultSet.getInt("ID_PLANO");
            }

        }

        return listPlano;
    }

    public List<Plano> selectPlanoALL() throws SQLException, ClassNotFoundException {
        List<Plano> listPlano = new ArrayList<Plano>();

        try {
            stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PLANOS);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Plano planoTemp = new Plano();
                Step stepTemp = new Step();
                planoTemp.setId(resultSet.getInt("ID"));
                planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
                planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
                planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
                planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
                planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
                planoTemp.setRequisito(resultSet.getString("REQUISITO"));
                planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
                planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
                planoTemp.setCadeia(resultSet.getString("CADEIA"));
                planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
                planoTemp.setProduto(resultSet.getString("PRODUTO"));
                planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
                planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
                planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
                planoTemp.setType(resultSet.getString("TP_TYPE"));
                planoTemp.setTrg(resultSet.getString("TRG"));
                planoTemp.setSubject(resultSet.getString("SUBJECT"));
                planoTemp.setCriacaoAlteracao(resultSet.getNString("CRIACAO_ALTERACAO"));
                planoTemp.setNomeArquivo(resultSet.getNString("NOME_ARQUIVO"));
                planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));
                stepTemp.setId(resultSet.getInt("ID_STEP"));
                stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
                stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
                stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
                stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
                planoTemp.setStep(stepTemp);

                listPlano.add(planoTemp);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listPlano;
    }

    public List<Plano> selectPorCasoTeste(Plano plano) throws SQLException, ClassNotFoundException {
        List<Plano> listPlano = new ArrayList<Plano>();
        String casoTeste = plano.getCasoTeste();
        String sql = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO AND caso_teste like '%" + casoTeste + "%' ORDER BY ID ASC;";

        PreparedStatement ps = conn.prepareStatement(sql);
//                  ps.setString(1, plano.getCasoTeste());

        ResultSet resultSet = ps.executeQuery();

//            resultSet.last();
//            int qtdLinha = resultSet.getRow();
//            resultSet.first();
        while (resultSet.next()) {
            Plano planoTemp = new Plano();
            Step stepTemp = new Step();
            planoTemp.setId(resultSet.getInt("ID"));
            planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
            planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
            planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
            planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
            planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
            planoTemp.setRequisito(resultSet.getString("REQUISITO"));
            planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
            planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
            planoTemp.setCadeia(resultSet.getString("CADEIA"));
            planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
            planoTemp.setProduto(resultSet.getString("PRODUTO"));
            planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
            planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
            planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
            planoTemp.setType(resultSet.getString("TP_TYPE"));
            planoTemp.setTrg(resultSet.getString("TRG"));
            planoTemp.setSubject(resultSet.getString("SUBJECT"));
            planoTemp.setCriacaoAlteracao(resultSet.getNString("CRIACAO_ALTERACAO"));
            planoTemp.setNomeArquivo(resultSet.getNString("NOME_ARQUIVO"));
            planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));

//                for(int i = 0; i < qtdLinha; i++){
            stepTemp.setId(resultSet.getInt("ID_STEP"));
            stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
            stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
            stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
            stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
            planoTemp.setStep(stepTemp);

            listPlano.add(planoTemp);
//                }
        }

        return listPlano;
    }

    /**
     *
     * @param Plano plano
     * @return retorna um objeto plano
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Plano getPorCasoTeste(Plano plano) throws SQLException, ClassNotFoundException {

        String casoTeste = plano.getCasoTeste();
        String sql = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO AND caso_teste like '%" + casoTeste + "%' ORDER BY ID ASC;";
        List<Step> listStep = new ArrayList<Step>();

        PreparedStatement ps = conn.prepareStatement(sql);
//                  ps.setString(1, plano.getCasoTeste());

        ResultSet resultSet = ps.executeQuery();
        Plano planoTemp = new Plano();
        while (resultSet.next()) {

            Step stepTemp = new Step();
            planoTemp.setId(resultSet.getInt("ID"));
            planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
            planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
            planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
            planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
            planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
            planoTemp.setRequisito(resultSet.getString("REQUISITO"));
            planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
            planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
            planoTemp.setCadeia(resultSet.getString("CADEIA"));
            planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
            planoTemp.setProduto(resultSet.getString("PRODUTO"));
            planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
            planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
            planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
            planoTemp.setType(resultSet.getString("TP_TYPE"));
            planoTemp.setTrg(resultSet.getString("TRG"));
            planoTemp.setSubject(resultSet.getString("SUBJECT"));
            planoTemp.setCriacaoAlteracao(resultSet.getString("CRIACAO_ALTERACAO"));
            planoTemp.setNomeArquivo(resultSet.getString("NOME_ARQUIVO"));
            planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));

            stepTemp.setId(resultSet.getInt("ID_STEP"));
            stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
            stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
            stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
            stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));

            planoTemp.setStep(stepTemp);
        }

        ps.close();

        return planoTemp;
    }

    public Plano verificaSeCtExiste(Plano plano) throws SQLException {
        String casoTeste = plano.getCasoTeste();
        String sql = "SELECT * FROM TB_PLANOS, TB_STEPS WHERE caso_teste like '" + casoTeste + "' ORDER BY ID ASC;";
        List<Step> listStep = new ArrayList<Step>();

        PreparedStatement ps = conn.prepareStatement(sql);
//                  ps.setString(1, plano.getCasoTeste());

        ResultSet resultSet = ps.executeQuery();
        Plano planoTemp = new Plano();
        while (resultSet.next()) {

            Step stepTemp = new Step();
            planoTemp.setId(resultSet.getInt("ID"));
            planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
            planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
            planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
            planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
            planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
            planoTemp.setRequisito(resultSet.getString("REQUISITO"));
            planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
            planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
            planoTemp.setCadeia(resultSet.getString("CADEIA"));
            planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
            planoTemp.setProduto(resultSet.getString("PRODUTO"));
            planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
            planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
            planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
            planoTemp.setType(resultSet.getString("TP_TYPE"));
            planoTemp.setTrg(resultSet.getString("TRG"));
            planoTemp.setSubject(resultSet.getString("SUBJECT"));
            planoTemp.setCriacaoAlteracao(resultSet.getString("CRIACAO_ALTERACAO"));
            planoTemp.setNomeArquivo(resultSet.getString("NOME_ARQUIVO"));
            planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));

            stepTemp.setId(resultSet.getInt("ID_STEP"));
            stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
            stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
            stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
            stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));

            planoTemp.setStep(stepTemp);
        }

        ps.close();

        return planoTemp;
    }

    public List<Plano> selectPlanoPorCondicao(Plano plano) throws SQLException, ClassNotFoundException {
        List<Plano> listPlano = new ArrayList<Plano>();
        try {
            stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(SELECT_PLANO_POR_CONDICAO);

            ps.setInt(1, plano.getId());
            ps.setString(2, plano.getFuncionalidade());
            ps.setString(3, plano.getSistemaMaster());
            ps.setString(4, plano.getSistemasEnvolvidos());
            ps.setString(5, plano.getFornecedor());
            ps.setString(6, plano.getTpRequisito());
            ps.setString(7, plano.getRequisito());
            ps.setString(8, plano.getCenarioTeste());
            ps.setString(9, plano.getCasoTeste());
            ps.setString(10, plano.getDescCasoTeste());
            ps.setString(11, plano.getCadeia());
            ps.setString(12, plano.getSegmento());
            ps.setString(13, plano.getProduto());
            ps.setString(14, plano.getCenarioIntegrado());
            ps.setInt(15, plano.getQtdSistemas());
            ps.setString(16, plano.getCenarioAutomatizavel());
            ps.setString(17, plano.getType());
            ps.setString(18, plano.getTrg());
            ps.setString(19, plano.getSubject());
            ps.setString(20, plano.getCriacaoAlteracao());
            ps.setString(21, plano.getNomeArquivo());
            ps.setInt(22, plano.getStep().getId());
            ps.setString(23, plano.getStep().getNomeStep());
            ps.setInt(24, plano.getStep().getIdPlano());
            ps.setString(25, plano.getStep().getDescStep());
            ps.setString(26, plano.getStep().getResultadoStep());

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Plano planoTemp = new Plano();
                Step stepTemp = new Step();
                planoTemp.setId(resultSet.getInt("ID"));
                planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
                planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
                planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
                planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
                planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
                planoTemp.setRequisito(resultSet.getString("REQUISITO"));
                planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
                planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
                planoTemp.setCadeia(resultSet.getString("CADEIA"));
                planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
                planoTemp.setProduto(resultSet.getString("PRODUTO"));
                planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
                planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
                planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
                planoTemp.setType(resultSet.getString("TP_TYPE"));
                planoTemp.setTrg(resultSet.getString("TRG"));
                planoTemp.setSubject(resultSet.getString("SUBJECT"));
                planoTemp.setCriacaoAlteracao(resultSet.getNString("CRIACAO_ALTERACAO"));
                planoTemp.setNomeArquivo(resultSet.getNString("NOME_ARQUIVO"));
                planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));
                stepTemp.setId(resultSet.getInt("ID_STEP"));
                stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
                stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
                stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
                stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
                planoTemp.setStep(stepTemp);

                listPlano.add(planoTemp);
            }

        } catch (SQLException e) {

        }
        return listPlano;
    }

    public List<Plano> selectPlanoPorId(Plano plano) throws SQLException, ClassNotFoundException {
        List<Plano> listPlano = new ArrayList<Plano>();
        try {
            stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(SELECT_PLANO_POR_ID);

            ps.setInt(1, plano.getId());

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Plano planoTemp = new Plano();
                Step stepTemp = new Step();
                planoTemp.setId(resultSet.getInt("ID"));
                planoTemp.setFuncionalidade(resultSet.getString("FUNCIONALIDADE_SERVICO"));
                planoTemp.setSistemaMaster(resultSet.getString("SISTEMA_MASTER"));
                planoTemp.setSistemasEnvolvidos(resultSet.getString("SISTEMAS_ENVOLVIDOS"));
                planoTemp.setFornecedor(resultSet.getString("FORNECEDOR"));
                planoTemp.setTpRequisito(resultSet.getString("TP_REQUISITO"));
                planoTemp.setRequisito(resultSet.getString("REQUISITO"));
                planoTemp.setCenarioTeste(resultSet.getString("CENARIO_TESTE"));
                planoTemp.setCasoTeste(resultSet.getString("CASO_TESTE"));
                planoTemp.setCadeia(resultSet.getString("CADEIA"));
                planoTemp.setSegmento(resultSet.getString("SEGMENTO"));
                planoTemp.setProduto(resultSet.getString("PRODUTO"));
                planoTemp.setCenarioIntegrado(resultSet.getString("CENARIO_INTEGRADO"));
                planoTemp.setQtdSistemas(resultSet.getInt("QTD_SISTEMAS"));
                planoTemp.setCenarioAutomatizavel(resultSet.getString("CENARIO_AUTOMATIZAVEL"));
                planoTemp.setType(resultSet.getString("TP_TYPE"));
                planoTemp.setTrg(resultSet.getString("TRG"));
                planoTemp.setSubject(resultSet.getString("SUBJECT"));
                planoTemp.setCriacaoAlteracao(resultSet.getString("CRIACAO_ALTERACAO"));
                planoTemp.setNomeArquivo(resultSet.getString("NOME_ARQUIVO"));
                planoTemp.setDescCasoTeste(resultSet.getString("DESCRICAO_CASO_TESTE"));
                stepTemp.setId(resultSet.getInt("ID_STEP"));
                stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
                stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
                stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
                stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));
                planoTemp.setStep(stepTemp);

                listPlano.add(planoTemp);
            }
            ps.close();

        } catch (SQLException e) {

        }

        return listPlano;
    }

    public int getIdPlanoBanco(Plano plano) throws SQLException, ClassNotFoundException {

        int id = 0;

        try {
            stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(SELECT_ID_PLANO);
            ps.setString(1, plano.getCasoTeste());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public boolean updatePlano(Plano plano) {
        try {
            PreparedStatement ps = conn.prepareStatement(UPDATE_TB_PLANOS);

            ps.setString(1, plano.getFuncionalidade());
            ps.setString(2, plano.getSistemaMaster());
            ps.setString(3, plano.getSistemasEnvolvidos());
            ps.setString(4, plano.getFornecedor());
            ps.setString(5, plano.getTpRequisito());
            ps.setString(6, plano.getRequisito());
            ps.setString(7, plano.getCenarioTeste());
            ps.setString(8, plano.getCasoTeste());
            ps.setString(9, plano.getDescCasoTeste());
            ps.setString(10, plano.getCadeia());
            ps.setString(11, plano.getSegmento());
            ps.setString(12, plano.getProduto());
            ps.setString(13, plano.getCenarioIntegrado());
            ps.setInt(14, plano.getQtdSistemas());
            ps.setString(15, plano.getCenarioAutomatizavel());
            ps.setString(16, plano.getType());
            ps.setString(17, plano.getTrg());
            ps.setString(18, plano.getSubject());
            ps.setString(19, plano.getCriacaoAlteracao());
            ps.setString(20, plano.getNomeArquivo());
            ps.setInt(21, plano.getId());

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean updateStep(Step step) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(UPDATE_TB_STEPS);

        ps.setString(1, step.getNomeStep());
        ps.setString(2, step.getDescStep());
        ps.setString(3, step.getResultadoStep());
        ps.setInt(4, step.getIdPlano());
        ps.setInt(5, step.getId());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    public boolean insertStep(Plano plano) throws ClassNotFoundException, SQLException {

        stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(INSERT_STEP);
        ps.setString(1, plano.getStep().getNomeStep());
        ps.setString(2, plano.getStep().getDescStep());
        ps.setString(3, plano.getStep().getResultadoStep());
        ps.setInt(4, plano.getStep().getIdPlano());

        retorno = ps.execute();

        ps.close();

        return retorno;

    }

    public void insertStepAdicionado(Step s) throws ClassNotFoundException, SQLException {

        stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(INSERT_STEP);
        ps.setString(1, s.getNomeStep());
        ps.setString(2, s.getDescStep());
        ps.setString(3, s.getResultadoStep());
        ps.setInt(4, s.getIdPlano());

        retorno = ps.execute();

        ps.close();
    }

    public boolean deleteStep(Plano plano) throws SQLException, ClassNotFoundException {

        stm = getConexao();
        PreparedStatement ps = conn.prepareStatement(DELETE_STEP);
        ps.setInt(1, plano.getId());
//                ps.setInt(2, plano.getStep().getIdPlano());

        retorno = ps.execute();
        ps.close();

        return retorno;
    }

    public void deleteStepExcluido(List<Step> listStep) throws SQLException, ClassNotFoundException {

        String delete = "DELETE FROM TB_STEPS WHERE ID_PLANO = ? AND NOT ID_STEP IN ";
        String stepExcluidos = "(";
        String DELETE_STEP_EXCLUIDO = "";

        for (int i = 0; i < listStep.size(); i++) {
            if (listStep.size() - i == 1) {
                stepExcluidos = stepExcluidos + listStep.get(i).getId();
            } else {
                stepExcluidos = stepExcluidos + listStep.get(i).getId() + ",";
            }

        }

        stepExcluidos = stepExcluidos + ")";

        DELETE_STEP_EXCLUIDO = delete + stepExcluidos;

        PreparedStatement ps = conn.prepareStatement(DELETE_STEP_EXCLUIDO);
//            System.out.println("ID_PLANO: "+step.getIdPlano());

        ps.setInt(1, listStep.get(0).getIdPlano());
//                ps.setInt(2, plano.getStep().getIdPlano());

        ps.execute();

        ps.close();

    }

    public int getStepMaiorId(Plano p) throws SQLException, ClassNotFoundException {
        int id = 0;
        String sql = "select max(id_step) as id_step from tb_steps where id_plano = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, p.getId());

        ResultSet resultSet = ps.executeQuery();

        id = resultSet.getInt("id_step");

        ps.close();

        return id;

    }

    public List<Step> getStepPorPlano(Plano plano) throws SQLException, ClassNotFoundException {

        String casoTeste = plano.getCasoTeste();
        String sql = "SELECT S.ID_STEP,S.ID_PLANO, S.NOME_STEP, S.DESC_STEP, S.RESULTADO_ESPERADO FROM TB_STEPS S, TB_PLANOS P WHERE S.ID_PLANO = P.ID AND P.CASO_TESTE LIKE  '" + casoTeste + "' ORDER BY CAST(SUBSTR(NOME_STEP,6,7) as INTEGER) ASC";
        List<Step> listStep = new ArrayList<Step>();

        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setInt(1, plano.getId());

        ResultSet resultSet = ps.executeQuery();
        Plano planoTemp = new Plano();
        while (resultSet.next()) {

            Step stepTemp = new Step();
            stepTemp.setId(resultSet.getInt("ID_STEP"));
            stepTemp.setNomeStep(resultSet.getString("NOME_STEP"));
            stepTemp.setIdPlano(resultSet.getInt("ID_PLANO"));
            stepTemp.setDescStep(resultSet.getString("DESC_STEP"));
            stepTemp.setResultadoStep(resultSet.getString("RESULTADO_ESPERADO"));

            planoTemp.setStep(stepTemp);
            listStep.add(stepTemp);
        }
        ps.close();
        return listStep;
    }

    public boolean insertTabelaConf(String tabela, String coluna, String valor) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO " + tabela + " ( " + coluna + " ) VALUES( ? )";
        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, valor);

            retorno = ps.execute();
            ps.close();
            return retorno;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }

    }

    public boolean deletaTabelaConf(String tabela) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM " + tabela;

        PreparedStatement ps = conn.prepareStatement(sql);

        retorno = ps.execute();
        ps.close();
        return retorno;

    }

    public List<String> selectConfALL(String tabela) throws SQLException, ClassNotFoundException {
        List<String> listConf = new ArrayList<String>();
        String sql = "SELECT * FROM " + tabela;

        try {
            stm = getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                listConf.add(resultSet.getString(2));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listConf;
    }

    public void delete() {

    }

}
