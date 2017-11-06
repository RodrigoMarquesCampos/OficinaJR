/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Apresentacao;

import DSC.JROficina.Aplicacao.Entidade;
import DSC.JROficina.Aplicacao.Repositorio;
import DSC.JROficina.Aplicacao.ViolacaoRegrasNegocioException;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo
 */
public abstract class TelaEdicao<T extends Entidade> extends javax.swing.JInternalFrame {

    Repositorio<T> repositorio;
    T entidade;
    TelaBusca<T> busca;
    
    
    public TelaEdicao() {
  
    }
    
    public T getEntidade(){
        return entidade;
    }
    
    public void setEntidade(T entidade){
        this.entidade = entidade;
        
        carregaCampos();
    }

    public Repositorio<T> getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(Repositorio<T> repositorio) {
        this.repositorio = repositorio;
    }
    
    public TelaBusca<T> getBusca(){
        return busca;
    }
    
    public void setBusca(TelaBusca<T> busca){
        this.busca = busca;
    }
    
    public abstract void carregaCampos();
    public abstract void carregaObjeto() throws ViolacaoRegrasNegocioException;
    public abstract boolean verificarCamposObrigatorios();
    
    public void Salvar(){
        if(!verificarCamposObrigatorios()){
            JOptionPane.showMessageDialog(rootPane, "Todos os campos são de preenchimento obrigatorio!");
            return;
        }
    
        if(JOptionPane.showConfirmDialog(rootPane, "Deseja realmente salvar o objeto?") == 0){
            
            try{
                carregaObjeto();
            }catch(ViolacaoRegrasNegocioException ex){
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                return;
            }
            
            if(repositorio.Salvar(entidade)){
                JOptionPane.showMessageDialog(rootPane, "Registro salvo com sucesso!");
                cancelar();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Falha ao salvar o registro!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Operacao Cancelada!");
            cancelar();
        }
    }
    
    public void Apagar(){
        if(JOptionPane.showConfirmDialog(rootPane, "Deseja realmente apagar o registro?") == 0){
            if(repositorio.Apagar(entidade)){
                JOptionPane.showMessageDialog(rootPane, "Registro removido com sucesso!");
            } else{
                JOptionPane.showMessageDialog(rootPane, "Falha ao remover o registro!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Opercao Cancelada!");
        }
        
        cancelar();
    }
    
    public void cancelar(){
        busca.setVisible(true);
        this.setVisible(true);
        this.dispose();
    }
    
}