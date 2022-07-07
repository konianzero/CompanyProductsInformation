package org.company.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.company.persistence.model.HasId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleDTO implements HasId {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    private int productId;

    @NotBlank
    @Size(min = 30, max = 5000)
    private String content;
}
