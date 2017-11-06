/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Apresentacao;

import DSC.JROficina.Aplicacao.ClienteRepositorio;
import DSC.JROficina.Aplicacao.ServicoRepositorio;
import DSC.JROficina.Aplicacao.CompraRepositorio;
import DSC.JROficina.Aplicacao.FornecedorRepositorio;
import DSC.JROficina.Aplicacao.MotoRepositorio;
import DSC.JROficina.Aplicacao.PecaRepositorio;
import DSC.JROficina.Aplicacao.Repositorio;
import DSC.JROficina.Aplicacao.Servico;
import DSC.JROficina.Aplicacao.UsuarioRepositorio;
import DSC.JROficina.Aplicacao.VendaRepositorio;
import java.util.logging.Logger;
import DSC.JROficina.Persistencia.ClienteDAO;
import DSC.JROficina.Persistencia.CompraDAO;
import DSC.JROficina.Persistencia.FornecedorDAO;
import DSC.JROficina.Persistencia.MotoDAO;
import DSC.JROficina.Persistencia.UsuarioDAO;
import DSC.JROficina.Persistencia.PecaDAO;
import DSC.JROficina.Persistencia.ServicoDAO;
import DSC.JROficina.Persistencia.VendaDAO;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author Rodrigo
 */
public class Repositorios {
    static ClienteRepositorio clienteDAO = null;
    
    public static ClienteRepositorio getClienteRepositorio(){
        if(clienteDAO == null)
            try{
                clienteDAO = new ClienteDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return clienteDAO;
    } 

    static FornecedorRepositorio fornecedorDAO = null;
    
    public static FornecedorRepositorio getFornecedorRepositorio(){
        if(fornecedorDAO == null)
            try{
                fornecedorDAO = new FornecedorDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return fornecedorDAO;
    }

    public static UsuarioRepositorio usuarioDAO = null;
    
    public static UsuarioRepositorio getUsuarioRepositorio(){
        if(usuarioDAO == null)
            try{
                usuarioDAO = new UsuarioDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return usuarioDAO;
    }

    public static PecaRepositorio pecaDAO = null;
    
    public static PecaRepositorio getPecaRepositorio(){
        if(pecaDAO == null)
            try{
                pecaDAO = new PecaDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return pecaDAO;
    }
    
    public static MotoRepositorio motoDAO = null;
    
     public static MotoRepositorio getMotoRepositorio(){
        if(motoDAO == null)
            try{
                motoDAO = new MotoDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return motoDAO;
    }
     
    public static CompraRepositorio compraDAO = null; 

    public static CompraRepositorio getCompraRepositorio() {
          if(compraDAO == null)
            try{
                compraDAO = new CompraDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return compraDAO;
    }

    public static ServicoRepositorio servicoDAO = null; 

    static Repositorio<Servico> getServicoRepositorio() {
        if(servicoDAO == null)
            try{
                servicoDAO = new ServicoDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return servicoDAO;
    }

    public static VendaRepositorio vendaDAO = null; 

    public static VendaRepositorio getVendaRepositorio() {
          if(vendaDAO == null)
            try{
                vendaDAO = new VendaDAO();
            }catch(ClassNotFoundException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }catch(SQLException ex){
                Logger.getLogger(Repositorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        return vendaDAO;
    }
    
}
