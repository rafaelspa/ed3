package br.edu.ifsp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.FuncionarioDao;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.funcionario.Funcionario;
import br.edu.ifsp.model.funcionario.FuncionarioValidacao;

public class FuncionarioController {
	private Funcionario funcionario;
	private List<String> erros;

    public List<String> insereFuncionario(String nome, Character sexo, BigDecimal salario, Boolean planoSaude, Cargo cargo) {
    	recebeDadosFuncionario(null, nome, sexo, salario, planoSaude, cargo);
    	
		// Se nenhum erro de validacao for encontrado, tenta inserir o funcionario no banco.
		if (erros.size() == 0)
			erros.add(new FuncionarioDao().insereFuncionario(funcionario));
		
		// Retorna o ArrayList contendo:
		// - Em caso de sucesso: null na 1a posicao; OU
		// - Em caso de excecao: uma mensagem de excecao na 1a posicao; OU
		// - Em caso de erro de validacao: mensagens de erro iniciando na 1a posicao.
		return erros; 
    }
    
    // Metodo usado pelas operacoes de insercao e alteracao de funcionario.
    public void recebeDadosFuncionario(Integer id, String nome, Character sexo, BigDecimal salario, Boolean planoSaude, Cargo cargo) {
    	funcionario = new Funcionario();
    	erros = new ArrayList<String>();

		// Os metodos set abaixo criam um objeto Funcionario contendo os dados do funcionario informado.
		// Este objeto sera enviado a classe DAO, que fara a insercao de seus dados no banco.
    	funcionario.setId(id);
    	funcionario.setNome(nome);
		funcionario.setSexo(sexo);
		funcionario.setSalario(salario);
		funcionario.setPlanoSaude(planoSaude);
		funcionario.setCargo(cargo);
        
		// Retorna um ArrayList contendo os erros encontrados em regras de validacao e de negocios.
		erros = FuncionarioValidacao.validaFuncionario(funcionario);
    }
    
    public List<Cargo> recuperaCargos() {
    	// Recupera os cargos cadastrados no banco de dados para que sejam exibidos no campo Cargo.
		return new FuncionarioDao().recuperaCargos();
    }
    
    public String getExcecao() {
    	// Retorna a excecao lancada ao recuperar os cargos (ao abrir a interface "Cadastro de Funcionario").
    	return new FuncionarioDao().getExcecao();
    }

	public List<Funcionario> recuperaFuncionarios() {
		return new FuncionarioDao().consultaFuncionarios();
	}
}

