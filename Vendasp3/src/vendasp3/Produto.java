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

@Tabela("TB_PRODUTO")
public class Produto implements Persistente {

    @ColunaTabela(dbName = "ID", setJavaName = "setId")
    private Long id;

    @TipoChave("getCodigo")
    @ColunaTabela(dbName = "CODIGO", setJavaName = "setCodigo")
    private String codigo;

    @ColunaTabela(dbName = "NOME", setJavaName = "setNome")
    private String nome;

    @ColunaTabela(dbName = "DESCRICAO", setJavaName = "setDescricao")
    private String descricao;

    @ColunaTabela(dbName = "VALOR", setJavaName = "setValor")
    private BigDecimal valor;

    @ColunaTabela(dbName = "PESO", setJavaName = "setPeso")
    private BigDecimal peso;

    @ColunaTabela(dbName = "CATEGORIA", setJavaName = "setCategoria")
    private String categoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}