package com.api.rest.encuestas.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Encuesta {

    @Id
    @GeneratedValue
    @Column(name = "encuesta_id") //encuesta_id será el nombre que tendra la propiedad de la clase encuesta id en la tabla de la BD
    private Long id;

    @Column(name = "pregunta")
    @NotEmpty  //validacion
    private String pregunta;

    //Nota: Para OneToMany y @ManyToOne → La clave foránea estará en la tabla de la clase donde se encuentra el @Many
    //Para @OneToOne → La entidad que tiene la anotación @JoinColumn es el propietario de la relación, por lo tanto ahi se creará la columna que almacene la relacion.
    //@ManyToMany: Se crea una tabla intermedia para almacenar las relaciones entre las dos entidades.
    // La columna a crear que contendra la clave foranea(encuesta_id) se creará en la clase o tabla que se encuentra el Many, por lo tanto se crea en Opciones

    //cascade = CascadeType.ALL asegura que las operaciones realizadas en la entidad padre (como guardar, actualizar o eliminar) se apliquen automáticamente a las entidades relacionadas.
    @OneToMany(cascade = CascadeType.ALL) //"uno a muchos", donde una Encuesta puede tener varias Opcion, no se uso mappedBy lo que indica que es unidireccional desde Encuesta hacia Opcion. Es decir, la entidad Opcion no conoce a Encuesta.
    @JoinColumn(name = "encuesta_id") // indica que la tabla opcion en la BD tendrá una columna llamada encuesta_id para almacenar la clave foránea que asocia cada opción con su encuesta.
    @OrderBy //se ordenará por opciones
    @Size(min = 2, max = 6) //validacion
    private Set<Opcion> opciones; //El uso de un Set en lugar de List indica que se garantizará que las opciones relacionadas con una encuesta sean únicas


    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Set<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(Set<Opcion> opciones) {
        this.opciones = opciones;
    }
}
