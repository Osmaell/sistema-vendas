package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.Cliente;
import io.github.osmaell.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clientes;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId( @PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if ( cliente.isPresent() ) {
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar( @RequestBody Cliente cliente ) {
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
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
    public ResponseEntity<?> atualizar( @PathVariable Integer id, @RequestBody Cliente cliente ) {
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