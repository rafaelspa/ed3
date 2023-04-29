package br.edu.ifsp.view.funcionario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.cargo.Cargo;

public class FuncionarioAlteracao {
	static Scanner scanner = new Scanner(System.in);

	public static void exibeInterface() {
		Boolean novoPlanoSaude;
		System.out.println("\nALTERAÇÃO DE FUNCIONÁRIO:");
		System.out.print("Informe o ID do funcionário a ser alterado: ");
		Integer idFuncionario = Integer.valueOf(scanner.nextInt());
		scanner.nextLine();
		System.out.print("NOME: ");
		String novoNome = scanner.nextLine();
		System.out.print("SEXO (Digite 'm' ou 'f'): ");
		Character novoSexo = scanner.nextLine().charAt(0);
		System.out.print("SALÁRIO (R$): ");
		BigDecimal novoSalario = scanner.nextBigDecimal();
		scanner.nextLine();
		System.out.print("PLANO DE SAÚDE (Digite 's' ou 'n'): ");
		if (scanner.nextLine().equals("s")) {
			novoPlanoSaude = Boolean.TRUE;
		} else {
			novoPlanoSaude = Boolean.FALSE;
		}
		List<String> erros = new FuncionarioController().alteraFuncionario(idFuncionario, novoNome, novoSexo, novoSalario,
				novoPlanoSaude, leCargo());

		if (erros.get(0) == null) { 
			System.out.println("Funcionario alterado com sucesso.\n");
		} else { 
			String mensagem = "Nao foi possivel alterar o funcionario:\n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			System.out.println(mensagem);
		}
	}
	
	public static Cargo leCargo() {
		int codCargo;
		Cargo cargo = new Cargo();
		
		List<Cargo> cargos = new ArrayList<Cargo>();
		// Retorna um ArrayList de objetos Cargo, contendo o Id e a Descricao dos cargos cadastrados.
		cargos = new FuncionarioController().recuperaCargos();		
		String erro = new FuncionarioController().getExcecao();
		if (erro != null) // Caso ocorra qualquer tipo de excecao.
			System.out.println("Nao foi possivel recuperar os dados dos cargos:\n" + erro);
		
		if (cargos.size() != 0) { // Se existir pelo menos um cargo cadastrado.
			System.out.println("CARGOS CADASTRADOS: ");
			for (Cargo c : cargos)
				System.out.println(c.getId() + " - " + c.getDescricao());
			
			System.out.print("CARGO (Digite o codigo do cargo): ");
			codCargo = Integer.parseInt(scanner.nextLine());
			for (Cargo c : cargos)
				if (c.getId() == codCargo)
					cargo = c;
		}
		return cargo;
	}

}
