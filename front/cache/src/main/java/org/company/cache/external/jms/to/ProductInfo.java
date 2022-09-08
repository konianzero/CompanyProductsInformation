package org.company.cache.external.jms.to;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductInfo implements Serializable {
    private String name;
    private List<ProductIntegration> integrations;
}
