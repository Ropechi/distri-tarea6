// ClienteRepository.java
package com.terere.terere_py.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terere.terere_py.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);
    
    // Buscar clientes por nombre (búsqueda parcial)
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar clientes por teléfono
    Optional<Cliente> findByTelefono(String telefono);
    
    // Verificar si existe un cliente con ese email
    boolean existsByEmail(String email);
}