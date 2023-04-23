package br.edu.ifsp.view.cargo;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controller.CargoController;
import br.edu.ifsp.model.cargo.Cargo;

public class CargoConsulta {
	
	public static void exibeInterface() {
		System.out.println("\nCARGOS CADASTRADOS:");
		
		List<Cargo> cargos = new ArrayList<Cargo>();
		
		cargos = new CargoController().recuperaCargos();
		
		if (cargos == null || cargos.isEmpty()) {
			System.out.println("\nNAO HA CARGOS CADASTRADOS\n");
			return;
		}
		cargos.forEach(cargo -> {
			System.out.println(
					cargo.getId() + " - " + cargo.getDescricao() + " - " + (cargo.getDepartamento() != null ? cargo.getDepartamento().getNomeDepto() : "Sem departamento"));
		});
		System.out.println();
	}
}
