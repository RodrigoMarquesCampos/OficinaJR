/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC.JROficina.Aplicacao;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class Compra implements Entidade{

    protected int id;
    protected double valor;
    protected List<Peca> pecas;
    protected Pessoa aliado;
    protected Date data;
    protected float valor_pago;
    protected int parcelas;
    protected float valor_parc; 
    protected Date vencimento;
    protected int tipo = 1;
    protected StatusTransacao status;

    public Compra(int id, double valor, List<Peca> pecas, Pessoa aliado, Date data, float valor_pago, int parcelas, float valor_parc, Date vencimento) {
        this.id = id;
        this.valor = valor;
        this.pecas = pecas;
        this.aliado = aliado;
        this.data = data;
        this.valor_pago = valor_pago;
        this.parcelas = parcelas;
        this.valor_parc = valor_parc;
        this.vencimento = vencimento;
    }
   
    public Compra(){    
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }
    
    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public float getValor_parc() {
        return valor_parc;
    }

    public void setValor_parc(float valor_parc) {
        this.valor_parc = valor_parc;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(List<Peca> pecas) {
        this.pecas = pecas;
    }

    public Pessoa getAliado() {
        return aliado;
    }

    public void setAliado(Pessoa aliado) {
        this.aliado = aliado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(float valor_pago) {
        this.valor_pago = valor_pago;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }
}
