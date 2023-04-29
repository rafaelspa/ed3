package br.edu.ifsp.view.funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.funcionario.Funcionario;

public class FuncionarioConsulta {
	
	static Scanner scanner = new Scanner(System.in);

	public static void exibeInterface() {
		System.out.println("\nFUNCIONARIOS CADASTRADOS:");

		List<Funcionario> funcionarios = new ArrayList<Funcionario>();

		funcionarios = new FuncionarioController().recuperaFuncionarios();

		if (funcionarios == null || funcionarios.isEmpty()) {
			System.out.println("\nNAO HA FUNCIONARIOS CADASTRADOS\n");
			return;
		}
		funcionarios.forEach(funcionario -> {
			System.out.println(funcionario.getId() + " - " + funcionario.getNome() + " - " + funcionario.getSexo()
					+ " - " + (funcionario.isPlanoSaude() ? "Tem" : "Nao tem") + " - "
					+ (funcionario.getCargo() != null ? funcionario.getCargo().getDescricao() : "Sem cargo"));
		});
		System.out.println();

		exibeOpcoesAlterarExcluir();
	}

	public static void exibeOpcoesAlterarExcluir() {
		int opcao = -1;
		while (opcao != 0) {
			System.out.println("ALTERACAO / EXCLUSAO DE FUNCIONARIO:");
			System.out.println("1) Alterar");
			System.out.println("2) Excluir");
			System.out.print("Digite uma opcao (0 para voltar): ");
			opcao = scanner.nextInt();
			System.out.println();
			switch (opcao) {
				case 0: {
					return;
				}
				case 1: {
					FuncionarioAlteracao.exibeInterface();
					
					break;
				}
				case 2: {
					FuncionarioExclusao.exibeInterface();
					break;
				} default:
					System.out.print("\nDigite uma opcao valida. ");
					System.out.println();
				}
			}
	}
}
