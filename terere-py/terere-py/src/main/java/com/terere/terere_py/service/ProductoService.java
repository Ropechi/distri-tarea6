// ProductoService.java
package com.terere.terere_py.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terere.terere_py.model.Producto;
import com.terere.terere_py.model.dto.ProductoDTO;
import com.terere.terere_py.repository.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    // Obtener todos los productos
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    // Obtener producto por ID
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    // Obtener productos por categoría
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    // Buscar productos por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Obtener productos disponibles (con stock > 0)
    public List<Producto> obtenerDisponibles() {
        return productoRepository.findByStockGreaterThan(0);
    }
    
    // Obtener productos por rango de precio
    public List<Producto> obtenerPorRangoPrecio(Double precioMin, Double precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }
    
    // Crear nuevo producto
    public Producto crearProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());
        return productoRepository.save(producto);
    }
    
    // Actualizar producto
    public Optional<Producto> actualizarProducto(Long id, ProductoDTO productoDTO) {
        return productoRepository.findById(id).map(producto -> {
            if(productoDTO.getNombre() != null) {
                producto.setNombre(productoDTO.getNombre());
            }
            if(productoDTO.getPrecio() != null) {
                producto.setPrecio(productoDTO.getPrecio());
            }
            if(productoDTO.getCategoria() != null) {
                producto.setCategoria(productoDTO.getCategoria());
            }
            if(productoDTO.getStock() != null) {
                producto.setStock(productoDTO.getStock());
            }
            if(productoDTO.getDescripcion() != null) {
                producto.setDescripcion(productoDTO.getDescripcion());
            }
            return productoRepository.save(producto);
        });
    }
    
    // Eliminar producto
    public boolean eliminarProducto(Long id) {
        if(productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Actualizar stock de un producto
    public Optional<Producto> actualizarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id).map(producto -> {
            producto.setStock(nuevoStock);
            return productoRepository.save(producto);
        });
    }
}