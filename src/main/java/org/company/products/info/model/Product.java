package org.company.products.info.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "products_unique_idx"))
public class Product extends AbstractNamedEntity {

    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(min = 20, max = 200)
    private String description;

    @Column(name = "implementation_cost", nullable = false)
    @Range(min = 1, max = 1000000)
    private int implementationCost;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<Article> articles;

    public Product() {  }

    public Product(Integer id, String name, String description, int implementationCost) {
        super(id, name);
        this.description = description;
        this.implementationCost = implementationCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImplementationCost() {
        return implementationCost;
    }

    public void setImplementationCost(int implementationCost) {
        this.implementationCost = implementationCost;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

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