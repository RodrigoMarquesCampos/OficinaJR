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
public enum TipoCliente {
    FISICO(1,"Fisico"),
    JURIDICO(2,"Juridico");
    
    private int id;
    private String descricao;
    
    private TipoCliente(int id, String descricao){
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
    
    public static TipoCliente Abrir(int id){
        for(TipoCliente t: TipoCliente.values())
                if(id == t.getId())
                    return t;
        return null;
    }
}
