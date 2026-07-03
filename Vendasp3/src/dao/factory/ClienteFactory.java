/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.factory;

import vendasp3.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteFactory {

    public static Cliente convert(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("ID_CLIENTE"));
        cliente.setNome(rs.getString("NOME"));
        cliente.setCpf(rs.getLong("CPF"));
        cliente.setTel(rs.getLong("TEL"));
        cliente.setEnd(rs.getString("ENDERECO"));
        cliente.setNumero(rs.getInt("NUMERO"));
        cliente.setCidade(rs.getString("CIDADE"));
        cliente.setEstado(rs.getString("ESTADO"));
        cliente.setIdade(rs.getInt("IDADE"));
        cliente.setEmail(rs.getString("EMAIL"));
        return cliente;
    }
}