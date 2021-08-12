package org.company.products.info.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public class Util {

    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    public static final int MIN_COST = 0;
    public static final int MAX_COST = 1000000;

    private Util() {
    }

    public static <T, U> Specification<U> filterBy(String filterColumn,
                                                   Optional<String> filter,
                                                   Optional<T> from,
                                                   Optional<T> to)
    {
        switch (filterColumn) {
            case "implementationCost": return integerFilterBetween(filterColumn,
                    from.map(Integer.class::cast).orElse(MIN_COST),
                    to.map(Integer.class::cast).orElse(MAX_COST)
            );
            case "date": return dateFilterBetween(filterColumn,
                    from.map(LocalDate.class::cast).orElse(MIN_DATE),
                    to.map(LocalDate.class::cast).orElse(MAX_DATE)
            );
            case "name", "description", "content": return stringFilterLike(filterColumn, filter.orElse(""));
            default: throw new IllegalArgumentException("filter by " + filterColumn + " not supported");
        }
    }

    private static <U> Specification<U> stringFilterLike(String filterColumn, String filter) {
        return (root, query, builder) -> builder.like(root.get(filterColumn), "%" + filter + "%");
    }

    private static <U> Specification<U> integerFilterBetween(String filterColumn, int from, int to) {
        return (root, query, builder) -> builder.between(root.get(filterColumn), from, to);
    }

    private static <U> Specification<U> dateFilterBetween(String filterColumn, LocalDate from, LocalDate to) {
        return (root, query, builder) -> builder.between(root.get(filterColumn), from, to);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalDate.parse(str) : null;
    }
}
