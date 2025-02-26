package com.tulio.serviciocliente.service;

import com.tulio.serviciocliente.exception.ResourceNotFoundException;
import com.tulio.serviciocliente.model.Cliente;
import com.tulio.serviciocliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("1234567890");
        cliente.setContrasena("password123");
        cliente.setEstado(true);
    }

    @Test
    void getAllClientes_ShouldReturnListOfClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<Cliente> result = clienteService.getAllClientes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void getClienteById_WhenExists_ShouldReturnCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteById(1L);

        assertNotNull(result);
        assertEquals(cliente.getNombre(), result.getNombre());
        verify(clienteRepository).findById(1L);
    }

    @Test
    void getClienteById_WhenNotExists_ShouldThrowException() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.getClienteById(1L);
        });

        verify(clienteRepository).findById(1L);
    }

    @Test
    void saveCliente_ShouldReturnSavedCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.saveCliente(cliente);

        assertNotNull(result);
        assertEquals(cliente.getNombre(), result.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void updateCliente_WhenExists_ShouldReturnUpdatedCliente() {
        Cliente updatedCliente = new Cliente();
        updatedCliente.setNombre("Jane Doe");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(updatedCliente);

        Cliente result = clienteService.updateCliente(1L, updatedCliente);

        assertNotNull(result);
        assertEquals(updatedCliente.getNombre(), result.getNombre());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void deleteCliente_WhenExists_ShouldDeleteSuccessfully() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);

        assertDoesNotThrow(() -> clienteService.deleteCliente(1L));

        verify(clienteRepository).findById(1L);
        verify(clienteRepository).delete(cliente);
    }

    @Test
    void existeCliente_WhenExists_ShouldReturnTrue() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        boolean result = clienteService.existeCliente(1L);

        assertTrue(result);
        verify(clienteRepository).existsById(1L);
    }
}