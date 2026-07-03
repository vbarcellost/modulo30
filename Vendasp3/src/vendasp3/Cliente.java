/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasp3;

import anotacoes.ColunaTabela;
import anotacoes.Tabela;
import anotacoes.TipoChave;
import dao.Persistente;

@Tabela("TB_CLIENTE")
public class Cliente implements Persistente {

    @TipoChave("getCpf")
    @ColunaTabela(dbName = "CPF", setJavaName = "setCpf")
    private Long cpf;

    @ColunaTabela(dbName = "ID", setJavaName = "setId")
    private Long id;

    @ColunaTabela(dbName = "NOME", setJavaName = "setNome")
    private String nome;

    @ColunaTabela(dbName = "CIDADE", setJavaName = "setCidade")
    private String cidade;

    @ColunaTabela(dbName = "ENDERECO", setJavaName = "setEnd")
    private String end;

    @ColunaTabela(dbName = "ESTADO", setJavaName = "setEstado")
    private String estado;

    @ColunaTabela(dbName = "NUMERO", setJavaName = "setNumero")
    private Integer numero;

    @ColunaTabela(dbName = "TEL", setJavaName = "setTel")
    private Long tel;

    @ColunaTabela(dbName = "IDADE", setJavaName = "setIdade")
    private Integer idade;

    @ColunaTabela(dbName = "EMAIL", setJavaName = "setEmail")
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Long getTel() { return tel; }
    public void setTel(Long tel) { this.tel = tel; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}