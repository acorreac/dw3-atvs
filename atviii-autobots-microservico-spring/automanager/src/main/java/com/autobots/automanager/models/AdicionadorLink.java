package com.autobots.automanager.models;

import java.util.List;

public interface AdicionadorLink<T> {
	public void adicionarLink(List<T> lista);
	public void adicionarLink(T objeto);
	public void adicionarLinkUpdate(T objeto);
	public void adicionarLinkDelete(T objeto);
}
