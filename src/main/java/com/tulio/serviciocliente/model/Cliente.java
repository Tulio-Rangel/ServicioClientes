package com.tulio.serviciocliente.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona {
    //private Long clienteId;

    private String contrasena;
    private boolean estado;
}
