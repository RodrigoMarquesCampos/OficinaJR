/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Aplicacao;

import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class Fornecedor extends Pessoa{

    public static List<Fornecedor> Buscar(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String cnpj;

    public Fornecedor() {
    }

    public Fornecedor(String cnpj) {
        this.cnpj = cnpj;
    }

    public Fornecedor(String cnpj, String nome, String telefone, int id, String endereco) {
        super(nome, telefone, id, endereco);
        this.cnpj = cnpj;
    }
    
    

    public Fornecedor(String nome, String telefone, int id, String endereco, String cnpj) {
        super(nome, telefone, id, endereco);
        this.cnpj = cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
    
     @Override
    public String toString() {
        return this.nome;
    }
    
  
    
    
}
