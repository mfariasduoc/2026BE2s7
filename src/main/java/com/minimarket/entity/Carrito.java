package com.minimarket.entity;

import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Modelo que representa los ítems agregados al carrito de compras por un usuario")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del ítem de carrito", 
            example = "1", 
            accessMode = Schema.AccessMode.READ_ONLY
           )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario propietario del carrito de compras")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @Schema(description = "Producto agregado al carrito")
    private Producto producto;

    @Column(nullable = false)
    @Schema(description = "Cantidad de unidades seleccionadas del producto", 
            example = "3"
           )
    private Integer cantidad;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
