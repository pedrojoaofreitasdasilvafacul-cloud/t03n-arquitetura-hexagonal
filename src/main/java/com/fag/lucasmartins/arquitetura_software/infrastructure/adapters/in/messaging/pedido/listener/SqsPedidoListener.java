package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoSqsEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper.PedidoSqsMapper;
import org.springframework.stereotype.Component;

@Component
public class SqsPedidoListener {

    private final ObjectMapper mapperJson;
    private final PedidoSqsMapper pedidoMapper;
    private final PedidoServicePort pedidoService;

    public SqsPedidoListener(ObjectMapper mapperJson,
                             PedidoSqsMapper pedidoMapper,
                             PedidoServicePort pedidoService) {
        this.mapperJson = mapperJson;
        this.pedidoMapper = pedidoMapper;
        this.pedidoService = pedidoService;
    }

    @SqsListener(value = "${aws.order-event}")
    public void processarMensagem(String payload) {
        try {
            PedidoSqsEventDTO evento = mapperJson.readValue(payload, PedidoSqsEventDTO.class);

            PedidoBO pedido = pedidoMapper.toBO(evento);

            pedidoService.criarPedido(pedido);

            System.out.println("Pedido processado com sucesso via SQS!");

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem SQS: " + e.getMessage());
        }
    }
}