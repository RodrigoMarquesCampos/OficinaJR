/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Aplicacao;

import java.util.Objects;

/**
 *
 * @author Rodrigo
 */
public class Cliente extends Pessoa{
    private String cpf_cnpj;
    private TipoCliente tipo;

    public Cliente() {
        super();
    }
    
    
    
    public Cliente(String nome, String telefone, int id, String endereco, String cpf_cnpj, TipoCliente tipo) {
        super(nome, telefone, id, endereco);
        this.tipo = tipo;
        this.cpf_cnpj = cpf_cnpj;
    }
    
    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @param nome
     * @throws ViolacaoRegrasNegocioException
     */
    public void setNomee(String nome) throws ViolacaoRegrasNegocioException{
        if(nome == null)
            throw new ViolacaoRegrasNegocioException("O nome do Cliente não pode ficar vazio!"); 
        
        char c;
        int cont = 0;
            
        //Verificação de numeros e/ou caracteres especiais
        for(int i =0; i<nome.length(); i++){
            c = nome.charAt(i);
            
            if(((c >= 60) && (c <= 90)) || ((c >= 97) && (c<= 122)) || (c == 'á')
            || (c == 'â') || (c == 'ã') || (c == 'Á') || (c == 'Â') || (c == 'Ã')
            || (c == 'ê') || (c == 'é') || (c == 'É') || (c == 'Ê') || (c == 'ó')
            || (c == 'ô') || (c == 'õ') || (c == 'Ó') || (c == 'Ô') || (c == 'Õ') || (c == ' ')){
              cont++; 
            }
        }
        if(cont != nome.length())
            throw new ViolacaoRegrasNegocioException("O nome do Cliente não pode ter numeros e/ou caracteres especiais!");
        
        this.nome = nome;
        
    }
    
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.cpf_cnpj);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.cpf_cnpj, other.cpf_cnpj)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
    
        
}
