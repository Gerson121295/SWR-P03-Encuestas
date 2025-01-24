package com.api.rest.encuestas.dto;

import java.util.Collection;

public class VotoResult {

    private int totalVotos;
    private Collection<OpcionCount> results;
/*
El atributo privado llamado results, será una colección de elementos de tipo OpcionCount.
Cada elemento en esta colección tiene dos propiedades: opcionId y count que son atributos de la clase OpcionCount
*/

    public int getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(int totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Collection<OpcionCount> getResults() {
        return results;
    }

    public void setResults(Collection<OpcionCount> results) {
        this.results = results;
    }
}
