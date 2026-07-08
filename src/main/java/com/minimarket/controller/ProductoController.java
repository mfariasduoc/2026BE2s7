package com.minimarket.controller;

import com.minimarket.entity.Producto;
import com.minimarket.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", 
     description = "Operaciones para la gestión del catálogo de productos"
    )
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar productos", 
               description = "Obtiene la lista completa de todos los productos en el minimarket."
               )
    @ApiResponse(responseCode = "200", 
                 description = "Catálogo obtenido con éxito"
                )
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.findAll();
    }

    @Operation(summary = "Buscar producto por ID", 
               description = "Busca un producto específico ingresando su ID numérico."
            )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                      description = "Producto encontrado con éxito"
                    ),
        @ApiResponse(responseCode = "404", 
                      description = "El producto con el ID solicitado no existe"
                    )
    })

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(
            @Parameter(description = "ID del producto a buscar", 
                       example = "1"
                      ) 
            @PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return (producto != null) ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Guardar un producto", 
               description = "Crea y almacena un nuevo producto en el sistema."
               )
    @ApiResponse(responseCode = "200", 
                 description = "Producto guardado de manera exitosa"
                )
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @Operation(summary = "Actualizar producto existente", 
               description = "Modifica los datos de un producto basándose en su ID."
              )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Producto actualizado correctamente"
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "No se pudo actualizar porque el ID no existe"
                    )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", 
                       example = "1"
                    )
            @PathVariable Long id, 
            @RequestBody Producto producto) {
        Producto productoExistente = productoService.findById(id);
        if (productoExistente != null) {
            producto.setId(id);
            return ResponseEntity.ok(productoService.save(producto));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un producto", 
               description = "Remueve permanentemente un producto del catálogo por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                     description = "Producto eliminado con éxito (Sin contenido en la respuesta)"
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "El producto no pudo ser eliminado porque el ID no existe"
                    )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", 
                       example = "2"
                      )
            @PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}