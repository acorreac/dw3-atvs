package com.autobots.automanager.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entitades.Endereco;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco>{

	@Override
	public void adicionarLink(List<Endereco> lista) {
		for(Endereco endereco:lista) {
			long id = endereco.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.encontrarEndereco(id))
					.withRel("Visualizar endereco de id " + id);
			endereco.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.encontrarEnderecos())
				.withRel("Lista de enderecos");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.atualizarEndereco(objeto.getId(), objeto))
				.withRel("Atualizar endereco de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.deletarEndereco(objeto.getId()))
				.withRel("Excluir endereco de id " + objeto.getId());
		objeto.add(linkProprio);
	}
}
