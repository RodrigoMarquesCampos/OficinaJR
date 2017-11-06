/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Compra;
import DSC.JROficina.Aplicacao.CompraRepositorio;
import DSC.JROficina.Aplicacao.Peca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;




/**
 *
 * @author Rodrigo
 */
public class CompraDAO extends DAOGenerico<Compra> implements CompraRepositorio{
    
    PreparedStatement sql;
    SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");

    public CompraDAO() throws ClassNotFoundException, SQLException {
        super();
        
        Class.forName("com.mysql.jdbc.Driver");
        
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/oficina","root","");
    }
    
    

    @Override
    protected String getConsultaInsert() {
        return "insert into transacaofinanceira(idpessoa_fk, tipo, data, status, parcelas, valor_total, valor_pago, vencimento) values(?,?,?,?,?,?,?,?)";
    }

    @Override
    protected String getConsultaUpdate() {
        return "update transacaofinanceira set idpessoa_fk = ?, tipo = ?, data = ?, status = ?, parcelas = ?, valor_total = ?, valor_pago = ?,"
                + " vencimento = ? where idtran_pk = ?";
    }

    @Override
    protected String getConsultaDelete() {
        return "delete from transacaofinanceira where idtran_pk = ?";
    }
    
    

    @Override
    protected String getConsultaAbrir() {
         return "select transacaofinanceira.idtran_pk, transacaofinanceira.idpessoa_fk, transacaofinanceira.tipo,"
            + " transacaofinanceira.data, transacaofinanceira.status, transacaofinanceira.parcelas, transacaofinanceira.valor_total,"
                + " transacaofinanceira.valor_pago, transacaofinanceira.vencimento from transacaofinanceira where id =?";
    }

    @Override
    protected String getConsultaBuscar() {
        return "select transacaofinanceira.idtran_pk, transacaofinanceira.idpessoa_fk, transacaofinanceira.tipo,"
            + " transacaofinanceira.data, transacaofinanceira.status, transacaofinanceira.parcelas, transacaofinanceira.valor_total,"
                + " transacaofinanceira.valor_pago, transacaofinanceira.vencimento from transacaofinanceira";
    }

    @Override
    protected void setBuscaFiltros(Compra filtro) {
        if(filtro.getId() > 0)
           this.adicionaFiltro("transacaofinanceira.idtran_pk", filtro.getId());
        
        if(filtro.getValor() > 0)
           this.adicionaFiltro("tran_item.valor_toal", filtro.getValor());
        
        if(filtro.getData() != null)
           this.adicionaFiltro("transacaofinanceira.data", new java.sql.Date(filtro.getData().getTime()));
        
        if(filtro.getAliado() != null)
           this.adicionaFiltro("transacaofinanceira.idpessoa_fk", filtro.getAliado().getId());

        if(filtro.getVencimento() != null)
           this.adicionaFiltro("transacaofinanceira.vencimento", filtro.getVencimento());
        
        if(filtro.getStatus() != null)
            this.adicionaFiltro("transacaofinanceira.status", filtro.getStatus());
        
        this.adicionaFiltro("transacaofinanceira.tipo", filtro.getTipo());
    }

