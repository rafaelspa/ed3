package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.DepartamentoDao;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.departamento.DepartamentoValidacao;
import br.edu.ifsp.model.funcionario.Funcionario;

public class DepartamentoController {
	private Departamento departamento;
	private List<String> erros;

    public List<String> insereDepartamento(String nomeDepto, Funcionario gerente) {
    	recebeDadosDepartamento(null, nomeDepto, gerente);
    	
		// Se nenhum erro de validacao for encontrado, tenta inserir o departamento no banco.
		if (erros.size() == 0)
			erros.add(new DepartamentoDao().insereDepartamento(departamento));
		
		// Retorna o ArrayList contendo:
		// - Em caso de sucesso: null na 1a posicao; OU
		// - Em caso de excecao: uma mensagem de excecao na 1a posicao; OU
		// - Em caso de erro de validacao: mensagens de erro iniciando na 1a posicao.
		return erros; 
    }
    
    // Metodo usado pelas operacoes de insercao e alteracao de funcionario.
    public void recebeDadosDepartamento(Integer id, String nomeDepto, Funcionario gerente) {
    	departamento = new Departamento();
    	erros = new ArrayList<String>();

		// Os metodos set abaixo criam um objeto Departamento contendo os dados do departamento informado.
		// Este objeto sera enviado a classe DAO, que fara a insercao de seus dados no banco.
    	departamento.setId(id);
    	departamento.setNomeDepto(nomeDepto);
    	departamento.setGerente(gerente);
        
		// Retorna um ArrayList contendo os erros encontrados em regras de validacao e de negocios.
		erros = DepartamentoValidacao.validaDepartamento(departamento);
    }
    
    public List<Funcionario> recuperaFuncionarios() {
    	// Recupera os funcionarios cadastrados no banco de dados para que sejam exibidos no campo Funcionario.
		return new DepartamentoDao().recuperaFuncionarios();
    }
    
    public String getExcecao() {
    	// Retorna a excecao lancada ao recuperar os cargos (ao abrir a interface "Cadastro de Funcionario").
    	return new DepartamentoDao().getExcecao();
    }
}

