package com.api.vendas.services;

import com.api.vendas.domain.entities.Pedido;
import com.api.vendas.domain.enums.StatusPedido;
import com.api.vendas.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}