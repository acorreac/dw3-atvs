package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private TelefoneSelecionador selecionarTelefone;

	@GetMapping("/telefone/{id}")
	public Telefone obterTelefone(@PathVariable Long id) {
		List<Telefone> telefones = repositorio.findAll();
		return selecionarTelefone.selecionar(telefones, id);
	}

	@GetMapping("/telefones")
	public List<Telefone> obterTelefones() {
		List<Telefone> telefones = repositorio.findAll();
		return telefones;
	}

	@PostMapping("/cadastrar")
	public void cadastrarTelefone(@RequestBody Telefone telefone) {
		repositorio.save(telefone);
	}

	@PutMapping("/atualizar")
	public void atualizarTelefone(@RequestBody Telefone atualizacao) {
		Telefone telefone = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefone, atualizacao);
		repositorio.save(telefone);
	}

	@DeleteMapping("/excluir")
	public void excluirTelefone(@RequestBody Telefone exclusao) {
		List<Telefone> telefones = repositorio.findAll();
		Long telefoneId = exclusao.getId();
		Telefone telefone = selecionarTelefone.selecionar(telefones, telefoneId);
		List<Cliente> clientes = clienteRepositorio.findAll();

		for (Cliente cliente : clientes) {
			cliente.getTelefones().remove(telefone);
			repositorio.delete(telefone);
		}
	}
	
}
