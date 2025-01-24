package com.api.rest.encuestas.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Voto {

    @Id
    @GeneratedValue
    @Column(name = "voto_id")
    private Long id;

    //Nota: Para OneToMany y @ManyToOne → La clave foránea estará en la tabla de la clase donde se encuentra el @Many
    //Para @OneToOne → La entidad que tiene la anotación @JoinColumn es el propietario de la relación, por lo tanto ahi se creará la columna que almacene la relacion.
    //@ManyToMany: Se crea una tabla intermedia para almacenar las relaciones entre las dos entidades.
    // La columna a crear que contendra la clave foranea(opcion_id) se creará en la clase o tabla que se encuentra el Many, por lo tanto se crea en Voto

    @ManyToOne //el @Many se encuentra en esta clase Voto por lo tanto en esta tabla se creara la columna opcion_id
    @JoinColumn(name="opcion_id")
    private Opcion opcion; //atributo opcion es de tipo Opcion


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }
}
