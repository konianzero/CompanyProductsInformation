package org.company.product.info.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.company.product.info.HasId;

@Getter
@Setter
public class ArticleTo implements HasId {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    private int productId;

    @NotBlank
    @Size(min = 30, max = 5000)
    private String content;

    public ArticleTo() {
    }

    public ArticleTo(Integer id, String name, int productId, String content) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.content = content;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productId=" + productId +
                ", content='" + content + '\'' +
                '}';
    }
}
