/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Cliente;
import DSC.JROficina.Aplicacao.ClienteRepositorio;
import DSC.JROficina.Aplicacao.TipoCliente;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class ClienteDAO extends DAOGenerico<Cliente> implements ClienteRepositorio{

    public ClienteDAO() throws ClassNotFoundException, SQLException {
        super();
        Class.forName("com.mysql.jdbc.Driver");
        
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/oficina","root","");
    }
    
    @Override
    protected String getConsultaInsert() {
        return "call inserir_cliente(?,?,?,?,?)"; //nome, telefone, endereco, cpf, tipo
    }

    @Override
    protected String getConsultaUpdate() {
        return "call update_cliente(?,?,?,?,?,?)"; //nome, telefone, endereco, cpf, tipo, id
    }

    @Override
    protected String getConsultaDelete() {
        return "call apaga_cliente(?)"; //id
    }

    @Override
    protected String getConsultaAbrir() {
        return "select pessoas.idpessoa_pk, pessoas.nome, clientes.tipo, clientes.cpf, pessoas.endereco, pessoas.telefone"
                + " from pessoas join clientes on pessoas.idpessoa_pk = clientes.idpessoa_fk where pessoas.idpessoa_pk = ?";
    }

    @Override
    protected String getConsultaBuscar() {
        return "select pessoas.idpessoa_pk, pessoas.nome, clientes.tipo, clientes.cpf, pessoas.endereco, pessoas.telefone"
                + " from pessoas join clientes on pessoas.idpessoa_pk = clientes.idpessoa_fk ";
    }

    @Override
    protected void setBuscaFiltros(Cliente filtro) {
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
        
        if(filtro.getCpf_cnpj() != null && !filtro.getCpf_cnpj().isEmpty()){
            this.adicionaFiltro("cpf", filtro.getCpf_cnpj());
            filtro.setCpf_cnpj(null);
        }
        
        if(filtro.getTipo() != null)
            this.adicionaFiltro("tipo", filtro.getTipo().getId());
        
    }

    @Override
    protected void setParametros(PreparedStatement sql, Cliente obj) {
        try{
            sql.setString(1, obj.getNome());
            sql.setString(2, obj.getTelefone());
            sql.setString(3, obj.getEndereco());
            sql.setString(4, obj.getCpf_cnpj());
            sql.setInt(5,obj.getTipo().getId());
            
            if(obj.getId() > 0)
                sql.setInt(6, obj.getId());
      
        }catch(SQLException ex){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    protected Cliente setDados(ResultSet resultado) {
        try{
            Cliente obj = new Cliente();
            obj.setId(resultado.getInt("idpessoa_pk"));
            obj.setNome(resultado.getString("nome"));
            obj.setCpf_cnpj(resultado.getString("cpf"));
            obj.setEndereco(resultado.getString("endereco"));
            obj.setTelefone(resultado.getString("telefone"));
            obj.setTipo(TipoCliente.Abrir( resultado.getInt("tipo") ));
            
        
            return obj;
        }catch(Exception ex){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    protected String getConsultaId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
