package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "articles", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "name"}, name = "articles_unique_idx"))
@NoArgsConstructor
@Getter
@Setter
public class Article extends NamedEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("articles")
    private Product product;

    @NotBlank
    @Column(name = "content", nullable = false)
    @Size(min = 30, max = 5000)
    private String content;

    @NotNull
    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT now()")
    private LocalDate date;

    public Article(Integer id, String name, Product product, String content, LocalDate date) {
        super(id, name);
        this.product = product;
        this.content = content;
        this.date = date;
    }
}
