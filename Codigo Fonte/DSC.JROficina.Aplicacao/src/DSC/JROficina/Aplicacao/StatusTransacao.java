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
public enum StatusTransacao {
    PAGO(1,"Pago"),
    PENDENTE(2,"Pendente");
    
    private int id;
    private String descricao;
    
    private StatusTransacao(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public String toString(){
        return descricao;
    }
    
    public static StatusTransacao Abrir(int id){
        for(StatusTransacao t: StatusTransacao.values())
                if(id == t.getId())
                    return t;
        return null;
    }
}
