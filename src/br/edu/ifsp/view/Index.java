package br.edu.ifsp.view;

import java.util.Scanner;

import br.edu.ifsp.view.funcionario.FuncionarioCadastro;

public class Index {
	public static void main(String[] args) {
		int opcaoCadastro = 0, opcaoOperacao = 0;
		
		Scanner entrada = new Scanner(System.in);

		do {
			System.out.println("CADASTROS:");
			System.out.println("1) Cadastro de Departamentos");
			System.out.println("2) Cadastro de Cargos");
			System.out.println("3) Cadastro de Funcionários");
			System.out.print("Digite uma opção (0 para sair): ");
	
			opcaoCadastro = Integer.parseInt(entrada.nextLine());
			
			switch (opcaoCadastro) {
			case 1:
			case 2:
			case 3:
				do {
					System.out.println("\nOPERAÇÕES:");
					System.out.println("1) Cadastrar");
					System.out.println("2) Consultar");
					System.out.print("Digite uma opção: ");
				
				    opcaoOperacao = Integer.parseInt(entrada.nextLine());
				    
					switch (opcaoOperacao) {
					case 1:
					case 2:	
						defineOperacao(opcaoCadastro, opcaoOperacao);
						break;
					default:
						if (opcaoOperacao != 1 && opcaoOperacao != 2)
							System.out.println("Digite uma opção válida.");
						break;
					}
				} while (opcaoOperacao != 1 && opcaoOperacao != 2);
				break;
			default:
				if (opcaoCadastro != 0)
					System.out.println("Digite uma opção válida.\n");
				else
					System.out.println("Programa encerrado.");
				break;
			}
		} while (opcaoCadastro != 0);

		entrada.close();
	}
	
	public static void defineOperacao(int opcaoCadastro, int opcaoOperacao) {
		if (opcaoCadastro == 1 && opcaoOperacao == 1)
			System.out.println(opcaoCadastro + " - " + opcaoOperacao + "\n");
		    // DepartamentoCadastro.exibeInterface();
		else if (opcaoCadastro == 1 && opcaoOperacao == 2)
			System.out.println(opcaoCadastro + " - " + opcaoOperacao + "\n");
		    // DepartamentoConsulta.exibeInterface();
		else if (opcaoCadastro == 2 && opcaoOperacao == 1)
			System.out.println(opcaoCadastro + " - " + opcaoOperacao + "\n");
		    // CargoCadastro.exibeInterface();
		else if (opcaoCadastro == 2 && opcaoOperacao == 2)
			System.out.println(opcaoCadastro + " - " + opcaoOperacao + "\n");
		    // CargoConsulta.exibeInterface();
		else if (opcaoCadastro == 3 && opcaoOperacao == 1)
		    FuncionarioCadastro.exibeInterface();
		else if (opcaoCadastro == 3 && opcaoOperacao == 2)
			System.out.println(opcaoCadastro + " - " + opcaoOperacao + "\n");
		    // FuncionarioConsulta.exibeInterface();
	}
}