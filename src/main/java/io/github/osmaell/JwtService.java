package io.github.osmaell;

import io.github.osmaell.domain.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura")
    private String chaveAssinatura;
    
    public String gerarToken(Usuario usuario) {

        long expiracaoString = Long.parseLong(expiracao);

        // definindo a hora que o token será expirado
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoString);

        // convertendo um LocalDateTime para um objeto do tipo Date
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

}