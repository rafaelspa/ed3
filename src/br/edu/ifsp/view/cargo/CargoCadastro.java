package br.edu.ifsp.view.cargo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.cargo.Cargo;

public class CargoCadastro {
	static Scanner entrada = new Scanner(System.in);
	
	public static void exibeInterface() {
		String nome;
		Character sexo = null;
		BigDecimal salario = null;
		boolean planoSaude;

		System.out.println("\nCADASTRO DE FUNCION�RIO:");
		System.out.print("NOME: ");
		nome = entrada.nextLine();
		System.out.print("SEXO (Digite 'm' ou 'f'): ");
		sexo = entrada.nextLine().toUpperCase().charAt(0);
		System.out.print("SAL�RIO (R$): ");
		salario = new BigDecimal(entrada.nextLine());
		System.out.print("PLANO DE SA�DE (Digite 's' ou 'n'): ");
		if (entrada.nextLine().equals("s"))
			planoSaude = true;
		else
			planoSaude = false;

		List<String> erros = new ArrayList<String>();
		
		// Envia os dados do funcion�rio (informados no formul�rio) ao controller. 
		// O controller retorna ent�o um ArrayList contendo os erros encontrados.
		erros = new FuncionarioController().insereFuncionario(nome,
												              sexo,
												              salario,
										                      planoSaude,
										                      leCargo());
		
		if (erros.get(0) == null) { // Se o primeiro elemento do ArrayList for null.
			System.out.println("Funcion�rio cadastrado com sucesso.\n");
		} else { // Se o primeiro elemento do ArrayList n�o for null.
			String mensagem = "N�o foi poss�vel cadastrar o funcion�rio:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			System.out.println(mensagem);
		}
	}
	
	public static Cargo leCargo() {
		int codCargo;
		Cargo cargo = new Cargo();
		
		List<Cargo> cargos = new ArrayList<Cargo>();
		// Retorna um ArrayList de objetos Cargo, contendo o Id e a Descri��o dos cargos cadastrados.
		cargos = new FuncionarioController().recuperaCargos();		
		String erro = new FuncionarioController().getExcecao();
		if (erro != null) // Caso ocorra qualquer tipo de exce��o.
			System.out.println("N�o foi poss�vel recuperar os dados dos cargos:\n" + erro);
		
		if (cargos.size() != 0) { // Se existir pelo menos um cargo cadastrado.
			System.out.println("CARGOS CADASTRADOS: ");
			for (Cargo c : cargos)
				System.out.println(c.getId() + " - " + c.getDescricao());
			
			System.out.print("CARGO (Digite o c�digo do cargo): ");
			codCargo = Integer.parseInt(entrada.nextLine());
			for (Cargo c : cargos)
				if (c.getId() == codCargo)
					cargo = c;
		}
		return cargo;
	}
}






















