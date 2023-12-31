package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.entitades.Email;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<Email>{
	
	@Override
	public void adicionarLink(List<Email> lista) {
		for(Email email:lista) {
			long id = email.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.encontrarEmail(id))
					.withRel("Visualizar email de id " + id);
			email.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Email objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.encontrarEmails())
				.withRel("Lista de emails");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(Email objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.atualizarEmail(objeto.getId(), objeto))
				.withRel("Atualizar email de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(Email objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.deletarEmail(objeto.getId()))
				.withRel("Excluir email de id " + objeto.getId());
		objeto.add(linkProprio);
	}

}
