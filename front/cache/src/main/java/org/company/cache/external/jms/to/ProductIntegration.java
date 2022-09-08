package org.company.cache.external.jms.to;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductIntegration implements Serializable {
    private String companyName;
    private LocalDate date;

}
