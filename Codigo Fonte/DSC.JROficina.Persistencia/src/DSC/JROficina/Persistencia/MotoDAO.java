/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Persistencia;

import DSC.JROficina.Aplicacao.Cliente;
import DSC.JROficina.Aplicacao.Moto;
import DSC.JROficina.Aplicacao.MotoRepositorio;
import DSC.JROficina.Aplicacao.TipoCliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Rodrigo
 */
public class MotoDAO extends DAOGenerico<Moto> implements MotoRepositorio{
    
     public MotoDAO() throws ClassNotFoundException, SQLException {
       super();
       
       clientes = new ClienteDAO();
   }

    @Override
    protected String getConsultaInsert() {
        return "insert into veiculo(placa, marca, ano, modelo, idpessoa_fk) values(?,?,?,?,?)";
    }

    @Override
    protected String getConsultaUpdate() {
        return "update veiculo set placa = ?, marca = ?, ano = ?, modelo = ?, idpessoa_fk = ? where id = ?";
    }

    @Override
    protected String getConsultaDelete() {
        return "delete from veiculo where placa = ?";
    }

    @Override
    protected String getConsultaAbrir() {
        return "select id, placa, modelo, marca, idpessoa_fk, ano from veiculo where id = ?";
    }

    @Override
    protected String getConsultaBuscar() {
        return "select id, placa, modelo, marca, idpessoa_fk, ano from veiculo ";
    }

    @Override
    protected void setBuscaFiltros(Moto filtro) {
        if(filtro.getMarca() != null && !filtro.getMarca().isEmpty())
            this.adicionaFiltro("marca", filtro.getMarca());
            
        if(filtro.getModelo() != null && !filtro.getModelo().isEmpty())
            this.adicionaFiltro("modelo",filtro.getModelo());
        
        if(filtro.getPlaca() != null && !filtro.getPlaca().isEmpty())
            this.adicionaFiltro("placa",filtro.getPlaca());
        
        if(filtro.getAno() > 0)
            this.adicionaFiltro("ano",filtro.getAno());
        
        if(filtro.getDono() != null)
            this.adicionaFiltro("idpessoa_fk",filtro.getDono().getId());
        
    }

    @Override
    protected void setParametros(PreparedStatement sql, Moto obj) {
        try{
            sql.setString(1, obj.getPlaca());
            sql.setString(2, obj.getMarca());
            sql.setInt(3, obj.getAno());
            sql.setString(4, obj.getModelo());
            sql.setInt(5, obj.getDono().getId());      
            
            if(obj.getId() > 0)
                sql.setInt(6, obj.getId());
            
            
        } catch (SQLException ex) {
             Logger.getLogger(MotoDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    ClienteDAO clientes;

    @Override
    protected Moto setDados(ResultSet resultado) {
        try{   
            Moto obj = new Moto();
            obj.setPlaca(resultado.getString("placa"));
            obj.setAno(resultado.getInt("ano"));
            obj.setMarca(resultado.getString("marca"));
            obj.setModelo(resultado.getString("modelo"));
            obj.setDono(clientes.Abrir(resultado.getInt("idpessoa_fk")));
            obj.setId(resultado.getInt("id"));
            
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
