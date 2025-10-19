// PedidoDTO.java
package com.terere.terere_py.model.dto;

import java.util.List;

public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private List<Long> productosIds;
    private Double total;
    private String estado;
    
    // Constructores
    public PedidoDTO() {
    }
    
    public PedidoDTO(Long id, Long clienteId, List<Long> productosIds, Double total, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.productosIds = productosIds;
        this.total = total;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public List<Long> getProductosIds() {
        return productosIds;
    }
    
    public void setProductosIds(List<Long> productosIds) {
        this.productosIds = productosIds;
    }
    
    public Double getTotal() {
        return total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}