/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.generic.GenericDAO;
import vendasp3.Cliente;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {

    @Override
    public Class<Cliente> getTipoClasse() {
        return Cliente.class;
    }

    @Override
    public void atualiarDados(Cliente entity, Cliente entityCadastrado) {
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setCidade(entity.getCidade());
        entityCadastrado.setEnd(entity.getEnd());
        entityCadastrado.setEstado(entity.getEstado());
        entityCadastrado.setNumero(entity.getNumero());
        entityCadastrado.setTel(entity.getTel());
        entityCadastrado.setIdade(entity.getIdade());
        entityCadastrado.setEmail(entity.getEmail());
    }

    @Override
    protected String getQueryInsercao() {
        return "INSERT INTO TB_CLIENTE (CPF, NOME, CIDADE, ENDERECO, ESTADO, NUMERO, TEL, IDADE, EMAIL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_CLIENTE WHERE CPF = ?";
    }

    @Override
    protected String getQueryAtualizacao() {
        return "UPDATE TB_CLIENTE SET NOME = ?, CIDADE = ?, ENDERECO = ?, ESTADO = ?, NUMERO = ?, TEL = ?, IDADE = ?, EMAIL = ? WHERE CPF = ?";
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setLong(1, cliente.getCpf());
        stm.setString(2, cliente.getNome());
        stm.setString(3, cliente.getCidade());
        stm.setString(4, cliente.getEnd());
        stm.setString(5, cliente.getEstado());
        stm.setInt(6, cliente.getNumero());
        stm.setLong(7, cliente.getTel());
        stm.setInt(8, cliente.getIdade());
        stm.setString(9, cliente.getEmail());
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stm, Long cpf) throws SQLException {
        stm.setLong(1, cpf);
    }

    @Override
    protected void setParametrosQueryAtualizacao(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1, cliente.getNome());
        stm.setString(2, cliente.getCidade());
        stm.setString(3, cliente.getEnd());
        stm.setString(4, cliente.getEstado());
        stm.setInt(5, cliente.getNumero());
        stm.setLong(6, cliente.getTel());
        stm.setInt(7, cliente.getIdade());
        stm.setString(8, cliente.getEmail());
        stm.setLong(9, cliente.getCpf());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stm, Long cpf) throws SQLException {
        stm.setLong(1, cpf);
    }
}