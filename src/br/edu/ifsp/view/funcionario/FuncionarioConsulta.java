package br.edu.ifsp.view.funcionario;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.funcionario.Funcionario;

public class FuncionarioConsulta {

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
					+ " - " + (funcionario.isPlanoSaude() ? "Tem" : "Nao tem")
					+ (funcionario.getCargo() != null ? funcionario.getCargo().getDescricao() : "Sem cargo"));
		});
		System.out.println();
	}
}
