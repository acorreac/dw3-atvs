package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco>{
	
	@Override
	public void adicionarLink(List<Endereco> lista) {
		for (Endereco endereco : lista) {
			long id = endereco.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.obterEndereco(id))
					.withSelfRel();
			endereco.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.obterEndereco(objeto.getId()))
				.withRel("Enderecos");
		objeto.add(linkProprio);
	}
	
	@Override
	public void adicionarLinkUpdate(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.atualizarEndereco(objeto))
				.withRel("Atualiza enderecos");
		objeto.add(linkProprio);
	}
	
	@Override
	public void adicionarLinkDelete(Endereco objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.deletarEndereco(objeto.getId()))
				.withRel("Deleta endereco");
		objeto.add(linkProprio);
	}
}
