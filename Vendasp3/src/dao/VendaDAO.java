/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexao.ConnectionFactory;
import dao.factory.ClienteFactory;
import dao.factory.ProdutoQuantidadeFactory;
import execption.DAOException;
import execption.MaisDeUmRegistroException;
import execption.TableException;
import execption.TipoChaveNaoEncontradaException;
import vendasp3.Cliente;
import vendasp3.ProdutoQuantidade;
import vendasp3.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VendaDAO implements IVendaDAO {

    @Override
    public Boolean cadastrar(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            stm = connection.prepareStatement(
                "INSERT INTO TB_VENDA (CODIGO, ID_CLIENTE, VALOR_TOTAL, QUANTIDADE_TOTAL, DATA_VENDA, STATUS_VENDA) VALUES (?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            stm.setString(1, venda.getCodigo());
            stm.setLong(2, venda.getCliente().getId());
            stm.setBigDecimal(3, venda.getValorTotal());
            stm.setInt(4, venda.getQuantidadeTotalProdutos());
            stm.setTimestamp(5, Timestamp.from(venda.getDataVenda()));
            stm.setString(6, venda.getStatus().name());
            int result = stm.executeUpdate();

            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                venda.setId(rs.getLong(1));
            }

            for (ProdutoQuantidade pq : venda.getProdutos()) {
                PreparedStatement stmProd = connection.prepareStatement(
                    "INSERT INTO TB_PRODUTO_QUANTIDADE (ID_VENDA, ID_PRODUTO, QUANTIDADE, VALOR_TOTAL) VALUES (?, ?, ?, ?)"
                );
                stmProd.setLong(1, venda.getId());
                stmProd.setLong(2, pq.getProduto().getId());
                stmProd.setInt(3, pq.getQuantidade());
                stmProd.setBigDecimal(4, pq.getValorTotal());
                stmProd.executeUpdate();
                stmProd.close();
            }

            connection.commit();
            return result > 0;

        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new DAOException("Erro ao cadastrar venda.", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
    }

    @Override
    public void cancelarVenda(Venda venda) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = getConnection();
            stm = connection.prepareStatement("UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE CODIGO = ?");
            stm.setString(1, Venda.Status.CANCELADA.name());
            stm.setString(2, venda.getCodigo());
            stm.executeUpdate();
            venda.setStatus(Venda.Status.CANCELADA);

        } catch (SQLException e) {
            throw new DAOException("Erro ao cancelar venda.", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public void finalizarVenda(Venda venda) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = getConnection();
            stm = connection.prepareStatement("UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE CODIGO = ?");
            stm.setString(1, Venda.Status.CONCLUIDA.name());
            stm.setString(2, venda.getCodigo());
            stm.executeUpdate();
            venda.setStatus(Venda.Status.CONCLUIDA);

        } catch (SQLException e) {
            throw new DAOException("Erro ao finalizar venda.", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Venda consultar(String codigo) throws MaisDeUmRegistroException, TableException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            stm = connection.prepareStatement(
                "SELECT V.ID AS ID_VENDA, V.CODIGO, V.VALOR_TOTAL, V.DATA_VENDA, V.STATUS_VENDA, V.QUANTIDADE_TOTAL, " +
                "C.ID AS ID_CLIENTE, C.CPF, C.NOME, C.CIDADE, C.ENDERECO, C.ESTADO, C.NUMERO, C.TEL, C.IDADE, C.EMAIL " +
                "FROM TB_VENDA V " +
                "INNER JOIN TB_CLIENTE C ON V.ID_CLIENTE = C.ID " +
                "WHERE V.CODIGO = ?"
            );
            stm.setString(1, codigo);
            rs = stm.executeQuery();

            if (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getLong("ID_VENDA"));
                venda.setCodigo(rs.getString("CODIGO"));
                venda.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
                venda.setDataVenda(rs.getTimestamp("DATA_VENDA").toInstant());
                venda.setStatus(Venda.Status.getByName(rs.getString("STATUS_VENDA")));

                Cliente cliente = ClienteFactory.convert(rs);
                venda.setCliente(cliente);

                buscarProdutosVenda(connection, venda);
                return venda;
            }
            return null;

        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar venda.", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
    }

    private void buscarProdutosVenda(Connection connection, Venda venda) throws SQLException {
        PreparedStatement stm = connection.prepareStatement(
            "SELECT PQ.ID, PQ.QUANTIDADE, PQ.VALOR_TOTAL, " +
            "P.ID AS ID_PRODUTO, P.CODIGO, P.NOME, P.DESCRICAO, P.VALOR, P.PESO, P.CATEGORIA " +
            "FROM TB_PRODUTO_QUANTIDADE PQ " +
            "INNER JOIN TB_PRODUTO P ON PQ.ID_PRODUTO = P.ID " +
            "WHERE PQ.ID_VENDA = ?"
        );
        stm.setLong(1, venda.getId());
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            ProdutoQuantidade pq = ProdutoQuantidadeFactory.convert(rs);
            venda.getProdutos().add(pq);
        }
        rs.close();
        stm.close();
    }

    @Override
    public Collection<Venda> buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Collection<Venda> list = new ArrayList<>();

        try {
            connection = getConnection();
            stm = connection.prepareStatement("SELECT * FROM TB_VENDA");
            rs = stm.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getLong("ID"));
                venda.setCodigo(rs.getString("CODIGO"));
                venda.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
                venda.setStatus(Venda.Status.getByName(rs.getString("STATUS_VENDA")));
                list.add(venda);
            }
            return list;

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar todas as vendas.", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
    }

    private Connection getConnection() throws DAOException {
        try {
            return ConnectionFactory.getConnection();
        } catch (SQLException e) {
            throw new DAOException("Erro ao abrir conexão.", e);
        }
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) rs.close();
            if (stm != null && !stm.isClosed()) stm.close();
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}