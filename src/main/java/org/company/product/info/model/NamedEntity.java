package org.company.product.info.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NamedEntity extends BaseEntity {

    @NotBlank
    @Size(min = 2, max = 150)
    @Column(name = "name", nullable = false)
    protected String name;

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
