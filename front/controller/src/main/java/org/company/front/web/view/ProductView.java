package org.company.front.web.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.company.cache.external.jms.to.*;
import org.company.persistence.model.Article;
import org.company.persistence.model.Product;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductView {
    private Integer id;
    protected String name;
    private String description;
    private List<Article> articles;
    private int implementationCost;
    private List<ProductIntegration> integrations;

    public ProductView(Product product, ProductInfo productInfo) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        articles = product.getArticles();
        implementationCost = product.getImplementationCost();
        integrations = productInfo.getIntegrations();
    }
}
