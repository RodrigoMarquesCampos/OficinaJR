/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Cliente;
import DSC.JROficina.Aplicacao.Venda;
import DSC.JROficina.Aplicacao.VendaRepositorio;
import DSC.JROficina.Aplicacao.Compra;
import DSC.JROficina.Aplicacao.CompraRepositorio;
import DSC.JROficina.Aplicacao.Peca;
import DSC.JROficina.Aplicacao.Servico;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class VendaDAO extends DAOGenerico<Venda> implements VendaRepositorio{
    
    PreparedStatement sql;
    private CompraDAO compradao;

    public VendaDAO() throws ClassNotFoundException, SQLException {
        super();
        
        Class.forName("com.mysql.jdbc.Driver");
        
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/oficina","root","");
    }

    @Override
    protected String getConsultaInsert() {
        return "insert into tran_veiculo(veiculo_fk, tran_fk) values (?,?)";
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
    protected String getConsultaId() {
        return "select max(idtran_pk) as idtran_pk from transacaofinanceira";
    }

    @Override
    protected void setBuscaFiltros(Venda filtro) {
       if(filtro.getId() > 0)
           this.adicionaFiltro("transacaofinanceira.idtran_pk", filtro.getId());
        
        if(filtro.getValor() > 0)
           this.adicionaFiltro("tran_item.valor_toal", filtro.getValor());
        
        if(filtro.getData() != null)
           this.adicionaFiltro("transacaofinanceira.data", filtro.getData());
        
        if(filtro.getAliado() != null)
           this.adicionaFiltro("transacaofinanceira.idpessoa_fk", filtro.getAliado().getId());

        if(filtro.getVencimento() != null)
           this.adicionaFiltro("transacaofinanceira.vencimento", filtro.getVencimento());
        
        if(filtro.getStatus() != null)
            this.adicionaFiltro("transacaofinanceira.status", filtro.getStatus());
        
        this.adicionaFiltro("transacaofinanceira.tipo", 2);
    }

    @Override
    protected void setParametros(PreparedStatement sql, Venda obj) {
        try {
            sql.setInt(1, obj.getVeiculo().getId());
            sql.setInt(2, obj.getId());
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    protected void setParametros(PreparedStatement sql, Compra obj, Servico p) {
       
        try{
            sql.setInt(1, obj.getId());
            sql.setInt(2, p.getId());
            sql.setInt(3, 0);
        }catch(SQLException ex){
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    protected Venda setDados(ResultSet resultado) {
        try{   
            ClienteDAO clientedao = new ClienteDAO();
            Venda obj = new Venda();
            obj.setId(resultado.getInt("idtran_pk"));
            obj.setAliado(clientedao.Abrir(resultado.getInt("idpessoa_fk")));
            obj.setData(resultado.getDate("data"));
            obj.setParcelas(resultado.getInt("parcelas"));
            obj.setValor(resultado.getFloat("valor_total"));
            obj.setValor_pago(resultado.getFloat("valor_pago"));
            obj.setValor_parc((float) ((obj.getValor() - obj.getValor_pago()) / obj.getParcelas()));
            obj.setVencimento(resultado.getDate("vencimento"));
            return obj;
        }catch(Exception ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private  Compra CarregaCompra(Venda venda){ 
        Compra compra = new Compra();
        compra.setAliado(venda.getAliado());
        compra.setData(venda.getData());
        compra.setId(venda.getId());
        compra.setParcelas(venda.getParcelas());
        compra.setPecas(venda.getPecas());
        compra.setStatus(venda.getStatus());
        compra.setTipo(2);
        compra.setValor(venda.getValor());
        compra.setValor_pago(venda.getValor_pago());
        compra.setValor_parc(venda.getValor_parc());
        compra.setVencimento(venda.getVencimento());
        return compra;
    }
         
    @Override
    public boolean Salvar(Venda obj){
        List<Peca> p = obj.getPecas();
        try{
            PecaDAO pecadao = new PecaDAO();
            if(obj.getId() == 0){
                try {
                    
                    compradao = new CompraDAO();//
                    if(!compradao.Salvar(CarregaCompra(obj)))//
                        return false;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

               sql = conexao.prepareStatement(getConsultaInsert()); //inserindo o veiculo
               
               int x = BuscarUltimoId();
               obj.setId(x);
                setParametros(sql, obj);
                
                if(sql.executeUpdate() < 0)
                    return false;
        
               sql = conexao.prepareStatement("insert into tran_item(idtran_fk, iditem_fk, quantidade) values(?,?,?)"); 
                        
                for(Servico c : obj.getServicos()){
                    this.setParametros(sql, obj, c); 
                    if(sql.executeUpdate() <= 0) 
                        return false;
                } 
                
                for(Peca c : p){
                    Peca peca = pecadao.Abrir(c.getId());
                    peca.setQtde(peca.getQtde() - (2 * c.getQtde()));
                    sql = conexao.prepareStatement(pecadao.getConsultaUpdate());
                    pecadao.setParametros(sql, peca);

                    if(sql.executeUpdate() <= 0) 
                        return false;
                }   
                
            }else{
                Venda h = this.Abrir(obj.getId());
                PreparedStatement sql = conexao.prepareStatement(getConsultaUpdate());
                sql.setInt(1, obj.getAliado().getId());
                sql.setInt(2, obj.getTipo());
                sql.setDate(3, new java.sql.Date(obj.getData().getTime())); 
                sql.setInt(4, obj.getStatus().getId());
                sql.setInt(5, obj.getParcelas());
                sql.setDouble(6, obj.getValor());
                sql.setFloat(7, obj.getValor_pago());
                sql.setDate(8, new java.sql.Date(obj.getVencimento().getTime())); 
                sql.setInt(9, obj.getId());
                
                
                if(sql.executeUpdate() <= 0)
                     return false;

                sql = conexao.prepareStatement("delete from tran_item where idtran_fk = ?");
                sql.setInt(1, obj.getId());

                if(sql.executeUpdate() <= 0)
                     return false;

                sql = conexao.prepareStatement("insert into tran_item(idtran_fk, iditem_fk, quantidade) values(?,?,?)");

                for(Peca i : p){
                    sql.setInt(1, obj.getId());
                    sql.setInt(2, i.getId());
                    sql.setInt(3, i.getQtde());
                    if(sql.executeUpdate() <= 0) 
                       return false;
                }
                
                for(Servico c : obj.getServicos()){
                   this.setParametros(sql, obj, c);
                   if(sql.executeUpdate() <= 0) 
                       return false;
                }
                
                for(Peca c : p){
                Peca peca = pecadao.Abrir(c.getId());
                for(Peca t : h.getPecas()){
                    if(t.getId() == c.getId()){
                       peca.setQtde((peca.getQtde() + t.getQtde()) - c.getQtde());
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
    
    public List<Venda> Buscar(Venda filtro){
        
        List<Venda> lista = new ArrayList<>();
        List<Integer> tran_item = new ArrayList<>();
        List<Peca> pecas = new ArrayList<>();
        List<Servico> servicos = new ArrayList<>();
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
            
            for(Venda p : lista){
                String consulta = "select iditem_fk, idtran_fk, quantidade from tran_item where idtran_fk = "+ String.valueOf(p.getId()) + " and quantidade != 0";
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
            
           for(Venda p : lista){
                String consulta = "select iditem_fk, idtran_fk, quantidade from tran_item where idtran_fk = "+ String.valueOf(p.getId()+ " and quantidade = 0");
                sql =  conexao.prepareStatement(consulta);
                resultado = sql.executeQuery();//
                while(resultado.next()){
                     ServicoDAO servicodao;
                    try {
                        servicodao = new ServicoDAO();
                        Servico pe = servicodao.Abrir(resultado.getInt("iditem_fk"));
                        servicos.add(pe); 
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      
                }
                p.setServicos(servicos);//
                servicos = new ArrayList<>();//
            } 
           
            return lista;
   
        } catch(SQLException ex){
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Venda Abrir(int id) {
         Venda p = new Venda();
         p.setId(id);
         List<Venda> venda = this.Buscar(p);
         if(venda != null){
             for(Venda x : venda)
                 return x;
         }else
             return null;
         
        return null;
    }
    
    public boolean Apagar(Venda obj){
    try{
        PreparedStatement sql = conexao.prepareStatement("delete from tran_item where idtran_fk = ?");

        sql.setInt(1, obj.getId());

        if((sql.executeUpdate() < 0) && obj.getPecas() == null)
            return false;
        
        sql = conexao.prepareStatement("delete from tran_veiculo where tran_fk = ?");

        sql.setInt(1, obj.getId());

        if(sql.executeUpdate() < 0)
            return false;

        if(!super.Apagar(obj))
            return false;
    }catch(Exception ex){
        return false;    
    }
    obj = null;
    return true;
    }
    
    
    
}
