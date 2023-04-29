package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

public class FuncionarioDao extends GenericDao {
	private String instrucaoSql; // Atributo para armazenar a instrucao SQL a ser executada.
	private PreparedStatement comando; // Atributo usado para preparar e executar instrucoes SQL.
	private static PreparedStatement comando2;
	private static PreparedStatement comando3;
	private static PreparedStatement comando4;
	private static PreparedStatement comando5;
	private ResultSet registros; // Atributo que recebe os dados retornados por uma instrucao SQL.
	private ResultSet registros2;
	private ResultSet registros3;
	private ResultSet registros4;
	private ResultSet registros5;
	private static String excecao = null; // Atributo para armazenar mensagens de excecao.

	public String insereFuncionario(Funcionario funcionario) {
		instrucaoSql = "INSERT INTO FUNCIONARIO (Nome, Sexo, Salario, PlanoSaude, IdCargo) VALUES (?,?,?,?,?)";
		return insereAlteraExclui(instrucaoSql, funcionario.getNome(), funcionario.getSexo().toString(), funcionario.getSalario(),
				funcionario.isPlanoSaude(), funcionario.getCargo().getId());
	}
	
	public String alteraFuncionario(Funcionario funcionario) {
		instrucaoSql = "UPDATE FUNCIONARIO SET Nome=?, Sexo=?, Salario=?, PlanoSaude=?, IdCargo=? WHERE FUNCIONARIO.Id=?";
		return insereAlteraExclui(instrucaoSql, funcionario.getNome(), funcionario.getSexo().toString(), funcionario.getSalario(),
				funcionario.isPlanoSaude(), funcionario.getCargo().getId(), funcionario.getId());
	}
	
	public String excluiFuncionario(Integer id) {
		instrucaoSql = "DELETE FROM FUNCIONARIO WHERE FUNCIONARIO.Id=?";
		return insereAlteraExclui(instrucaoSql, id);
	}
	
	public List<Cargo> recuperaCargos() {
		Cargo cargo;
		List<Cargo> cargos = new ArrayList<Cargo>();
		instrucaoSql = "SELECT * FROM CARGO";

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
						// Atribui o Id e a Descricao do cargo ao objeto Cargo por meio dos metodos set
						// e
						// adiciona este objeto ao ArrayList funcionarios.
						cargo = new Cargo();
						cargo.setId(registros.getInt("Id"));
						cargo.setDescricao(registros.getString("Descricao"));
						cargos.add(cargo);
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
			cargos = null; // Caso ocorra qualquer excecao.
		}
		return cargos; // Retorna o ArrayList de objetos Cargo.
	}

	// Esse metodo e necessario, porque os metodos "recuperaCargos" e
	// "consultaFuncionarios" retornam List<> e nao String.
	public String getExcecao() {
		return excecao;
	}

	public List<Funcionario> consultaFuncionarios() {
		Funcionario funcionario = null;
		Cargo cargo;
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();

		instrucaoSql = "SELECT * FROM FUNCIONARIO";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				registros = comando.executeQuery();
				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						funcionario = new Funcionario();
						funcionario.setId(registros.getInt("Id"));
						funcionario.setNome(registros.getString("Nome"));
						funcionario.setSexo(registros.getString("Sexo").charAt(0));
						funcionario.setSalario(registros.getBigDecimal("Salario"));
						funcionario.setPlanoSaude(registros.getBoolean("PlanoSaude"));
						if (Objects.nonNull(registros.getInt("IdCargo"))) {
							cargo = achaCargoPorId(registros.getInt("IdCargo"));
							funcionario.setCargo(cargo);
						}
						funcionarios.add(funcionario);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			funcionarios = null;
		}
		return funcionarios;
	}

	private Cargo achaCargoPorId(int id) {
		Cargo cargo = null;
		Departamento departamento;

		instrucaoSql = "SELECT * FROM CARGO WHERE CARGO.Id=" + id;

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando2 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros2 = comando2.executeQuery();

				if (registros2.next()) {
					registros2.beforeFirst();
					while (registros2.next()) {
						cargo = new Cargo();
						cargo.setId(registros2.getInt("Id"));
						cargo.setDescricao(registros2.getString("Descricao"));
						if (Objects.nonNull(registros2.getInt("IdDepto"))) {
							departamento = achaDepartamentoPorId(registros2.getInt("IdDepto"));
							cargo.setDepartamento(departamento);
						}
					}
				}
				registros2.close();
				comando2.close();
				ConnectionDatabase.getConexaoBd().close();
				return cargo;
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			System.out.println(excecao);
			cargo = null;
		}
		return cargo;
	}

	private Departamento achaDepartamentoPorId(int id) {
		Departamento departamento = null;
		Funcionario gerente;

		instrucaoSql = "SELECT * FROM DEPARTAMENTO WHERE DEPARTAMENTO.Id=" + id;

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando3 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros3 = comando3.executeQuery();

				if (registros3.next()) {
					registros3.beforeFirst();
					while (registros3.next()) {
						departamento = new Departamento();
						departamento.setId(registros3.getInt("Id"));
						departamento.setNomeDepto(registros3.getString("NomeDepto"));
						if (Objects.nonNull(registros3.getInt("IdFuncGerente"))) {
							gerente = achaGerentePorId(registros3.getInt("IdFuncGerente"));
							departamento.setGerente(gerente);
						}
					}
				}
				registros3.close();
				comando3.close();
				ConnectionDatabase.getConexaoBd().close();
				return departamento;
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			System.out.println(excecao);
			departamento = null;
		}
		return departamento;
	}

	private Funcionario achaGerentePorId(int id) {
		Funcionario gerente = null;
		Cargo cargo;

		instrucaoSql = "SELECT * FROM FUNCIONARIO WHERE FUNCIONARIO.Id=" + id;

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando4 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				registros4 = comando4.executeQuery();

				if (registros4.next()) {
					registros4.beforeFirst();
					while (registros.next()) {
						gerente = new Funcionario();
						gerente.setId(registros4.getInt("Id"));
						gerente.setNome(registros4.getString("Nome"));
						gerente.setSexo(registros4.getString("Sexo").charAt(0));
						gerente.setSalario(registros4.getBigDecimal("Salario"));
						gerente.setPlanoSaude(registros4.getBoolean("PlanoSaude"));
						if (Objects.nonNull(registros4.getInt("IdCargo"))) {
							cargo = achaCargoGerentePorId(registros4.getInt("IdCargo"));
							gerente.setCargo(cargo);
						}
					}
				}
				registros4.close();
				comando4.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			gerente = null;
		}
		return gerente;
	}

	private Cargo achaCargoGerentePorId(int id) {
		Cargo cargo = null;

		instrucaoSql = "SELECT * FROM CARGO WHERE CARGO.Id=" + id;

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando5 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros5 = comando5.executeQuery();

				if (registros5.next()) {
					registros5.beforeFirst();
					while (registros5.next()) {
						cargo = new Cargo();
						cargo.setId(registros5.getInt("Id"));
						cargo.setDescricao(registros5.getString("Descricao"));
						cargo.setDepartamento(null);
					}
				}
				registros5.close();
				comando5.close();
				ConnectionDatabase.getConexaoBd().close();
				return cargo;
			}
		} catch (Exception e) {
			excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			System.out.println(excecao);
			cargo = null;
		}
		return cargo;
	}
}
