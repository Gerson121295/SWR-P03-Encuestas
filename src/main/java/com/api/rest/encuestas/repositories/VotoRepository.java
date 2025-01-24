package com.api.rest.encuestas.repositories;

import com.api.rest.encuestas.model.Voto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long> {

    //Consulta nativa (SQL puro) personalizada - selecionar los votos
    /*
    1. select v.*   :Selecciona todas las columnas de la tabla Voto (v.*).
    2. from Opcion o, Voto v   :Especifica las tablas involucradas en la consulta: Opcion (alias o) y Voto (alias v).
    3. where o.encuesta_id = ?1   :Filtra las filas de la tabla Opcion para que coincidan con el ID de encuesta (?1 es el primer parámetro que recibe el método).
    4. and v.opcion_id = o.opcion_id   :Establece una relación entre las tablas Voto y Opcion, asegurando que el voto esté asociado a una opción válida.

    Obtiene todos los votos (Voto) asociados con una encuesta específica (encuestaId) a través de las opciones (Opcion) vinculadas.
     */
    @Query(value="select v.* from Opcion o, Voto v where o.encuesta_id=?1 and v.opcion_id=o.opcion_id", nativeQuery = true)
    public Iterable<Voto> findByEncuestaId(Long encuestaId); //encuestaId es el primer parametro que se recibe seria ?1

}
