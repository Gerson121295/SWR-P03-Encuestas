package com.api.rest.encuestas.controllers.v1;


import com.api.rest.encuestas.dto.OpcionCount;
import com.api.rest.encuestas.dto.VotoResult;
import com.api.rest.encuestas.model.Voto;
import com.api.rest.encuestas.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("EncuestaControllerV1_Computer") //se agrego ("EncuestaControllerV1")  para definir la version v1
@RequestMapping("/v1/encuesta")
public class ComputeResultController {

    @Autowired
    private VotoRepository votoRepository;

    @GetMapping("/calcularResultado")  //GET: http://localhost:8080/encuesta/calcularResultado?encuestaId=1        otra forma en params en key agregar: encuestaId  y en value agregar: 2 para evitar agregar en la url: ?encuestaId=1
    public ResponseEntity<?> calcularResultado(@RequestParam Long encuestaId){  //RequestParam se utiliza para obtener los parámetros que se añaden a una URL el cual tendra el id de la encuesta a revisar los resultados
        VotoResult votoResult = new VotoResult();
        Iterable<Voto> votos = votoRepository.findByEncuestaId(encuestaId);

        //Algoritmo para contar votos
        int totalVotos = 0;
        Map<Long, OpcionCount> tempMap = new HashMap<Long, OpcionCount>();

        for(Voto v : votos){ //recorre la lista votos, que contiene elementos del tipo Voto, La variable v representa cada elemento individual de esa lista en cada iteración.
            totalVotos ++;

            //Obtenemos la OpcionCount correspondiente a esta opcion
            //tempMap.get(...): Busca en el mapa tempMap si ya existe un objeto OpcionCount asociado a ese ID de opción. Si existe, devuelve ese objeto OpcionCount. Si no existe, devuelve null.
            OpcionCount opcionCount = tempMap.get(v.getOpcion().getId()); //obtiene el id de una opcion en particular //Obtiene el ID de la opción asociada con el voto actual (v)

            //Verificar si no existe en el mapa (null)
            if(opcionCount == null){ // Si no hay un contador (opcionCount) para esta opción en el mapa:
                opcionCount = new OpcionCount(); //Se crea un nuevo objeto OpcionCount.
                opcionCount.setOpcionId(v.getOpcion().getId()); //Se le asigna el ID de la opción con
                tempMap.put(v.getOpcion().getId(), opcionCount); //Se añade al mapa tempMap con tempMap.put(...), asociándolo al ID de la opción.
            }
            //Una vez que se tiene un objeto OpcionCount para esta opción (ya sea existente o recién creado), se incrementa su contador (count) en 1. Esto refleja que hay un voto más para esa opción.
            opcionCount.setCount(opcionCount.getCount() + 1);

        }

        votoResult.setTotalVotos(totalVotos); //Asigna el total de votos procesados.
        votoResult.setResults(tempMap.values()); //Asigna todos los valores del mapa (tempMap) al atributo results del objeto votoResult.

        return new ResponseEntity<>(votoResult, HttpStatus.OK);
    }
}
