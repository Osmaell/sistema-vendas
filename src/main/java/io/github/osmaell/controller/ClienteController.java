package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.Cliente;
import io.github.osmaell.domain.repository.Clientes;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Api("Api Clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clientes;

    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId( @PathVariable @ApiParam("Id do cliente") Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if ( cliente.isPresent() ) {
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<?> salvar( @RequestBody @Valid Cliente cliente ) {
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar( @PathVariable Integer id ) {

        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()) {
            clientes.delete( cliente.get() );
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar( @PathVariable Integer id, @RequestBody @Valid Cliente cliente ) {
        return clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    public ResponseEntity<?> buscar( Cliente filtro ) {

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        List<Cliente> listaClientes = clientes.findAll(example);
        return ResponseEntity.ok(listaClientes);
    }

}