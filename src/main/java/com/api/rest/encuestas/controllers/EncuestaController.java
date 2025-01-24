package com.api.rest.encuestas.controllers;

import com.api.rest.encuestas.exception.ResourceNotFoundException;
import com.api.rest.encuestas.model.Encuesta;
import com.api.rest.encuestas.repositories.EncuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/encuesta")
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping   ////GET: http://localhost:8080/encuesta
    public ResponseEntity<Iterable<Encuesta>> listarTodasEncuestas(){
        return new ResponseEntity<>(encuestaRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping  //POST: http://localhost:8080/encuesta
    public ResponseEntity<?> crearEncuesta(@Valid @RequestBody Encuesta encuesta){ //La anotacion @Valid realiza la validaciones que se defiinio en Encuesta.
       encuesta = encuestaRepository.save(encuesta);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(encuesta.getId())
                .toUri();
        httpHeaders.setLocation(newEncuestaUri);

       return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    /**  //Creacion de una Pregunta.
     {
     "pregunta": "¿Cual es tu lenguaje de programacion favorito?",
     "opciones":[
         {"value":"Java"},
         {"value":"C"},
         {"value":"C++"},
         {"value":"JavaScript"},
         {"value":"Go"}
     ]
     }
     */

    @GetMapping("/{encuesta_Id}")  //GET: - http://localhost:8080/encuesta/1
    public ResponseEntity<?> ObtenerEncuesta(@PathVariable Long encuesta_Id){

        verifyEncuesta(encuesta_Id); //llama la funcion verificar encuesta si existe

        Optional<Encuesta> encuesta = encuestaRepository.findById(encuesta_Id); //Optional es una clase que retorna true o false si encuentra la encuesta

        if(encuesta.isPresent()){
            return new ResponseEntity<>(encuesta, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{encuesta_Id}")
    public ResponseEntity<?> actualizarEncuesta(@Valid @PathVariable Long encuesta_Id, @RequestBody Encuesta encuesta){
        verifyEncuesta(encuesta_Id); //llama la funcion verificar encuesta si existe

        //Valida si se encuentra la encuesta con Optional que retorna true o false
        Optional<Encuesta> encuestaActual = encuestaRepository.findById(encuesta_Id);

        if(encuestaActual.isPresent()){ //si encuestaActual es true
            encuesta.setId(encuesta_Id); //le agregamos el id que se recibe encuesta_Id a la encuesta a actualizar para evitar crear una nueva
            encuestaRepository.save(encuesta); //llama al metodo save de encuestaRepository al cual se le envia la encuesta que contiene los datos a actualizar
            return new ResponseEntity<>(encuesta, HttpStatus.OK); //retora la encuesta y el estatus de ok
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{encuesta_Id}")
    public ResponseEntity<?> eliminarEncuesta(@PathVariable Long encuesta_Id){
        verifyEncuesta(encuesta_Id);
        encuestaRepository.deleteById(encuesta_Id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    //Exception personalizada - Verificar si se encuentra la encuesta
    protected void verifyEncuesta(Long encuesta_Id){
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuesta_Id);
        if(!encuesta.isPresent()){
            throw new ResourceNotFoundException("Encuesta con el ID:" + encuesta_Id + " no encontrada");
        }
    }

}
