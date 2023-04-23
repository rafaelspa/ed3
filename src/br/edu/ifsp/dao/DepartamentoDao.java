package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

public class DepartamentoDao extends GenericDao {
	private String instrucaoSql; // Atributo para armazenar a instrucao SQL a ser executada.
	private static PreparedStatement comando; // Atributo usado para preparar e executar instrucoes SQL.
	private static PreparedStatement comando2; 
	private ResultSet registros; // Atributo que recebe os dados retornados por uma instrucao SQL.
	private ResultSet registros2;
	private static String excecao = null; // Atributo para armazenar mensagens de excecao.

    public String insereDepartamento(Departamento departamento) {
        instrucaoSql = "INSERT INTO Departamento (NomeDepto, IdFuncGerente) VALUES (?,?)";
        return insere(instrucaoSql, departamento.getNomeDepto(), departamento.getGerente().getId());
    }
    
    public List<Funcionario> recuperaFuncionarios() {
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        instrucaoSql = "SELECT * FROM FUNCIONARIO";
        
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
        	            funcionario = new Funcionario();
        	            funcionario.setId(registros.getInt("Id"));
        	            funcionario.setNome(registros.getString("Nome"));
        	            funcionario.setSexo(registros.getString("Sexo").charAt(0));
        	            funcionario.setSalario(registros.getBigDecimal("Salario"));
        	            funcionario.setPlanoSaude(registros.getBoolean("PlanoSaude"));
        	            funcionarios.add(funcionario);
        	        }
        	    }
                registros.close(); // Libera os recursos usados pelo objeto ResultSet.
                comando.close(); // Libera os recursos usados pelo objeto PreparedStatement.
                // Libera os recursos usados pelo objeto Connection e fecha a conexao com o banco de dados.
                ConnectionDatabase.getConexaoBd().close(); 
            }
        } catch (Exception e) {
        	excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
        	funcionarios = null; // Caso ocorra qualquer excecao.
        }
        return funcionarios; // Retorna o ArrayList de objetos Funcionario.
    }
    
    // Esse metodo e necessario, porque os metodos "recuperaFuncionarios" e "consultaDepartamentos" retornam List<> e nao String.
	public String getExcecao() {
		return excecao;
	}
	
    public List<Departamento> consultaDepartamentos() {
    	Departamento departamento;
    	Funcionario gerente;
        List<Departamento> departamentos = new ArrayList<Departamento>();
        
        instrucaoSql = "SELECT * FROM DEPARTAMENTO";
        
        try {
        	excecao = ConnectionDatabase.conectaBd();
        	if (excecao == null) {
        		comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
        		
        		registros = comando.executeQuery();
        		
        		if (registros.next()) {
        			registros.beforeFirst();
        			while(registros.next()) {
        				departamento = new Departamento();
        				departamento.setId(registros.getInt("Id"));
        				gerente = retornaFuncionarioPorId(registros.getInt("IdFuncGerente"));
        				departamento.setGerente(gerente);
        				departamento.setNomeDepto(registros.getString("NomeDepto"));
        				departamentos.add(departamento);
        			}
        		}
        		registros.close();
        		comando.close();
        		ConnectionDatabase.getConexaoBd().close();
        		return departamentos;
        	}	
        } catch(Exception e) {
        	excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
        	System.out.println(excecao);
        	departamentos = null;
        }
        return departamentos;
    }
    
    public Funcionario retornaFuncionarioPorId(Integer id) {
    	Funcionario funcionario = null;
    	instrucaoSql = "SELECT * FROM FUNCIONARIO WHERE FUNCIONARIO.Id=" + id;
    	
    	try {
    		excecao = ConnectionDatabase.conectaBd();
    		if (excecao == null) {
    			comando2 = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
    			registros2 = comando2.executeQuery();
    			
    			if (registros2.next()) {
    				registros2.beforeFirst();
    				funcionario = new Funcionario();
    				funcionario.setId(registros2.getInt("Id"));
    				funcionario.setNome(registros2.getString("Nome"));
    				funcionario.setSexo(registros2.getString("Sexo").charAt(0));
    				funcionario.setSalario(registros2.getBigDecimal("Salario"));
    				funcionario.setPlanoSaude(registros2.getBoolean("PlanoSaude"));
    			}
    			registros2.close();
    			comando2.close();
    			ConnectionDatabase.getConexaoBd().close();
    		}
    	} catch(Exception e) {
    		excecao = "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
    		funcionario = null;
    	}
    	return funcionario;
    }
}
