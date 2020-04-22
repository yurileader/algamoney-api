package com.example.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;
	
	public Pessoa atualizar (Pessoa pessoa, Long codigo) {
		Pessoa pessoaSalva = encontrarPessoa(codigo);
		/**BeansUtils pode ser usado para ajudar a tratar os dados para atualziar
		 * Source: A fonte dos dados - no caso da classe pessoas
		 * target: Para onde irei mandar os dados - no caso para minha variavel pessoaSalva
		 * ignoreProperties: qual dado devo ignorar - no caso o codigo que é codigo*/
		//BeanUtils.copyProperties(source, target, ignoreProperties);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		return pessoaRepository.save(pessoaSalva);
	}

	//Atualiza apenas o campo ativo da classe pessoa 
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {

		Pessoa pessoaSalva = encontrarPessoa(codigo);
		
		pessoaSalva.setAtivo(ativo);
		
		pessoaRepository.save(pessoaSalva);
	}

	
	
	public Pessoa encontrarPessoa(Long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
}
