package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.Cliente;
import io.github.osmaell.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}