/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.TipoUsuario;
import DSC.JROficina.Aplicacao.Usuario;
import DSC.JROficina.Aplicacao.UsuarioRepositorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class UsuarioDAO extends DAOGenerico<Usuario> implements UsuarioRepositorio{
    
     public UsuarioDAO() throws ClassNotFoundException, SQLException {
        super();
    }
    

    @Override
    protected String getConsultaInsert() {
        return "call inserirUsuario(?,?,?,?)"; //nome, login, senha, tipo
    }

    @Override
    protected String getConsultaUpdate() {
        return "call updateUsuario(?,?,?,?,?)"; //nome, login, senha, tipo, id
    }

    @Override
    protected String getConsultaDelete() {
        return "call apagaUsuario(?)";
    }

    @Override
    protected String getConsultaAbrir() {
        return "select pessoas.idpessoa_pk, pessoas.nome, usuario.login, usuario.senha, usuario.tipo from pessoas join usuario"
        + " on pessoas.idpessoa_pk = usuario.idpessoa_fk where pessoas.idpessoa_pk = ?";
    }

    @Override
    protected String getConsultaBuscar() {
        return "select pessoas.idpessoa_pk, pessoas.nome, usuario.login, usuario.senha, usuario.tipo from pessoas join usuario"
        + " on pessoas.idpessoa_pk = usuario.idpessoa_fk";
    }

    @Override
    protected void setBuscaFiltros(Usuario filtro) {
        if(filtro.getId() > 0)
           this.adicionaFiltro("idpessoa_fk", filtro.getId());
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
            this.adicionaFiltro("nome", filtro.getNome());
        }
        
        if(filtro.getLogin() != null && !filtro.getLogin().isEmpty()){
            this.adicionaFiltro("login", filtro.getLogin());
        }
        
        if(filtro.getSenha() != null && !filtro.getSenha().isEmpty()){
            this.adicionaFiltro("senha", filtro.getSenha());
        }
        
        if(filtro.getTipo() != null)
            this.adicionaFiltro("tipo", filtro.getTipo().getId());
    }

    @Override
    protected void setParametros(PreparedStatement sql, Usuario obj) {
        
        try{
            sql.setString(1, obj.getNome());
            String x = obj.getNome();
            sql.setString(2, obj.getLogin());
            sql.setString(3, obj.getSenha());
            sql.setInt(4, obj.getTipo().getId());
            
            if(obj.getId() > 0){
                sql.setInt(5, obj.getId());
            }
        }catch(SQLException ex){
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Usuario setDados(ResultSet resultado) {
        try{
            Usuario obj = new Usuario();
            obj.setNomee(resultado.getString("nome"));
            obj.setLogin(resultado.getString("login"));
            obj.setSenha(resultado.getString("senha"));
            obj.setId(resultado.getInt("idpessoa_pk"));
            obj.setTipo(TipoUsuario.Abrir(resultado.getInt("tipo")));
            
            return obj;
        }catch(Exception ex){
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    protected String getConsultaId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
