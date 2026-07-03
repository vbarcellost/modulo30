/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.generic.GenericDAO;
import vendasp3.Produto;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO extends GenericDAO<Produto, String> implements IProdutoDAO {

    @Override
    public Class<Produto> getTipoClasse() {
        return Produto.class;
    }

    @Override
    public void atualiarDados(Produto entity, Produto entityCadastrado) {
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setDescricao(entity.getDescricao());
        entityCadastrado.setValor(entity.getValor());
        entityCadastrado.setPeso(entity.getPeso());
        entityCadastrado.setCategoria(entity.getCategoria());
    }

    @Override
    protected String getQueryInsercao() {
        return "INSERT INTO TB_PRODUTO (CODIGO, NOME, DESCRICAO, VALOR, PESO, CATEGORIA) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_PRODUTO WHERE CODIGO = ?";
    }

    @Override
    protected String getQueryAtualizacao() {
        return "UPDATE TB_PRODUTO SET NOME = ?, DESCRICAO = ?, VALOR = ?, PESO = ?, CATEGORIA = ? WHERE CODIGO = ?";
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getCodigo());
        stm.setString(2, produto.getNome());
        stm.setString(3, produto.getDescricao());
        stm.setBigDecimal(4, produto.getValor());
        stm.setBigDecimal(5, produto.getPeso());
        stm.setString(6, produto.getCategoria());
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo);
    }

    @Override
    protected void setParametrosQueryAtualizacao(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getDescricao());
        stm.setBigDecimal(3, produto.getValor());
        stm.setBigDecimal(4, produto.getPeso());
        stm.setString(5, produto.getCategoria());
        stm.setString(6, produto.getCodigo());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo);
    }
}