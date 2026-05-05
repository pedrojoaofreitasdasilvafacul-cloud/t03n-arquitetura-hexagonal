package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.*;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoSqsEventDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoSqsMapper {

    public PedidoBO toBO(PedidoSqsEventDTO evento) {

        PessoaBO cliente = new PessoaBO();
        cliente.setId(evento.getClienteId());

        List<PedidoProdutoBO> produtos = evento.getItensPedido()
                .stream()
                .map(item -> {
                    ProdutoBO produto = new ProdutoBO();
                    produto.setId(item.getCodigoSku());

                    PedidoProdutoBO pedidoProduto = new PedidoProdutoBO();
                    pedidoProduto.setProduto(produto);
                    pedidoProduto.setQuantidade(item.getQuantidade());
                    return pedidoProduto;
                })
                .collect(Collectors.toList());

        PedidoBO pedido = new PedidoBO();
        pedido.setPessoa(cliente);
        pedido.setItens(produtos);
        pedido.setCep(evento.getZipCode());

        return pedido;
    }
}