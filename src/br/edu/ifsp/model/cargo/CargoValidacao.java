package br.edu.ifsp.model.cargo;

import java.util.ArrayList;
import java.util.List;

public class CargoValidacao {
	private static List<String> errosValidacao; // List para armazenar as mensagens de erro de validacao.
	
	// Valida os dados informados conforme as regras abaixo.
	public static List<String> validaCargo(Cargo cargo){
		errosValidacao = new ArrayList<>();
		
		// Validacao do campo Descricao.
		if (!cargo.getDescricao().equals("")) {
			if (cargo.getDescricao().length() < 5 || cargo.getDescricao().length() > 100)
				errosValidacao.add("* A descricao deve ter entre 5 e 100 caracteres.");
		} else {
			errosValidacao.add("* A descricao nao foi informada.");
		}
			
		// Validacao do campo Departamento (para o caso de nao existirem departamentos cadastrados).
		if (cargo.getDepartamento() == null)
			errosValidacao.add("* O Departamento nao foi informado.");
		
		return errosValidacao; // Retorna o ArrayList contendo as mensagens de erro de validacao.
	}
}