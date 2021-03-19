package org.company.products.info.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "articles", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "name"}, name = "articles_unique_idx"))
public class Article extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @Nullable
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @NotBlank
    @Column(name = "content", nullable = false)
    @Size(min = 150, max = 5000)
    private String content;

    @NotNull
    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT now()")
    private LocalDate date;
}
