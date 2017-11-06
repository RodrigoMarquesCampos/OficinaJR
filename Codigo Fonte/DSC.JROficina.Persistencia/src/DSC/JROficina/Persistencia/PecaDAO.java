/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Fornecedor;
import DSC.JROficina.Aplicacao.Peca;
import DSC.JROficina.Aplicacao.PecaRepositorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class PecaDAO extends DAOGenerico<Peca> implements PecaRepositorio{
    
    public PecaDAO() throws ClassNotFoundException, SQLException{
        super();
    } 
    
    @Override
    protected String getConsultaInsert() {
        return "call inserirPeca(?,?,?,?,?,?,?)";  // nome, marca, valor_compra, valor_venda, desc, qtde, fornecedor
    }

    @Override
    protected String getConsultaUpdate() {
        return "call updatePeca(?,?,?,?,?,?,?,?)"; // nome, marca, valor_compra, valor_venda, desc, qtde, fornecedor, id
    }

    @Override
    protected String getConsultaDelete() {
        return "call apagaPeca(?)"; //id
    }

    @Override
    protected String getConsultaAbrir() {
        return "select pecas.iditem_fk, pecas.nome, pecas.marca, pecas.moto, pecas.valor_compra, "
            + "itemfinanceiro.valorunitario, pessoas.nome, pessoas.telefone, pessoas.endereco, "
            + "fornecedor.idpessoa_fk, fornecedor.cnpj, peca_tem_fornecedor.qtde from itemfinanceiro join pecas on "
            + "itemfinanceiro.iditem_pk = pecas.iditem_fk join peca_tem_fornecedor on "
            + "pecas.iditem_fk = peca_tem_fornecedor.peca_fk join pessoas on "
            + "peca_tem_fornecedor.fornecedor_fk = pessoas.idpessoa_pk join fornecedor on "
            + "pessoas.idpessoa_pk = fornecedor.idpessoa_fk where pecas.iditem_fk = ?"; 
    }

    @Override
    protected String getConsultaBuscar() {
       return "select pecas.iditem_fk, pecas.nome, pecas.marca, pecas.moto, pecas.valor_compra, "
            + "itemfinanceiro.valorunitario, pessoas.nome, pessoas.telefone, pessoas.endereco, "
            + "fornecedor.idpessoa_fk, fornecedor.cnpj, peca_tem_fornecedor.qtde from itemfinanceiro"
            + " join pecas on itemfinanceiro.iditem_pk = pecas.iditem_fk join peca_tem_fornecedor on "
            + "pecas.iditem_fk = peca_tem_fornecedor.peca_fk join pessoas on "
            + "peca_tem_fornecedor.fornecedor_fk = pessoas.idpessoa_pk join fornecedor on "
            + "pessoas.idpessoa_pk = fornecedor.idpessoa_fk"; 
    }

    @Override
    protected void setBuscaFiltros(Peca filtro) {
        
        if(filtro.getId() > 0)
            this.adicionaFiltro("iditem_fk", filtro.getId());
        
        if(filtro.getDesc() != null && !filtro.getDesc().isEmpty())
            this.adicionaFiltro("moto", filtro.getDesc());
        
        if(filtro.getMarca() != null && !filtro.getMarca().isEmpty())
            this.adicionaFiltro("marca",filtro.getMarca());
        
        if(filtro.getValor_compra() > 0 )
            this.adicionaFiltro("valor_compra", filtro.getValor_compra());
        
        if(filtro.getValor_venda() > 0 )
            this.adicionaFiltro("itemfinanceiro.valorunitario", filtro.getValor_venda());
        
        if(filtro.getFornecedor() != null){
           Fornecedor f = filtro.getFornecedor();
        
             if(f.getNome() != null && !f.getNome().isEmpty())
                this.adicionaFiltro("pessoas.nome", f.getNome());
        }
        
        if(filtro.getNome() != null && !filtro.getNome().isEmpty())
            this.adicionaFiltro("pecas.nome",filtro.getNome());
        
        if(filtro.getQtde() > -1)
            this.adicionaFiltro("qtde", filtro.getQtde());
    
    }
    

    @Override
    protected void setParametros(PreparedStatement sql, Peca obj) {
       try{
          sql.setString(1, obj.getNome()); 
          sql.setString(2, obj.getMarca());
          sql.setFloat(3, obj.getValor_compra());
          sql.setFloat(4, obj.getValor_venda());
          sql.setString(5, obj.getDesc());
          sql.setInt(6, obj.getQtde());
          sql.setString(7, obj.getFornecedor().getNome());
          
          if(obj.getId() > 0)
              sql.setInt(8, obj.getId());
          
          
       }catch(SQLException ex){
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
}

    @Override
    protected Peca setDados(ResultSet resultado) {
       Fornecedor forn = new Fornecedor();
        
        try{
            Peca obj = new Peca();
            obj.setId(resultado.getInt("iditem_fk"));
            obj.setNome(resultado.getString("nome"));
            obj.setMarca(resultado.getString("marca"));
            obj.setDesc(resultado.getString("moto"));
            obj.setValor_compra(resultado.getFloat("valor_compra"));
            obj.setValor_venda(resultado.getFloat("valor_compra"));
            obj.setQtde(resultado.getInt("qtde"));
            forn.setNome(resultado.getString("pessoas.nome"));
            obj.setFornecedor(forn);
       
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
