package br.edu.ifsp.dao;

import java.sql.PreparedStatement;

public class GenericDao {
    private PreparedStatement comando; // Atributo usado para preparar e executar instrucoes SQL.
    /**
     * @param instrucaoSql Instrucao SQL a ser executada.
     * @param parametros Valores dos campos da instrucao SQL. As reticencias no tipo Object 
     * indicam que "parametros" pode receber um numero variavel de argumentos Object. 
     */
    protected String insere(String instrucaoSql, Object... parametros) {
    	try {
    		String excecao = ConnectionDatabase.conectaBd(); // Abre a conexao com o banco de dados.
    		if (excecao == null) {
    			// Obtem os dados de conexao com o banco de dados e prepara a instrucao SQL.
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
    			
    	    	// Associa cada parametro Object recebido ao objeto "comando".
    	        for (int i = 0; i < parametros.length; i++)
    	        	// 1o argumento: posicao do parametro na instrucao SQL; 2o argumento: parametro.
    	        	// Para objetos Funcionario: 1) Nome, 2) Sexo, 3) Salario, 4) PlanoSaude, 5) IdCargo
    	        	comando.setObject(i + 1, parametros[i]);

    	        comando.execute(); // Executa a instrucao SQL.
    	        
    	        comando.close(); // Libera os recursos usados pelo objeto PreparedStatement.
    	        // Libera os recursos usados pelo objeto Connection e fecha a conexao com o banco de dados.
                ConnectionDatabase.getConexaoBd().close();
    		} else
        		return excecao; // Caso ocorra excecao ao tentar conectar com o banco de dados.
        } catch (Exception e) {
        	// Caso ocorra qualquer tipo de excecao.
            return "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage(); 
        }
        return null; // Se o registro foi inserido com sucesso.
    }
}
