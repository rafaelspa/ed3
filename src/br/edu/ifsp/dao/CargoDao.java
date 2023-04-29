package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

public class CargoDao extends GenericDao {
	private String instrucaoSql; // Atributo para armazenar a instrucao SQL a ser executada.
	private PreparedStatement comando; // Atributo usado para preparar e executar instrucoes SQL.
	private static PreparedStatement comando2;
	private static PreparedStatement comando3;
	private ResultSet registros; // Atributo que recebe os dados retornados por uma instrucao SQL.
	private ResultSet registros2;
	private ResultSet registros3;
	private static String excecao = null; // Atributo para armazenar mensagens de excecao.

	public String insereCargo(Cargo cargo) {
		instrucaoSql = "INSERT INTO CARGO (Descricao, IdDepto) VALUES (?,?)";
		return insereAlteraExclui(instrucaoSql, cargo.getDescricao(), cargo.getDepartamento().getId());
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
						// Atribui o Id e o Nome do departamento ao objeto Departamento por meio dos
						// metodos set e
						// adiciona este objeto ao ArrayList departamentos.
						departamento = new Departamento();
						departamento.setId(registros.getInt("Id"));
						departamento.setNomeDepto(registros.getString("NomeDepto"));
						departamentos.add(departamento);
					}
				}
				registros.close(); // Libera os recursos usados pelo objeto ResultSet.
				comando.close(); // Libera os recursos usados pelo objeto PreparedStatement.
				// Libera os recursos usados pelo objeto Connection e fecha a conexao com o
				// banco de dados.
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			departamentos = null; // Caso ocorra qualquer excecao.
		}
		return departamentos; // Retorna o ArrayList de objetos Departamento.
	}

	// Esse metodo e necessario, porque os metodos "recuperaDepartamentos" e
	// "consultaCargos" retornam List<> e nao String.
	public String getExcecao() {
		return excecao;
	}

	public List<Cargo> consultaCargos() {
		Cargo cargo;
		Departamento departamento;
		List<Cargo> cargos = new ArrayList<Cargo>();

		instrucaoSql = "SELECT * FROM CARGO";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						cargo = new Cargo();
						cargo.setId(registros.getInt("Id"));
						cargo.setDescricao(registros.getString("Descricao"));
						if (Objects.nonNull(registros.getInt("IdDepto"))) {
							departamento = retornaDepartamentoPorId(registros.getInt("IdDepto"));
							cargo.setDepartamento(departamento);							
						}
						cargos.add(cargo);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
				return cargos;
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			System.out.println(excecao);
			cargos = null;
		}
		return cargos;
	}
	
	 public Departamento retornaDepartamentoPorId(Integer id) {
	    	Departamento departamento = null;
	    	Funcionario gerente;
	    	
	    	instrucaoSql = "SELECT * FROM DEPARTAMENTO WHERE DEPARTAMENTO.Id=" + id;
	    	
	    	try {
	    		excecao = ConnectionDatabase.conectaBd();
	    		if (excecao == null) {
	    			comando2 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
	    			registros2 = comando2.executeQuery();
	    			
	    			if (registros2.next()) {
	    				registros2.beforeFirst();
	    				departamento = new Departamento();
	    				departamento.setId(registros2.getInt("Id"));
	    				departamento.setNomeDepto(registros2.getString("NomeDepto"));
	    				gerente = retornaFuncionarioPorId(registros2.getInt("IdFuncGerente"));
	    				departamento.setGerente(gerente);
	    			}
	    			registros2.close();
	    			comando2.close();
	    			ConnectionDatabase.getConexaoBd().close();
	    		}
	    	} catch(Exception e) {
	    		excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
	    		departamento = null;
	    	}
	    	return departamento;
	    }
	 
	    public Funcionario retornaFuncionarioPorId(Integer id) {
	    	Funcionario funcionario = null;
	    	instrucaoSql = "SELECT * FROM FUNCIONARIO WHERE FUNCIONARIO.Id=" + id;
	    	
	    	try {
	    		excecao = ConnectionDatabase.conectaBd();
	    		if (excecao == null) {
	    			comando3 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
	    			registros3 = comando3.executeQuery();
	    			
	    			if (registros3.next()) {
	    				registros3.beforeFirst();
	    				funcionario = new Funcionario();
	    				funcionario.setId(registros3.getInt("Id"));
	    				funcionario.setNome(registros3.getString("Nome"));
	    				funcionario.setSexo(registros3.getString("Sexo").charAt(0));
	    				funcionario.setSalario(registros3.getBigDecimal("Salario"));
	    				funcionario.setPlanoSaude(registros3.getBoolean("PlanoSaude"));
	    			}
	    			registros3.close();
	    			comando3.close();
	    			ConnectionDatabase.getConexaoBd().close();
	    		}
	    	} catch(Exception e) {
	    		excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
	    		funcionario = null;
	    	}
	    	return funcionario;
	    }
}
