package io.github.osmaell.security.jwt;

import io.github.osmaell.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

        long expiracaoLong = Long.parseLong(expiracao);

        // definindo a hora que o token será expirado
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoLong);

        // convertendo um LocalDateTime para um objeto do tipo Date
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        // retornando um token JWT
        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    /**
     * Método responsável por obter as informações
     * do token (claims).
     */
    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {

        try {
            Claims claims = obterClaims(token);

            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }

    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }

}