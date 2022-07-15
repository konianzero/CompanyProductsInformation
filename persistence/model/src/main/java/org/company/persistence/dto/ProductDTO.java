package org.company.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.company.persistence.model.HasId;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO implements HasId {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 150)
    protected String name;

    @NotBlank
    @Size(min = 20, max = 200)
    private String description;

    @Range(min = 1, max = 1000000)
    private int implementationCost;
}
