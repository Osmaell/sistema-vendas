package io.github.osmaell.service.impl;

import io.github.osmaell.domain.entity.Cliente;
import io.github.osmaell.domain.entity.ItemPedido;
import io.github.osmaell.domain.entity.Pedido;
import io.github.osmaell.domain.entity.Produto;
import io.github.osmaell.domain.repository.Clientes;
import io.github.osmaell.domain.repository.ItemsPedido;
import io.github.osmaell.domain.repository.Pedidos;
import io.github.osmaell.domain.repository.Produtos;
import io.github.osmaell.dto.ItemPedidoDTO;
import io.github.osmaell.dto.PedidoDTO;
import io.github.osmaell.exception.RegraNegocioException;
import io.github.osmaell.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private Pedidos pedidos;

    @Autowired
    private Clientes clientesRepository;

    @Autowired
    private Produtos produtosRepository;

    @Autowired
    private ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {

        Integer idCliente = dto.getCliente();

        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        // retornando uma lista de ItemsPedido
        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());

        // salvando pedido
        pedidos.save(pedido);

        // salvando items pedido
        itemsPedidoRepository.saveAll(itemsPedido);

        // setando itemspedido ao pedido
        pedido.setItens(itemsPedido);

        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {

        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {

                    // recuperando um produto a partir do código passado no dto
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow( () -> new RegraNegocioException("Código de produto inválido: " + dto.getProduto()));

                    // criando objeto ItemPedido
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setProduto(produto);

                    return itemPedido;
        }).collect(Collectors.toList());
    }

}