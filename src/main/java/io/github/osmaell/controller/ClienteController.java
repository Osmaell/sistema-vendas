package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.Cliente;
import io.github.osmaell.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}