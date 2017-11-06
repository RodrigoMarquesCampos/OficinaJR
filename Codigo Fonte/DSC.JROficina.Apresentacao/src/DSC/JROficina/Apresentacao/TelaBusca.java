/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Apresentacao;

import DSC.JROficina.Aplicacao.Entidade;
import DSC.JROficina.Aplicacao.Repositorio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Rodrigo
 */
public abstract class TelaBusca<T extends Entidade> extends javax.swing.JInternalFrame {
    
    Repositorio<T> repositorio;
    T filtro;
    
    TelaEdicao<T> tela_edicao;
    
    Class tipo_tela;
    
    public abstract int retornaIdSelecionado();
    
    public abstract void preencheFiltro();
    
    public abstract void preencheTabela(List<T> listagem);
    
    public abstract void limpaFiltro();
    
    public TelaBusca(Repositorio<T> repositorio, Class tipo_tela){
        this.repositorio = repositorio;
        this.tipo_tela = tipo_tela;
    }
    
    public void buscar(){
        limpaFiltro();
        
        preencheFiltro();
        
        List<T> listagem = repositorio.Buscar(filtro);
        preencheTabela(listagem);
    }
    
    public void novo(){
        
        try{
            tela_edicao = (TelaEdicao<T>) tipo_tela.newInstance();
            
        }catch(InstantiationException ex){
            Logger.getLogger(TelaBusca.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IllegalAccessException ex){
            Logger.getLogger(TelaBusca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tela_edicao.setBusca(this);
        
        tela_edicao.setRepositorio(repositorio);
        
        //tela_edicao.setEntidade(filtro);
        
        this.getParent().add(tela_edicao);
        
        tela_edicao.setVisible(true);
        
        this.setVisible(false);
        
        
    }
    
    public void editar(){
        int id = retornaIdSelecionado();
        
        filtro = repositorio.Abrir(id);
        
        try{
            
            tela_edicao = (TelaEdicao<T>) tipo_tela.newInstance();
            
        }catch(InstantiationException ex){
            Logger.getLogger(TelaBusca.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IllegalAccessException ex){
            Logger.getLogger(TelaBusca.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        tela_edicao.setBusca(this);
        
        tela_edicao.setRepositorio(repositorio);
        
        tela_edicao.setEntidade(filtro);
        
        this.getParent().add(tela_edicao);
        
        tela_edicao.setVisible(true);
        
        this.setVisible(false);
    
    }
        
}

