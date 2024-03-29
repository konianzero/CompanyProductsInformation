package org.company.persistence.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;

@UtilityClass
public class JpaSpecificationUtil {

    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    public static final int MIN_COST = 0;
    public static final int MAX_COST = 1000000;

    public static <T extends Comparable, U> Specification<U> filterBy(String filterColumn,
                                                                      Optional<String> filter,
                                                                      Optional<T> from,
                                                                      Optional<T> to)
    {
        return switch (filterColumn) {
            case "implementationCost" -> objectFilterBetween(filterColumn,
                    from.map(Integer.class::cast).orElse(MIN_COST),
                    to.map(Integer.class::cast).orElse(MAX_COST)
            );
            case "date" -> objectFilterBetween(filterColumn,
                    from.map(LocalDate.class::cast).orElse(MIN_DATE),
                    to.map(LocalDate.class::cast).orElse(MAX_DATE)
            );
            case "name", "description", "content" -> stringFilterLike(filterColumn, filter.orElse(""));
            default -> throw new IllegalArgumentException("filter by " + filterColumn + " not supported");
        };
    }

    private static <U> Specification<U> stringFilterLike(String filterColumn, String filter) {
        return (root, query, builder) -> builder.like(root.get(filterColumn), "%" + filter + "%");
    }

    private static <U> Specification<U> objectFilterBetween(String filterColumn, Comparable from, Comparable to) {
        return (root, query, builder) -> builder.between(root.get(filterColumn), from, to);
    }
}
