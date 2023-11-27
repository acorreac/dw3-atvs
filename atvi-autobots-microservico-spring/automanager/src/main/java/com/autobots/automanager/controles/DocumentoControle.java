package com.autobots.automanager.controles;

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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private DocumentoSelecionador selecionadorDocumento;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@GetMapping("/documento/{id}")
	public Documento obterDocumento(@PathVariable Long id) {
		List<Documento> documentos = repositorio.findAll();
		
		return selecionadorDocumento.selecionar(documentos, id);
	}
	
	@GetMapping("/documentos")
	public List<Documento> obterDocumentos() {
		List<Documento> documentos = repositorio.findAll();
		
		return documentos;
	}
	
	@PostMapping("/cadastrar")
	public void cadastrarDocumento(@RequestBody Documento documento) {
			repositorio.save(documento);
	}
	
	@PutMapping("/atualizar")
	public void atualizarDocumento(@RequestBody Documento atualizacao) {
		Documento documento = repositorio.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documento, atualizacao);
		repositorio.save(documento);
	}
	
	@DeleteMapping("/excluir")
	public void excluirDocumento(@RequestBody Documento exclusao) {
		List<Documento> documentos = repositorio.findAll();
		Long documentoId = exclusao.getId();
		Documento documento = selecionadorDocumento.selecionar(documentos, documentoId);
		List<Cliente> clientes = clienteRepositorio.findAll();

		for (Cliente cliente : clientes) {
			cliente.getDocumentos().remove(documento);
		}
		repositorio.delete(documento);
	}
}
