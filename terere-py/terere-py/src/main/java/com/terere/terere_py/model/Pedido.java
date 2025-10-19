// Pedido.java
package com.terere.terere_py.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
    
    public Pedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pedido(Long id, Cliente cliente, List<Producto> productos, Double total, EstadoPedido estado,
			LocalDateTime fechaPedido, LocalDateTime fechaEntrega) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.productos = productos;
		this.total = total;
		this.estado = estado;
		this.fechaPedido = fechaPedido;
		this.fechaEntrega = fechaEntrega;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public java.time.LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(java.time.LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public java.time.LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(java.time.LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pedido_productos",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;
    
    @Column(nullable = false)
    private Double total;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    
    @Column(name = "fecha_pedido", nullable = false)
    private java.time.LocalDateTime fechaPedido;
    
    @Column(name = "fecha_entrega")
    private java.time.LocalDateTime fechaEntrega;
    
    @PrePersist
    protected void onCreate() {
        fechaPedido = java.time.LocalDateTime.now();
        if(estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
    }
    
    public enum EstadoPedido {
        PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO
    }
}