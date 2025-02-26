package com.tulio.serviciocliente.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tulio.serviciocliente.model.Cliente;
import com.tulio.serviciocliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllClientes_ShouldReturnListOfClientes() throws Exception {
        when(clienteService.getAllClientes()).thenReturn(Arrays.asList(cliente));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$[0].identificacion").value(cliente.getIdentificacion()));
    }

    @Test
    void getClienteById_ShouldReturnCliente() throws Exception {
        when(clienteService.getClienteById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.identificacion").value(cliente.getIdentificacion()));
    }

    @Test
    void createCliente_ShouldReturnCreatedCliente() throws Exception {
        when(clienteService.saveCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.identificacion").value(cliente.getIdentificacion()));
    }

    @Test
    void updateCliente_ShouldReturnUpdatedCliente() throws Exception {
        when(clienteService.updateCliente(eq(1L), any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.identificacion").value(cliente.getIdentificacion()));
    }

    @Test
    void deleteCliente_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }
}