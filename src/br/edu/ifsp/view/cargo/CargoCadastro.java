package br.edu.ifsp.view.cargo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.ifsp.controller.CargoController;
import br.edu.ifsp.model.departamento.Departamento;

public class CargoCadastro {
	static Scanner entrada = new Scanner(System.in);
	
	public static void exibeInterface() {
		String descricao;

		System.out.println("\nCADASTRO DE CARGO:");
		System.out.print("DESCRICAO: ");
		descricao = entrada.nextLine();

		List<String> erros = new ArrayList<String>();
		
		// Envia os dados do cargo (informados no formulario) ao controller. 
		// O controller retorna entao um ArrayList contendo os erros encontrados.
		erros = new CargoController().insereCargo(descricao, leDepartamento());
		
		if (erros.get(0) == null) { // Se o primeiro elemento do ArrayList for null.
			System.out.println("Cargo cadastrado com sucesso.\n");
		} else { // Se o primeiro elemento do ArrayList nao for null.
			String mensagem = "Nao foi possivel cadastrar o funcionario:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			System.out.println(mensagem);
		}
	}
	
	public static Departamento leDepartamento() {
		int codDepartamento;
		Departamento departamento = new Departamento();
		
		List<Departamento> departamentos = new ArrayList<Departamento>();
		// Retorna um ArrayList de objetos Departamento, contendo o Id e a Nome dos departamentos cadastrados.
		departamentos = new CargoController().recuperaDepartamentos();		
		String erro = new CargoController().getExcecao();
		if (erro != null) // Caso ocorra qualquer tipo de excecao.
			System.out.println("Nao foi possivel recuperar os dados dos departamentos:\n" + erro);
		
		if (departamentos.size() != 0) { // Se existir pelo menos um departamento cadastrado.
			System.out.println("DEPARTAMENTOS CADASTRADOS: ");
			for (Departamento d : departamentos)
				System.out.println(d.getId() + " - " + d.getNomeDepto());
			
			System.out.print("DEPARTAMENTO (Digite o codigo do departamento): ");
			codDepartamento = Integer.parseInt(entrada.nextLine());
			for (Departamento d : departamentos)
				if (d.getId() == codDepartamento)
					departamento = d;
		}
		return departamento;
	}
}






















