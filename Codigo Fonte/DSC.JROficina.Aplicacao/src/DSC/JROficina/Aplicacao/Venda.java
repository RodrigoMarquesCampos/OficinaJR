/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Aplicacao;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Rodrigo
 */
public class Venda extends Compra{
    private List<Servico> servicos;
    private Moto veiculo;
    
    public Venda(){
        super();
        this.setTipo(2);
    }

    public Venda(List<Servico> servicos, int id, double valor, List<Peca> pecas, Pessoa aliado, Date data, float valor_pago, int parcelas, float valor_parc, Date vencimento) {
        super(id, valor, pecas, aliado, data, valor_pago, parcelas, valor_parc, vencimento);
        this.servicos = servicos;
        this.tipo = 2;
    }
    
    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public Moto getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Moto veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.servicos);
        hash = 71 * hash + Objects.hashCode(this.veiculo);
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
        final Venda other = (Venda) obj;
        if (!Objects.equals(this.servicos, other.servicos)) {
            return false;
        }
        return true;
    }
    
    
    
}
