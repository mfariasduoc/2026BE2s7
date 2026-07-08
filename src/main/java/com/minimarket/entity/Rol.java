package com.minimarket.entity;

import jakarta.persistence.*;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Modelo que representa los roles de seguridad asignados a los usuarios")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del rol", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del rol de usuario", example = "ROLE_ADMIN")
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @Schema(hidden = true) // oculta la lista para no generar buble
    private Set<Usuario> usuarios;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
