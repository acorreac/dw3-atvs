package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.entitades.Servico;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico>{
	@Override
	public void adicionarLink(List<Servico> lista) {
		for(Servico servico:lista) {
			long id = servico.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.encontrarServico(id))
					.withRel("Visualizar servico de id " + id);
			servico.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Servico objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.encontrarServicos())
				.withRel("Lista de servicos");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Servico objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.atualizarServico(objeto.getId(), objeto))
				.withRel("Atualizar servico de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Servico objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.deletarServico(objeto.getId()))
				.withRel("Excluir servico de id " + objeto.getId());
		objeto.add(linkProprio);
	}

}
