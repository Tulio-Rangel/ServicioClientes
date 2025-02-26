package com.tulio.serviciocliente.repository;

import com.tulio.serviciocliente.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void whenSaveCliente_thenReturnCliente() {
        // Create cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("John Doe");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("1234567890");
        cliente.setContrasena("password123");
        cliente.setEstado(true);

        // Save cliente
        Cliente savedCliente = clienteRepository.save(cliente);

        // Verify saved cliente
        assertNotNull(savedCliente);
        assertNotNull(savedCliente.getId());
        assertEquals("John Doe", savedCliente.getNombre());
        assertEquals("123456789", savedCliente.getIdentificacion());
    }

    @Test
    void whenFindById_thenReturnCliente() {
        // Create and persist cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("John Doe");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("1234567890");
        cliente.setContrasena("password123");
        cliente.setEstado(true);

        entityManager.persist(cliente);
        entityManager.flush();

        // Find cliente by id
        Cliente found = clienteRepository.findById(cliente.getId()).orElse(null);

        // Verify found cliente
        assertNotNull(found);
        assertEquals(cliente.getNombre(), found.getNombre());
        assertEquals(cliente.getIdentificacion(), found.getIdentificacion());
    }
}