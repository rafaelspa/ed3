package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.CargoDao;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.cargo.CargoValidacao;
import br.edu.ifsp.model.departamento.Departamento;

public class CargoController {
	private Cargo cargo;
	private List<String> erros;

    public List<String> insereCargo(String descricao, Departamento departamento) {
    	recebeDadosCargo(null, descricao, departamento);
    	
		// Se nenhum erro de validacao for encontrado, tenta inserir o cargo no banco.
		if (erros.size() == 0)
			erros.add(new CargoDao().insereCargo(cargo));
		
		// Retorna o ArrayList contendo:
		// - Em caso de sucesso: null na 1a posicao; OU
		// - Em caso de excecao: uma mensagem de excecao na 1a posicao; OU
		// - Em caso de erro de validacao: mensagens de erro iniciando na 1a posicao.
		return erros; 
    }
    
    // Metodo usado pelas operacoes de insercao e alteracao de cargo.
    public void recebeDadosCargo(Integer id, String descricao, Departamento departamento) {
    	cargo = new Cargo();
    	erros = new ArrayList<String>();

		// Os metodos set abaixo criam um objeto Cargo contendo os dados do cargo informado.
		// Este objeto sera enviado a classe DAO, que fara a insercao de seus dados no banco.
    	cargo.setId(id);
    	cargo.setDescricao(descricao);
		cargo.setDepartamento(departamento);
        
		// Retorna um ArrayList contendo os erros encontrados em regras de validacao e de negocios.
		erros = CargoValidacao.validaCargo(cargo);
    }
    
    public List<Departamento> recuperaDepartamentos() {
    	// Recupera os departamentos cadastrados no banco de dados para que sejam exibidos no campo Departamento.
		return new CargoDao().recuperaDepartamentos();
    }
    
    public String getExcecao() {
    	// Retorna a excecao lancada ao recuperar os departamentos (ao abrir a interface "Cadastro de Cargo").
    	return new CargoDao().getExcecao();
    }
}

