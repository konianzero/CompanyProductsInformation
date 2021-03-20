package org.company.products.info.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import org.company.products.info.Views;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "products_unique_idx"))
public class Product extends AbstractNamedEntity {

    @JsonView({Views.ProductView.class, Views.ArticleView.class})
    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(min = 20, max = 200)
    private String description;

    @JsonView({Views.ProductView.class, Views.ArticleView.class})
    @Column(name = "implementation_cost", nullable = false)
    @Range(min = 1, max = 1000000)
    private int implementationCost;

    @JsonView(Views.ProductView.class)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<Article> articles;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", implementationCost=" + implementationCost +
                ", articles=" + articles +
                '}';
    }
}