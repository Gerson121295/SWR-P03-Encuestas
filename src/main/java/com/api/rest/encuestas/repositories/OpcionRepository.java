package com.api.rest.encuestas.repositories;

import com.api.rest.encuestas.model.Opcion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionRepository extends CrudRepository<Opcion, Long> {

}
