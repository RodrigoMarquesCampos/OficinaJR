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
public enum TipoUsuario {
    ADIMINISTRADOR(1, "Administrador"),
    FUNCIONARIO(2, "Funcionario");
    
    private int id;
    private String descricao;

    private TipoUsuario(int id, String descricao) {
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
    
    public static TipoUsuario Abrir(int id){
        for(TipoUsuario t: TipoUsuario.values())
                if(id == t.getId())
                    return t;
        return null;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    } 
    
}
