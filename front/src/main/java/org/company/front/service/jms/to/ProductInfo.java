package org.company.front.service.jms.to;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductInfo {
    private String name;
    private List<ProductIntegration> integrations;
}
