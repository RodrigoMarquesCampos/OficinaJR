/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Fornecedor;
import DSC.JROficina.Aplicacao.FornecedorRepositorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class FornecedorDAO extends DAOGenerico<Fornecedor> implements FornecedorRepositorio{

   public FornecedorDAO() throws ClassNotFoundException, SQLException {
       super();
   }
   
    @Override
    protected String getConsultaInsert() {
        return "call inserir_fornecedor(?,?,?,?)";
    }

    @Override
    protected String getConsultaUpdate() {
        return "call update_fornecedor(?,?,?,?,?)";
    }

    @Override
    protected String getConsultaDelete() {
        return "call apaga_fornecedor(?)";
    }

    @Override
    protected String getConsultaAbrir() {
        return "select pessoas.idpessoa_pk, pessoas.nome, fornecedor.cnpj, pessoas.endereco, pessoas.telefone "
                + "from pessoas join fornecedor on pessoas.idpessoa_pk = fornecedor.idpessoa_fk where idpessoa_pk = ?";
    }

    @Override
    protected String getConsultaBuscar() {
        return "select pessoas.idpessoa_pk, pessoas.nome, fornecedor.cnpj, pessoas.endereco, pessoas.telefone "
                + "from pessoas join fornecedor on pessoas.idpessoa_pk = fornecedor.idpessoa_fk";
    }

    
    @Override
    protected void setBuscaFiltros(Fornecedor filtro) {
          if(filtro.getId() > 0)
           this.adicionaFiltro("idpessoa_fk", filtro.getId());
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            this.adicionaFiltro("nome", filtro.getNome());
            filtro.setNome(null);
        }
        if(filtro.getEndereco() != null && !filtro.getEndereco().isEmpty()){
            this.adicionaFiltro("endereco", filtro.getEndereco());
            filtro.setEndereco(null);
        }
            
        if(filtro.getTelefone() != null && !filtro.getTelefone().isEmpty()){
            this.adicionaFiltro("telefone", filtro.getTelefone());
            filtro.setTelefone(null);
        }
        
        if(filtro.getCnpj() != null && !filtro.getCnpj().isEmpty()){
            this.adicionaFiltro("cnpj",filtro.getCnpj());
            filtro.setTelefone(null);
        }
        
    }

    @Override
    protected void setParametros(PreparedStatement sql, Fornecedor obj) {
        try{
            sql.setString(1, obj.getNome());
            sql.setString(2, obj.getTelefone());
            sql.setString(3, obj.getEndereco());
            sql.setString(4, obj.getCnpj());
            
            if(obj.getId() > 0)
                sql.setInt(5, obj.getId());
      
        }catch(SQLException ex){
            Logger.getLogger(Fornecedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Fornecedor setDados(ResultSet resultado) {
        try{
            Fornecedor obj = new Fornecedor();
            obj.setId(resultado.getInt("idpessoa_pk"));
            obj.setNome(resultado.getString("nome"));
            obj.setCnpj(resultado.getString("cnpj"));
            obj.setEndereco(resultado.getString("endereco"));
            obj.setTelefone(resultado.getString("telefone"));
            
            return obj;
        }catch(Exception ex){
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    protected String getConsultaId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
