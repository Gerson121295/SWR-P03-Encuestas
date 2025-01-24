package com.api.rest.encuestas.repositories;


import com.api.rest.encuestas.model.Encuesta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncuestaRepository extends CrudRepository<Encuesta, Long> {
}
