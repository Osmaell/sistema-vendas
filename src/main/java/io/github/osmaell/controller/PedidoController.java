package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.ItemPedido;
import io.github.osmaell.domain.entity.Pedido;
import io.github.osmaell.dto.InformacoesItemPedidoDTO;
import io.github.osmaell.dto.InformacoesPedidoDTO;
import io.github.osmaell.dto.PedidoDTO;
import io.github.osmaell.exception.RegraNegocioException;
import io.github.osmaell.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId( @PathVariable Integer id ) {

        InformacoesPedidoDTO dtoPedido = pedidoService
                .obterPedido(id)
                .map( pedido -> converter(pedido))
                .orElseThrow(() ->
                        new RegraNegocioException("Pedido não encontrado."));

        return ResponseEntity.ok( dtoPedido );
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {

        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .totalPedido(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .build();

    }

    private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> itens) {

        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream()
                .map(item -> InformacoesItemPedidoDTO.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build())
                .collect(Collectors.toList());
    }

}