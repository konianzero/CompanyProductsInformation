package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "products_unique_idx"))
@NoArgsConstructor
@Getter
@Setter
public class Product extends NamedEntity {

    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(min = 20, max = 200)
    private String description;

    @Column(name = "implementation_cost", nullable = false)
    @Range(min = 1, max = 1000000)
    private int implementationCost;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<Article> articles;

    public Product(Integer id, String name, String description, int implementationCost) {
        super(id, name);
        this.description = description;
        this.implementationCost = implementationCost;
    }
}
