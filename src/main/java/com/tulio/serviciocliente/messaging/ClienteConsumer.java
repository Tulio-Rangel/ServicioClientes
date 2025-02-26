package com.tulio.serviciocliente.messaging;

import com.tulio.serviciocliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClienteConsumer {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "validar-cliente")
    public void recibirMensajeValidacion(Long clienteId) {
        boolean existeCliente = clienteService.existeCliente(clienteId);

        // Enviar respuesta a la cola de respuesta
        jmsTemplate.convertAndSend("respuesta-validacion", existeCliente);
    }
}
