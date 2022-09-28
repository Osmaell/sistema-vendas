package io.github.osmaell.service.impl;

import io.github.osmaell.domain.repository.Pedidos;
import io.github.osmaell.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private Pedidos pedidos;

}
