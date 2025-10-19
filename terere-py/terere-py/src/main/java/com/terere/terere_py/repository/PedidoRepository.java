// PedidoRepository.java
package com.terere.terere_py.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terere.terere_py.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Obtener todos los pedidos de un cliente específico
    List<Pedido> findByClienteId(Long clienteId);
    
    // Obtener pedidos por estado
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    
    // Obtener pedidos de un cliente con estado específico
    List<Pedido> findByClienteIdAndEstado(Long clienteId, Pedido.EstadoPedido estado);
    
    // Obtener pedidos dentro de un rango de fechas
    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Obtener pedidos por rango de total
    List<Pedido> findByTotalBetween(Double totalMin, Double totalMax);
    
    // Contar pedidos por estado
    Long countByEstado(Pedido.EstadoPedido estado);
    
    // Contar pedidos de un cliente
    Long countByClienteId(Long clienteId);
}