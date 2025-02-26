package com.tulio.serviciocliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Pattern(regexp = "[MF]", message = "El género debe ser 'M' (Masculino) o 'F' (Femenino)")
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
