package io.github.osmaell.controller;

import io.github.osmaell.domain.entity.Usuario;
import io.github.osmaell.domain.repository.Usuarios;
import io.github.osmaell.dto.CredenciaisDTO;
import io.github.osmaell.dto.TokenDTO;
import io.github.osmaell.exception.SenhaInvalidaException;
import io.github.osmaell.security.jwt.JwtService;
import io.github.osmaell.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        Usuario usuarioSalvo = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody CredenciaisDTO credenciais) {

        try {

            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha())
                    .build();

            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);

            String token = jwtService.gerarToken( usuario );
            return ResponseEntity.ok( new TokenDTO(usuario.getLogin(), token) );
        } catch (UsernameNotFoundException | SenhaInvalidaException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED ,ex.getMessage());
        }

    }

}