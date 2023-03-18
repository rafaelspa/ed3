package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;

public class CargoDao extends GenericDao {
	private String instrucaoSql; // Atributo para armazenar a instrucao SQL a ser executada.
	private PreparedStatement comando; // Atributo usado para preparar e executar instrucoes SQL.
	private ResultSet registros; // Atributo que recebe os dados retornados por uma instrucao SQL.
	private static String excecao = null; // Atributo para armazenar mensagens de excecao.

    public String insereCargo(Cargo cargo) {
        instrucaoSql = "INSERT INTO CARGO (Descricao, IdDepto) VALUES (?,?)";
        return insere(instrucaoSql, cargo.getDescricao(), cargo.getDepartamento().getId());
    }
    
    public List<Departamento> recuperaDepartamentos() {
        Departamento departamento;
        List<Departamento> departamentos = new ArrayList<Departamento>();
        instrucaoSql = "SELECT * FROM DEPARTAMENTO";
        
        try {
        	excecao = ConnectionDatabase.conectaBd(); // Abre a conexao com o banco de dados.
        	if (excecao == null) {
                // Obtem os dados de conexao com o banco de dados e prepara a instrucao SQL.
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
                
                // Executa a instrucao SQL e retorna os dados ao objeto ResultSet.
                registros = comando.executeQuery();
                
                if (registros.next()) { // Se for retornado pelo menos um registro.
                    registros.beforeFirst(); // Retorna o cursor para antes do 1o registro.
        	        while (registros.next()) {
                        // Atribui o Id e o Nome do departamento ao objeto Departamento por meio dos metodos set e
                        // adiciona este objeto ao ArrayList departamentos.
        	            departamento = new Departamento();
        	            departamento.setId(registros.getInt("Id"));
        	            departamento.setNomeDepto(registros.getString("NomeDepto"));
        	            departamentos.add(departamento);
        	        }
        	    }
                registros.close(); // Libera os recursos usados pelo objeto ResultSet.
                comando.close(); // Libera os recursos usados pelo objeto PreparedStatement.
                // Libera os recursos usados pelo objeto Connection e fecha a conexao com o banco de dados.
                ConnectionDatabase.getConexaoBd().close(); 
            }
        } catch (Exception e) {
        	excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
        	departamentos = null; // Caso ocorra qualquer excecao.
        }
        return departamentos; // Retorna o ArrayList de objetos Departamento.
    }
    
    // Esse metodo e necessario, porque os metodos "recuperaDepartamentos" e "consultaCargos" retornam List<> e nao String.
	public String getExcecao() {
		return excecao;
	}
}
