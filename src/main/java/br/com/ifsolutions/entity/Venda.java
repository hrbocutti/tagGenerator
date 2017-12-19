package br.com.ifsolutions.entity;

import java.util.ArrayList;

public class Venda {
    protected String codmovenda;
    protected String nomeCliente;
    protected String codEmpresa;
    protected String dataVenda;
    protected String numNota;
    protected String numCupom;
    protected ArrayList<Produtos> produtos;

    public String getCodmovenda() {
        return codmovenda;
    }

    public void setCodmovenda(String codmovenda) {
        this.codmovenda = codmovenda;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getNumNota() {
        return numNota;
    }

    public void setNumNota(String numNota) {
        this.numNota = numNota;
    }

    public String getNumCupom() {
        return numCupom;
    }

    public void setNumCupom(String numCupom) {
        this.numCupom = numCupom;
    }

    public ArrayList<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produtos> produtos) {
        this.produtos = produtos;
    }
}
