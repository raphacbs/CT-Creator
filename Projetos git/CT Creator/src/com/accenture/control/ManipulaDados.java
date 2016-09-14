/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.Plano;
import com.accenture.bean.Step;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author raphael.da.silva
 */
public class ManipulaDados {
    private String user = "root";
    private String pass = "root";
    private boolean retorno = false;
    
    private final String SELECT_PLANO_STEP = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = ? AND P.ID = S.ID_PLANO"; 
    private final String SELECT_ALL_PLANOS = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO";
    private final String INSERT_PLANO = "insert into root.TB_PLANOS(FUNCIONALIDADE_SERVICO, SISTEMA_MASTER, SISTEMAS_ENVOLVIDOS,FORNECEDOR,TP_REQUISITO,REQUISITO,CENARIO_TESTE,CASO_TESTE, DESCRICAO_CASO_TESTE, "
                + "CADEIA, SEGMENTO, PRODUTO, CENARIO_INTEGRADO, QTD_SISTEMAS, CENARIO_AUTOMATIZAVEL, TYPE , TRG , SUBJECT, CRIACAO_ALTERACAO, NOME_ARQUIVO) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String INSERT_STEP = "insert into root.TB_STEPS(NOME_STEP, DESC_STEP, RESULTADO_ESPERADO, ID_PLANO) "
                + "VALUES(?, ?, ?, ?)";
    private final String DELETE_PLANO_STEP = "DELETE FROM TB_PLANOS WHERE ID = ?; DELETE FROM TB_STEPS WHERE ID_PLANO = ?;";
    private final String DELETE_STEP = "DELETE FROM TB_STEPS WHERE ID_STEP = ? AND ID_PLANO = ? ";
    private final String SELECT_ID_PLANO = "SELECT ID FROM TB_PLANOS where CASO_TESTE = ? ";
    
    
    public Connection getConnection() throws SQLException { 
        Connection con = null;
        String dbURL2 = "jdbc:derby://localhost:1527/fastPlano";
        con = DriverManager .getConnection(dbURL2, user, pass); 
        return con; 
    }
    
    public void closeConnnection(Connection con) { 
	try { 
		con.close(); 
		} catch (SQLException e) {
			e.printStackTrace(); 
			} 
	}

    public boolean insertPlano(Plano plano){
        Connection con = null;
        
        try{
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_PLANO);
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
            
        }catch(SQLException e){
            
            
            return retorno;
        }finally{
            closeConnnection(con);
        }
        
        return retorno;
    }
    
    public boolean deletePlano(Plano plano) throws SQLException{
        Connection con = null;
        try{
            PreparedStatement ps = con.prepareStatement(DELETE_PLANO_STEP);
            ps.setInt(1, plano.getId());
            ps.setInt(2, plano.getId());
            
            retorno = ps.execute();
        }catch(SQLException e){
            
        }finally{
            closeConnnection(con);
        }
        
        return retorno;
    }
    
    public boolean insertStep(Step step){
        Connection con = null;
        
        try{
            con = getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_STEP);
             ps.setString(1, step.getNomeStep());
             ps.setString(2, step.getDescStep());
             ps.setString(3, step.getResultadoStep());
             ps.setInt(4, step.getIdPlano());
             
             retorno = ps.execute();
             
             
        }catch(SQLException e){
             return retorno;
        }finally{
            closeConnnection(con);
        }
        return retorno;
    }
    
    public int getIdPlanoBanco(Plano plano) throws SQLException{
        Connection con = null;
        int id = 0;
        
        try{
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_ID_PLANO);
            ps.setString(1,plano.getCasoTeste());
            ResultSet resultSet = ps.executeQuery();
            
            while(resultSet.next()) {
                id = resultSet.getInt("ID");
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnnection(con);
        }
        
        return id;
    }
   
    
    

}