package br.edu.ifsp.view.funcionario;

import java.util.List;
import java.util.Scanner;

import br.edu.ifsp.controller.FuncionarioController;

public class FuncionarioExclusao {
	static Scanner scanner = new Scanner(System.in);

	public static void exibeInterface() {
		
		System.out.println("EXCLUSAO DE FUNCIONARIO:");
		System.out.print("Informe o ID do funcionario a ser excluido: ");
		Integer idFuncionario = Integer.valueOf(scanner.nextInt());
		
		List<String> erros = new FuncionarioController().excluiFuncionario(idFuncionario);
		
		if (erros.get(0) == null) { 
			System.out.println("Funcionario excluido com sucesso.\n");
		} else { 
			String mensagem = "Nao foi possivel excluir o funcionario:\n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			System.out.println(mensagem);
		}
	}
}
