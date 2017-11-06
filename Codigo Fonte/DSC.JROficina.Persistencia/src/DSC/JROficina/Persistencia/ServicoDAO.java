/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Entidade;
import DSC.JROficina.Aplicacao.Servico;
import DSC.JROficina.Aplicacao.ServicoRepositorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class ServicoDAO extends DAOGenerico<Servico> implements ServicoRepositorio{

    
    public ServicoDAO() throws ClassNotFoundException, SQLException {
        super();
    }
    @Override
    protected String getConsultaInsert() {
        return "call inserirServico(?,?)"; //valor, desc
    }

    @Override
    protected String getConsultaUpdate() {
        return "call  update_servico(?, ?, ?)"; //valor, desc, id
    }

    @Override
    protected String getConsultaDelete() {
        return "call delete_servico(?)"; //id
    }

    @Override
    protected String getConsultaAbrir() {
        return "select servicos.iditem_fk, servicos.descricao, itemfinanceiro.valorunitario, itemfinanceiro.iditem_pk from servicos join "
                + "itemfinanceiro on servicos.iditem_fk = itemfinanceiro.iditem_pk where servicos.iditem_fk = ?";
    }

    @Override
    protected String getConsultaBuscar() {
        return "select servicos.iditem_fk, servicos.descricao, itemfinanceiro.valorunitario, itemfinanceiro.iditem_pk from servicos join "
                + "itemfinanceiro on servicos.iditem_fk = itemfinanceiro.iditem_pk ";
    }

    @Override
    protected void setBuscaFiltros(Servico filtro) {
        if(filtro.getId() > 0)
            this.adicionaFiltro("servico.iditem_fk", filtro.getId());
        
        if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())
            this.adicionaFiltro("servico.descricao", filtro.getDescricao());
        
        if(filtro.getValor() > 0)
            this.adicionaFiltro("servico.valorunitario", filtro.getValor());
    }

    @Override
    protected void setParametros(PreparedStatement sql, Servico obj) {
        try{
            sql.setFloat(1, obj.getValor());
            sql.setString(2, obj.getDescricao());
            
            if(obj.getId() > 0)
                sql.setInt(3, obj.getId());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Servico setDados(ResultSet resultado) {
        Servico obj = new Servico();
        try{   
            obj.setId(resultado.getInt("servicos.iditem_fk"));
            obj.setDescricao(resultado.getString("servicos.descricao"));
            obj.setValor(resultado.getFloat("itemfinanceiro.valorunitario"));
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return obj;
    }

    @Override
    protected String getConsultaId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    
}
