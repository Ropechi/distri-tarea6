// PedidoService.java
package com.terere.terere_py.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terere.terere_py.model.Cliente;
import com.terere.terere_py.model.Pedido;
import com.terere.terere_py.model.Producto;
import com.terere.terere_py.model.dto.PedidoDTO;
import com.terere.terere_py.repository.ClienteRepository;
import com.terere.terere_py.repository.PedidoRepository;
import com.terere.terere_py.repository.ProductoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    // Obtener todos los pedidos
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }
    
    // Obtener pedido por ID
    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    // Obtener pedidos de un cliente
    public List<Pedido> obtenerPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    
    // Obtener pedidos por estado
    public List<Pedido> obtenerPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
    // Obtener pedidos de un cliente con estado específico
    public List<Pedido> obtenerPorClienteYEstado(Long clienteId, Pedido.EstadoPedido estado) {
        return pedidoRepository.findByClienteIdAndEstado(clienteId, estado);
    }
    
    // Crear nuevo pedido
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        // Obtener cliente
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        // Obtener productos
        List<Producto> productos = productoRepository.findAllById(pedidoDTO.getProductosIds());
        
        if(productos.isEmpty()) {
            throw new RuntimeException("No se encontraron los productos solicitados");
        }
        
        // Calcular total
        Double total = productos.stream()
            .mapToDouble(Producto::getPrecio)
            .sum();
        
        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProductos(productos);
        pedido.setTotal(total);
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
        
        return pedidoRepository.save(pedido);
    }
    
    // Actualizar estado del pedido
    public Optional<Pedido> actualizarEstado(Long id, Pedido.EstadoPedido nuevoEstado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(nuevoEstado);
            if(nuevoEstado == Pedido.EstadoPedido.ENTREGADO) {
                pedido.setFechaEntrega(java.time.LocalDateTime.now());
            }
            return pedidoRepository.save(pedido);
        });
    }
    
    // Actualizar pedido completo
    public Optional<Pedido> actualizarPedido(Long id, PedidoDTO pedidoDTO) {
        return pedidoRepository.findById(id).map(pedido -> {
            if(pedidoDTO.getProductosIds() != null && !pedidoDTO.getProductosIds().isEmpty()) {
                List<Producto> productos = productoRepository.findAllById(pedidoDTO.getProductosIds());
                pedido.setProductos(productos);
                
                Double total = productos.stream()
                    .mapToDouble(Producto::getPrecio)
                    .sum();
                pedido.setTotal(total);
            }
            if(pedidoDTO.getEstado() != null) {
                pedido.setEstado(Pedido.EstadoPedido.valueOf(pedidoDTO.getEstado()));
            }
            return pedidoRepository.save(pedido);
        });
    }
    
    // Eliminar pedido
    public boolean eliminarPedido(Long id) {
        if(pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Contar pedidos por estado
    public Long contarPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.countByEstado(estado);
    }
    
    // Contar pedidos de un cliente
    public Long contarPorCliente(Long clienteId) {
        return pedidoRepository.countByClienteId(clienteId);
    }
}