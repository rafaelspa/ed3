package br.edu.ifsp.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDatabase {
	private static Connection conexao; // Atributo que recebera os dados para conexao com o banco de dados.
	
	// String de conexao (Dados: servidor, banco de dados, usuario, senha, uso de SSL e fuso horario).
	private static final String SERVER = "jdbc:mysql://localhost:3306";
	private static final String DATABASE = "/estudante";
	private static final String USER = "?user=root";
	private static final String PASSWORD = "&password=mysql";
	private static final String ALLOW_PUBLIC_KEY_RETRIEVAL = "&allowPublicKeyRetrieval=true";
	
	// O protocolo SSL criptografa o fluxo de dados entre o servidor de banco de dados 
    // e a aplicacao cliente, protegendo-o de ataques externos.
	private static final String USE_SSL = "&useSSL=false";
	
    // Dependendo da versao do MySQL e de como seu servidor esta configurado, tambem
    // pode ser preciso determinar um fuso horario especefico do servidor MySQL.
	private static final String USE_TIMEZONE = "&useTimezone=true";
	private static final String SERVER_TIMEZONE = "&serverTimezone=UTC";
	
	private static final String STRING_CONNECTION = 
			SERVER + DATABASE + USER + ALLOW_PUBLIC_KEY_RETRIEVAL + PASSWORD + USE_SSL + USE_TIMEZONE + SERVER_TIMEZONE;

    public static String conectaBd() { // Abre a conexao com o banco de dados.
        try {
            conexao = DriverManager.getConnection(STRING_CONNECTION); // Atribui os dados de conexao ao objeto "conexao".
        } catch (Exception e) {
        	// Caso ocorra qualquer tipo de excecao.
            return "Tipo de Excecao: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage(); 
        }
        return null; // Caso a conexao ocorra com sucesso.
    }

    public static Connection getConexaoBd() { // Retorna o objeto que contem os dados para conexao com o banco de dados.
        return conexao;
    }
}
