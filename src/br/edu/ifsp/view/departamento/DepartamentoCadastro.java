package br.edu.ifsp.view.departamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.ifsp.controller.DepartamentoController;
import br.edu.ifsp.model.funcionario.Funcionario;

public class DepartamentoCadastro {
	static Scanner entrada = new Scanner(System.in);

	public static void exibeInterface() {
		String nomeDepto;

		System.out.println("\nCADASTRO DE DEPARTAMENTO:");
		System.out.print("NOME DEPARTAMENTO: ");
		nomeDepto = entrada.nextLine();

		List<String> erros = new ArrayList<String>();

		// Envia os dados do departamento (informados no formulario) ao controller.
		// O controller retorna entao um ArrayList contendo os erros encontrados.
		erros = new DepartamentoController().insereDepartamento(nomeDepto, leGerente());

		if (erros.get(0) == null) { // Se o primeiro elemento do ArrayList for null.
			System.out.println("Departamento cadastrado com sucesso.\n");
		} else { // Se o primeiro elemento do ArrayList nao for null.
			String mensagem = "Nao foi possivel cadastrar o departamento:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			System.out.println(mensagem);
		}
	}

	public static Funcionario leGerente() {
		int idFuncGerente;
		Funcionario gerente = new Funcionario();

		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		// Retorna um ArrayList de objetos Funcionario, contendo o Id e a Descricao dos
		// funcionarios cadastrados.
		funcionarios = new DepartamentoController().recuperaFuncionarios();
		String erro = new DepartamentoController().getExcecao();
		if (erro != null) // Caso ocorra qualquer tipo de excecao.
			System.out.println("Nao foi possivel recuperar os dados dos funcionarios:\n" + erro);

		if (funcionarios.size() != 0) { // Se existir pelo menos um funcionario cadastrado.
			System.out.println("FUNCIONARIOS CADASTRADOS: ");
			for (Funcionario f : funcionarios)
				System.out.println(f.getId() + " - " + f.getNome() + " - " + f.getSexo() + " - " + f.getCargo() + " - "
						+ f.getSalario());

			System.out.print("FUNCIONARIO (Digite o codigo do funcionario): ");
			idFuncGerente = Integer.parseInt(entrada.nextLine());
			for (Funcionario f : funcionarios)
				if (f.getId() == idFuncGerente)
					gerente = f;
		}
		return gerente;
	}
}
