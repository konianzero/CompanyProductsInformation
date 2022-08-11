package org.company.remote.to;

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
