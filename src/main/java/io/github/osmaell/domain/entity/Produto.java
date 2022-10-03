package io.github.osmaell.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Campo descrição é obrigatório.")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Campo preço é obrigatório.")
    @Column(name = "preco_unitario")
    private BigDecimal preco;

}