/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Aplicacao;

/**
 *
 * @author Rodrigo
 */
public abstract class Pessoa implements Entidade{
    protected String nome;
    protected String telefone;
    protected int id;
    protected String endereco;
    

    @Override
    public int getId() {
       return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

   public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Pessoa() {
    }
    
    
    
    public Pessoa(String nome, String telefone, int id, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.id = id;
        this.endereco = endereco;
    }
    
    public Pessoa(String nome){
        this.nome = nome;
    }
}
