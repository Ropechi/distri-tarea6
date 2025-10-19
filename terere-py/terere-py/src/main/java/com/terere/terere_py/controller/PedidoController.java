// PedidoController.java
package com.terere.terere_py.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terere.terere_py.model.Pedido;
import com.terere.terere_py.model.dto.PedidoDTO;
import com.terere.terere_py.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    // GET - Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    // GET - Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.obtenerPorId(id);
        if(pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET - Obtener pedidos de un cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.obtenerPorCliente(clienteId);
        if(pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }
    
    // GET - Obtener pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPorEstado(@PathVariable String estado) {
        try {
            Pedido.EstadoPedido estadoEnum = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
            List<Pedido> pedidos = pedidoService.obtenerPorEstado(estadoEnum);
            if(pedidos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(pedidos);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // GET - Obtener pedidos de cliente con estado específico
    @GetMapping("/cliente/{clienteId}/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPorClienteYEstado(
            @PathVariable Long clienteId,
            @PathVariable String estado) {
        try {
            Pedido.EstadoPedido estadoEnum = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
            List<Pedido> pedidos = pedidoService.obtenerPorClienteYEstado(clienteId, estadoEnum);
            if(pedidos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(pedidos);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // POST - Crear nuevo pedido
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT - Actualizar estado del pedido
    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @PathVariable String estado) {
        try {
            Pedido.EstadoPedido estadoEnum = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
            Optional<Pedido> pedidoActualizado = pedidoService.actualizarEstado(id, estadoEnum);
            if(pedidoActualizado.isPresent()) {
                return ResponseEntity.ok(pedidoActualizado.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT - Actualizar pedido completo
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedido(
            @PathVariable Long id,
            @RequestBody PedidoDTO pedidoDTO) {
        try {
            Optional<Pedido> pedidoActualizado = pedidoService.actualizarPedido(id, pedidoDTO);
            if(pedidoActualizado.isPresent()) {
                return ResponseEntity.ok(pedidoActualizado.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // DELETE - Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        boolean eliminado = pedidoService.eliminarPedido(id);
        if(eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}