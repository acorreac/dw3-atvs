package com.autobots.automanager.models;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.entitades.CredencialCodigoBarra;

@Component
public class AddLinkCredencialCodigoDeBarra implements AdicionadorLink<CredencialCodigoBarra>{
	@Override
	public void adicionarLink(List<CredencialCodigoBarra> lista) {
		for(CredencialCodigoBarra credencial:lista) {
			long id = credencial.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialControle.class)
							.encontrarCredencialCodigoBarraPorId(id))
					.withRel("Visualizar credencial de id " + id);
			credencial.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(CredencialCodigoBarra objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.encontrarCredenciaisCodigoBarras())
				.withRel("Lista de credenciais");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkUpdate(CredencialCodigoBarra objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.atualizarCredencialCodigoBarra(objeto.getId(), objeto))
				.withRel("Atualizar credencial de id " + objeto.getId());
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkDelete(CredencialCodigoBarra objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.deletarCredencialCodigoBarra(objeto.getId()))
				.withRel("Excluir credencial de id " + objeto.getId());
		objeto.add(linkProprio);
	}
}
