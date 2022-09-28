package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.Pedido;
import io.github.osmaell.dto.PedidoDTO;
import io.github.osmaell.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PedidoDTO dto) {
        Pedido pedido = pedidoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido.getId());
    }

}