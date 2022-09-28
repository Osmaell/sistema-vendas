package io.github.osmaell.service;

import io.github.osmaell.domain.entity.Pedido;
import io.github.osmaell.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

}