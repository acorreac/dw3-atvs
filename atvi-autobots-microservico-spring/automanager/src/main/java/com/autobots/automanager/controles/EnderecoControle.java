package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	
	@Autowired
	public EnderecoRepositorio repositorio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private ClienteSelecionador clienteSelecionador;
	
	@GetMapping("/endereco/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		Endereco endereco = repositorio.findById(id).get();
		return endereco;
	}
	
	@GetMapping("/enderecos")
	public List<Endereco> obterEnderecos() {
		List<Endereco> enderecos = repositorio.findAll();
		return enderecos;
	}
	
	@PutMapping("/atualizar")
	public void atualizarEndereco(@RequestBody Endereco atualizacao) {
		Endereco endereco = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(endereco, atualizacao);
		repositorio.save(endereco);
	}
	
	@DeleteMapping("/deletar")
	public void deletarEndereco(@RequestBody Cliente cliente) {
		List<Cliente> clientes = clienteRepositorio.findAll();
		Cliente clienteSelecionado = clienteSelecionador.selecionar(clientes, cliente.getId());
		repositorio.delete(clienteSelecionado.getEndereco());
		clienteSelecionado.setEndereco(null);
		
		clienteRepositorio.save(clienteSelecionado);
	}
}
