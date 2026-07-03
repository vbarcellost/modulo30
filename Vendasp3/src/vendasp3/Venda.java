/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasp3;

import anotacoes.ColunaTabela;
import anotacoes.Tabela;
import anotacoes.TipoChave;
import dao.Persistente;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Tabela("TB_VENDA")
public class Venda implements Persistente {

    public enum Status {
        INICIADA, CONCLUIDA, CANCELADA;

        public static Status getByName(String name) {
            for (Status s : Status.values()) {
                if (s.name().equals(name)) return s;
            }
            return null;
        }
    }

    @ColunaTabela(dbName = "ID", setJavaName = "setId")
    private Long id;

    @TipoChave("getCodigo")
    @ColunaTabela(dbName = "CODIGO", setJavaName = "setCodigo")
    private String codigo;

    private Cliente cliente;

    private Set<ProdutoQuantidade> produtos;

    @ColunaTabela(dbName = "VALOR_TOTAL", setJavaName = "setValorTotal")
    private BigDecimal valorTotal;

    @ColunaTabela(dbName = "DATA_VENDA", setJavaName = "setDataVenda")
    private Instant dataVenda;

    @ColunaTabela(dbName = "STATUS_VENDA", setJavaName = "setStatus")
    private Status status;

    public Venda() {
        this.produtos = new HashSet<>();
        this.valorTotal = BigDecimal.ZERO;
    }

    public void adicionarProduto(Produto produto, Integer quantidade) {
        if (this.status == Status.CONCLUIDA || this.status == Status.CANCELADA) {
            throw new UnsupportedOperationException("Não é possível adicionar produtos a uma venda " + this.status);
        }
        ProdutoQuantidade pq = getProdutoQuantidade(produto);
        if (pq == null) {
            pq = new ProdutoQuantidade();
            pq.setProduto(produto);
            this.produtos.add(pq);
        }
        pq.adicionar(quantidade);
        recalcularTotal();
    }

    public void removerProduto(Produto produto, Integer quantidade) {
        if (this.status == Status.CONCLUIDA || this.status == Status.CANCELADA) {
            throw new UnsupportedOperationException("Não é possível remover produtos de uma venda " + this.status);
        }
        ProdutoQuantidade pq = getProdutoQuantidade(produto);
        if (pq != null) {
            pq.remover(quantidade);
            if (pq.getQuantidade() <= 0) {
                this.produtos.remove(pq);
            }
        }
        recalcularTotal();
    }

    public void removerTodosProdutos() {
        if (this.status == Status.CONCLUIDA || this.status == Status.CANCELADA) {
            throw new UnsupportedOperationException("Não é possível remover produtos de uma venda " + this.status);
        }
        this.produtos.clear();
        this.valorTotal = BigDecimal.ZERO;
    }

    private ProdutoQuantidade getProdutoQuantidade(Produto produto) {
        for (ProdutoQuantidade pq : this.produtos) {
            if (pq.getProduto().getCodigo().equals(produto.getCodigo())) {
                return pq;
            }
        }
        return null;
    }

    private void recalcularTotal() {
        this.valorTotal = BigDecimal.ZERO;
        for (ProdutoQuantidade pq : this.produtos) {
            this.valorTotal = this.valorTotal.add(pq.getValorTotal());
        }
    }

    public Integer getQuantidadeTotalProdutos() {
        return this.produtos.stream().mapToInt(ProdutoQuantidade::getQuantidade).sum();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Set<ProdutoQuantidade> getProdutos() { return produtos; }
    public void setProdutos(Set<ProdutoQuantidade> produtos) { this.produtos = produtos; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public Instant getDataVenda() { return dataVenda; }
    public void setDataVenda(Instant dataVenda) { this.dataVenda = dataVenda; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}