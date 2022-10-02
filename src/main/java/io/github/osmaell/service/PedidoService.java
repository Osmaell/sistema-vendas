package io.github.osmaell.service;

import io.github.osmaell.domain.entity.Pedido;
import io.github.osmaell.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedido(Integer id);
}