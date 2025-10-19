// ProductoRepository.java
package com.terere.terere_py.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terere.terere_py.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos por categoría
    List<Producto> findByCategoria(String categoria);
    
    // Buscar productos por nombre (búsqueda parcial)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar productos por rango de precio
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
    
    // Buscar productos con stock disponible
    List<Producto> findByStockGreaterThan(Integer stock);
    
    // Contar productos por categoría
    Long countByCategoria(String categoria);
}