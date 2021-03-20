package org.company.products.info.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import org.company.products.info.Views;

@Entity
@Table(name = "articles", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "name"}, name = "articles_unique_idx"))
public class Article extends AbstractNamedEntity {

    @JsonView(Views.ArticleView.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @Nullable
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @JsonView({Views.ProductView.class, Views.ArticleView.class})
    @NotBlank
    @Column(name = "content", nullable = false)
    @Size(min = 150, max = 5000)
    private String content;

    @JsonView({Views.ProductView.class, Views.ArticleView.class})
    @NotNull
    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT now()")
    private LocalDate date;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", product=" + product +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
