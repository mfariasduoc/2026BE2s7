package com.minimarket.controller;

import com.minimarket.entity.Carrito;
import com.minimarket.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Operaciones para gestionar el carrito de compras de los clientes")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Operation(summary = "Listar carritos activos", description = "Retorna una lista con todos los ítems agregados en los carritos del sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de carritos obtenida con éxito")
    @GetMapping
    public List<Carrito> listarCarrito() {
        return carritoService.findAll();
    }

    @Operation(summary = "Obtener ítem de carrito por ID", description = "Busca un registro específico dentro del carrito mediante su identificador.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "El ítem de carrito solicitado no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarritoPorId(
            @Parameter(description = "ID del ítem de carrito", example = "1")
            @PathVariable Long id) {
        Carrito carrito = carritoService.findById(id);
        return (carrito != null) ? ResponseEntity.ok(carrito) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Agregar producto al carrito", description = "Crea una nueva entrada en el carrito de un usuario asociando un producto y su cantidad.")
    @ApiResponse(responseCode = "200", description = "Producto añadido al carrito con éxito")
    @PostMapping
    public Carrito agregarProductoAlCarrito(@RequestBody Carrito carrito) {
        return carritoService.save(carrito);
    }

    @Operation(summary = "Actualizar cantidad en el carrito", description = "Modifica la cantidad o datos de un ítem existente en el carrito mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque el ID de carrito no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizarCarrito(
            @Parameter(description = "ID del ítem a modificar", example = "1")
            @PathVariable Long id, 
            @RequestBody Carrito carrito) {
        Carrito existente = carritoService.findById(id);
        if (existente != null) {
            carrito.setId(id);
            return ResponseEntity.ok(carritoService.save(carrito));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar producto del carrito", description = "Remueve de forma definitiva un producto del carrito de compras.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto removido del carrito con éxito (Sin contenido)"),
        @ApiResponse(responseCode = "404", description = "El ítem de carrito no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProductoDelCarrito(
            @Parameter(description = "ID del ítem a remover", example = "1")
            @PathVariable Long id) {
        Carrito carrito = carritoService.findById(id);
        if (carrito != null) {
            carritoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
