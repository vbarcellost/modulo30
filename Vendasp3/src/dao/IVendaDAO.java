/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import execption.DAOException;
import execption.MaisDeUmRegistroException;
import execption.TableException;
import execption.TipoChaveNaoEncontradaException;
import vendasp3.Venda;

import java.util.Collection;

public interface IVendaDAO {

    Boolean cadastrar(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    void cancelarVenda(Venda venda) throws DAOException;

    void finalizarVenda(Venda venda) throws DAOException;

    Venda consultar(String codigo) throws MaisDeUmRegistroException, TableException, DAOException;

    Collection<Venda> buscarTodos() throws DAOException;
}