    @Override
    protected void setParametros(PreparedStatement sql, Compra obj) {
       try{
            sql.setInt(1, obj.getAliado().getId());
            sql.setInt(2, obj.getTipo());
            sql.setDate(3, new java.sql.Date(obj.getData().getTime())); 
            sql.setInt(4, obj.getStatus().getId());
            sql.setInt(5, obj.getParcelas());
            sql.setDouble(6, (Double) obj.getValor());
            sql.setFloat(7, (float) obj.getValor_pago());
            sql.setDate(8, new java.sql.Date(obj.getVencimento().getTime()));
            
            if(obj.getId() > 0)
                sql.setInt(9, obj.getId());
      
        }catch(SQLException ex){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Compra setDados(ResultSet resultado) {
        try{   
            FornecedorDAO fornecedordao = new FornecedorDAO();
            Compra obj = new Compra();
            obj.setId(resultado.getInt("idtran_pk"));
            obj.setAliado(fornecedordao.Abrir(resultado.getInt("idpessoa_fk")));
            obj.setData(resultado.getDate("data"));
            obj.setParcelas(resultado.getInt("parcelas"));
            obj.setValor(resultado.getFloat("valor_total"));
            obj.setValor_pago(resultado.getFloat("valor_pago"));
            obj.setValor_parc((float) ((obj.getValor() - obj.getValor_pago()) / obj.getParcelas()));
            obj.setVencimento(resultado.getDate("vencimento"));
            obj.setTipo(resultado.getInt("tipo"));
            return obj;
        }catch(Exception ex){
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean Salvar(Compra obj) {
       List<Peca> p = obj.getPecas();
       
       
        try{ 
            PecaDAO pecadao = new PecaDAO();
            Compra h = this.Abrir(obj.getId());
            
            if(obj.getId() == 0){
               sql = conexao.prepareStatement(getConsultaInsert());
                setParametros(sql, obj);
                
                if(sql.executeUpdate() <= 0)
                    return false;
                
                sql = conexao.prepareStatement("insert into tran_item(idtran_fk, iditem_fk, quantidade) values(?,?,?)");               
               
                int x = BuscarUltimoId();
                for(Peca c : p){
                    obj.setId(x);
                    this.setParametros(sql, obj, c); 
                    if(sql.executeUpdate() <= 0) 
                        return false;
                }

                for(Peca c : p){
                    Peca peca = pecadao.Abrir(c.getId());
                    peca.setFornecedor(c.getFornecedor());
                    peca.setQtde(peca.getQtde() + c.getQtde());
                    sql = conexao.prepareStatement(pecadao.getConsultaUpdate());
                    pecadao.setParametros(sql, peca);               
                    peca.setFornecedor(c.getFornecedor());

                    if(sql.executeUpdate() <= 0) 
                        return false;
                    }
                
            } else{
                PreparedStatement sql = conexao.prepareStatement(getConsultaUpdate());
                setParametros(sql, obj);

                if(sql.executeUpdate() <= 0)
                     return false;

                sql = conexao.prepareStatement("delete from tran_item where idtran_fk = ?");
                sql.setInt(1, obj.getId());

                if(sql.executeUpdate() <= 0)
                     return false;


                sql = conexao.prepareStatement("insert into tran_item(idtran_fk, iditem_fk, quantidade) values(?,?,?)");

                for(Peca c : p){
                   this.setParametros(sql, obj, c);
                   if(sql.executeUpdate() <= 0) 
                       return false;
                } 
            
           
            for(Peca c : p){
                Peca peca = pecadao.Abrir(c.getId());
                for(Peca t : h.getPecas()){
                    if(t.getId() == c.getId()){
                       peca.setQtde((peca.getQtde() - t.getQtde()) + c.getQtde());
                    }
                }
                peca.setFornecedor(c.getFornecedor());
                
                sql = conexao.prepareStatement(pecadao.getConsultaUpdate());
                pecadao.setParametros(sql, peca);

                if(sql.executeUpdate() <= 0) 
                    return false;
                }
            }
           
            return true;
        }catch(Exception ex){
            return false;
        }
      }
        
    
    @Override
    public List<Compra> Buscar(Compra filtro) {
        
        List<Compra> lista = new ArrayList<>();
        List<Integer> tran_item = new ArrayList<>();
        List<Peca> pecas = new ArrayList<>();
        try{
            
            if(filtro != null)
                this.setBuscaFiltros(filtro);
            
            String sqlfinal = this.getConsultaBuscar();
            
            if(!where.isEmpty())
                sqlfinal += " where " + where;
            
            PreparedStatement sql =  conexao.prepareStatement(sqlfinal);
            
            ResultSet resultado = sql.executeQuery();
            
            while(resultado.next())
                lista.add(this.setDados(resultado));
            
            for(Compra p : lista){
                String consulta = "select iditem_fk, idtran_fk, quantidade from tran_item where idtran_fk = "+ String.valueOf(p.getId())+" and quantidade != 0";
                sql =  conexao.prepareStatement(consulta);
                resultado = sql.executeQuery();
                while(resultado.next()){
                     PecaDAO pecadao;
                    try {
                        pecadao = new PecaDAO();
                        Peca pe = pecadao.Abrir(resultado.getInt("iditem_fk"));
                        pe.setQtde(resultado.getInt("quantidade"));
                        pecas.add(pe); 
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      
                }
                p.setPecas(pecas);
                pecas = new ArrayList<>();
            }
            
            this.where = "";
            
            return lista;
        
        } catch(SQLException ex){
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public Compra Abrir(int id) {
         Compra p = new Compra();
         p.setId(id);
         List<Compra> compra = this.Buscar(p);
         if(compra != null){
             for(Compra x : compra)
                 return x;
         }else
             return null;
         
        return null;
    }
    
    protected void setParametros(PreparedStatement sql, Compra obj, Peca p) {
       
        try{
            sql.setInt(1, obj.getId());
            sql.setInt(2, p.getId());
            sql.setInt(3, p.getQtde());
           
        }catch(SQLException ex){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    
    public boolean Apagar(Compra obj){
           try{
            PreparedStatement sql = conexao.prepareStatement("delete from tran_item where idtran_fk = ?");
            
            sql.setInt(1, obj.getId());
            
            if((sql.executeUpdate() <= 0) && obj.getPecas() == null)
                return false;
       
            if(!super.Apagar(obj))
                return false;
        }catch(Exception ex){
            return false;    
        }
        obj = null;
        return true;
    }
        
        
    
    @Override
    protected String getConsultaId() {
        return "select max(idtran_pk) as idtran_pk from transacaofinanceira";
    } 
    
}
    
    
    
