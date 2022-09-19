package io.github.osmaell.domain.repository;

import io.github.osmaell.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {

}