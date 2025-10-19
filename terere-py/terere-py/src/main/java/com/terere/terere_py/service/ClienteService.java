// ClienteService.java
package com.terere.terere_py.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terere.terere_py.model.Cliente;
import com.terere.terere_py.model.dto.ClienteDTO;
import com.terere.terere_py.repository.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    // Obtener todos los clientes
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }
    
    // Obtener cliente por ID
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    // Obtener cliente por email
    public Optional<Cliente> obtenerPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    // Buscar clientes por nombre
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Crear nuevo cliente
    public Cliente crearCliente(ClienteDTO clienteDTO) {
        // Verificar que el email no exista
        if(clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        return clienteRepository.save(cliente);
    }
    
    // Actualizar cliente
    public Optional<Cliente> actualizarCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id).map(cliente -> {
            if(clienteDTO.getNombre() != null) {
                cliente.setNombre(clienteDTO.getNombre());
            }
            if(clienteDTO.getEmail() != null && !clienteDTO.getEmail().equals(cliente.getEmail())) {
                if(clienteRepository.existsByEmail(clienteDTO.getEmail())) {
                    throw new RuntimeException("El email ya está registrado");
                }
                cliente.setEmail(clienteDTO.getEmail());
            }
            if(clienteDTO.getTelefono() != null) {
                cliente.setTelefono(clienteDTO.getTelefono());
            }
            if(clienteDTO.getDireccion() != null) {
                cliente.setDireccion(clienteDTO.getDireccion());
            }
            return clienteRepository.save(cliente);
        });
    }
    
    // Eliminar cliente
    public boolean eliminarCliente(Long id) {
        if(clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Verificar si existe un cliente
    public boolean existeCliente(Long id) {
        return clienteRepository.existsById(id);
    }
}