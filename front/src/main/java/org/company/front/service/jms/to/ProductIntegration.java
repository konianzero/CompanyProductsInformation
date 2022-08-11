package org.company.front.service.jms.to;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductIntegration {
    private String companyName;
    private LocalDate date;

}
