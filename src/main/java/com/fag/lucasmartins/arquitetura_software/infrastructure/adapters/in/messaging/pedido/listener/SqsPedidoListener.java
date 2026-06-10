package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoSqsEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper.PedidoSqsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SqsPedidoListener {

    private static final Logger log = LoggerFactory.getLogger(SqsPedidoListener.class);

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
    public void processarMensagem(String payload) throws Exception {
        log.info("Mensagem recebida do SQS: {}", payload);

        PedidoSqsEventDTO evento = mapperJson.readValue(payload, PedidoSqsEventDTO.class);

        PedidoBO pedido = pedidoMapper.toBO(evento);

        pedidoService.criarPedido(pedido);

        log.info("Pedido processado com sucesso via SQS!");
    }
}