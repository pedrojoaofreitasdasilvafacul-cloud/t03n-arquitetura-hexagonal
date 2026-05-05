package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PedidoSqsEventDTO {

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("customerId")
    private Integer clienteId;

    @JsonProperty("orderItems")
    private List<ItemPedidoDTO> itensPedido = new ArrayList<>();

    @JsonProperty("origin")
    private String origem;

    @JsonProperty("occurredAt")
    private Instant dataOcorrencia;

    public static class ItemPedidoDTO {
        @JsonProperty("sku")
        private Integer codigoSku;

        @JsonProperty("amount")
        private Integer quantidade;

        public Integer getCodigoSku() { return codigoSku; }
        public void setCodigoSku(Integer codigoSku) { this.codigoSku = codigoSku; }

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public List<ItemPedidoDTO> getItensPedido() { return itensPedido; }
    public void setItensPedido(List<ItemPedidoDTO> itensPedido) { this.itensPedido = itensPedido; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public Instant getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(Instant dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }
}