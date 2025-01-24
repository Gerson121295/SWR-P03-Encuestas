package com.api.rest.encuestas.controllers.v1;

import com.api.rest.encuestas.model.Voto;
import com.api.rest.encuestas.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//Sin versionado de la API
//@RestController
//@RequestMapping("/encuesta")

//Con Versionado de la API
@RestController("VotoControllerV1") //se agrego ("EncuestaControllerV1")  para definir la version v1
@RequestMapping("/v1/encuesta")
public class VotoController {

    @Autowired
    private VotoRepository votoRepository;

    @PostMapping("/votos/{encuestaId}")  //POST: http://localhost:8080/encuesta/votos/1
    public ResponseEntity<?> crearVoto(@PathVariable Long encuestaId, @RequestBody Voto voto){

        voto = votoRepository.save(voto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(voto.getId())
                .toUri());

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    /* //Crear un voto
    {
        "opcion": {
            "id": 61,
            "value": "TypeScript"
        }
    }
     */

    @GetMapping("/votos/{encuestaId}")  //GET: http://localhost:8080/encuesta/votos/1
    public Iterable<Voto> listarTodosLosVotos(@PathVariable Long encuestaId){
        return votoRepository.findByEncuestaId(encuestaId);
    }



}
