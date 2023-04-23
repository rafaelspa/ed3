package br.edu.ifsp.view.departamento;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controller.DepartamentoController;
import br.edu.ifsp.model.departamento.Departamento;

public class DepartamentoConsulta {
	
	public static void exibeInterface() {
		System.out.println("\nDEPARTAMENTOS CADASTRADOS:");
		
		List<Departamento> departamentos = new ArrayList<Departamento>();
		
		departamentos = new DepartamentoController().recuperaDepartamentos();
		
		if (departamentos == null || departamentos.isEmpty()) {
			System.out.println("\nNAO HA DEPARTAMENTOS CADASTRADOS\n");
			return;
		}
		departamentos.forEach(departamento -> {
			System.out.println(
					departamento.getId() + " - " + departamento.getNomeDepto() + " - " + (departamento.getGerente() != null ? departamento.getGerente().getId() : "Sem gerente"));
		});
		System.out.println();
	}
}
