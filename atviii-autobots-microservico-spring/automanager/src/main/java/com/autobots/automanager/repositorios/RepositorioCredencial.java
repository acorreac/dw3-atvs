package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entitades.Credencial;

public interface RepositorioCredencial extends JpaRepository<Credencial, Long>{

}
