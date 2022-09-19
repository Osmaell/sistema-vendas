package io.github.osmaell.domain.repository;

import io.github.osmaell.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = " SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")
    List<Cliente> buscarPorNome( @Param("nome") String nome);

    List<Cliente> findByNomeOrId(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query(value = " delete from Cliente c where c.nome = :nome")
    @Modifying
    void deleteByNome(String nome);

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos p WHERE c.id = :id")
    Cliente findClienteFetchPedidos(  @Param("id") Integer id);

}