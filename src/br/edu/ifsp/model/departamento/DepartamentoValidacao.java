package br.edu.ifsp.model.departamento;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoValidacao {
	private static List<String> errosValidacao; // List para armazenar as mensagens de erro de validacao.
	
	// Valida os dados informados conforme as regras abaixo.
	public static List<String> validaDepartamento(Departamento departamento){
		errosValidacao = new ArrayList<>();
		
		// Validacao do campo NomeDepto.
		if (!departamento.getNomeDepto().equals("")) {
			if (departamento.getNomeDepto().length() < 5 || departamento.getNomeDepto().length() > 100)
				errosValidacao.add("* O Nome do departamento deve ter entre 5 e 100 caracteres.");
		} else {
			errosValidacao.add("* O Nome do departamento nao foi informado.");
		}
		
		// Validacao do campo IdFuncGerente (para o caso de nao existir gerente cadastrado).
		if (departamento.getGerente() == null)
			errosValidacao.add("* O gerente nao foi informado.");
		
		return errosValidacao; // Retorna o ArrayList contendo as mensagens de erro de validacao.
	}
}