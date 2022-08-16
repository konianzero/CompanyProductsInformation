package org.company.remote.to;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductsInfoResponse {
    private List<ProductInfo> productsInfo;
}
