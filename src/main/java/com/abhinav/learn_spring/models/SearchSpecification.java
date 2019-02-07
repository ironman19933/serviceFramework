package com.abhinav.learn_spring.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SearchSpecification<T> implements Specification<T> {
    private Map<String, HashSet<String>> filters;
    private Map<String, Map<String, String>> searchParams;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        Predicate[] predicates = getPredicatesFromSearchParams(searchParams, root, builder);
        criteriaQuery.where(predicates);
        return null;
    }

    private Predicate[] getPredicatesFromSearchParams(Map<String, Map<String, String>> searchParams, Root root, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : searchParams.entrySet()) {
            for (Map.Entry<String, String> e : entry.getValue().entrySet()) {
                addPredicate(entry.getKey(), e.getKey(), e.getValue(), root, predicateList, builder);
            }
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        return predicateList.toArray(predicates);
    }

    private static <T extends Enum<T>> Collection<T> getFilterEnum(Class<T> enumClass, Set<String> filterVal) {
        List<T> result = new ArrayList<>();
        if (!filterVal.isEmpty()) {
            for (String filter : filterVal) {
                result.add(Enum.valueOf(enumClass, filter));
            }
        }
        return result;
    }

    private void addPredicate(String operator, String key, String value, Root root, List<Predicate> predicates, CriteriaBuilder builder) {
        SearchOperator searchOperator = SearchOperator.value(operator);
        Path path = root;
        path = path.get(key);
        switch (searchOperator) {
            case IN:
                Set<String> values = new HashSet<>(Arrays.asList(value.split(",")));
                if (path.getJavaType().isEnum()) {
                    Collection<?> filterValuesList = getFilterEnum(path.getJavaType(), values);
                    predicates.add(path.in(filterValuesList));
                } else {
                    predicates.add(path.in(values));
                }
                break;
            case EQUAL_TO:
                if (path.getJavaType().isEnum()) {
                    predicates.add(path.in(Enum.valueOf(path.getJavaType(), value)));
                } else {
                    predicates.add(path.in(value));
                }
                break;
            case NOT_EQUAL_TO:
                if (path.getJavaType().isEnum()) {
                    predicates.add(builder.notEqual(path, Enum.valueOf(path.getJavaType(), value)));
                } else {
                    predicates.add(builder.notEqual(path, value));
                }
                break;
            case IS_NULL:
                predicates.add(path.isNull());
                break;
            case IS_NOT_NULL:
                predicates.add(path.isNotNull());
                break;
            case GREATER_THAN:
                predicates.add(builder.greaterThan((Expression<String>) path, value));
                break;
            case LESS_THAN:
                predicates.add(builder.lessThan((Expression<String>) path, value));
                break;
            case GREATER_THAN_EQUAL_TO:
                predicates.add(builder.greaterThanOrEqualTo((Expression<String>) path, value));
                break;
            case LESS_THAN_EQUAL_TO:
                predicates.add(builder.lessThanOrEqualTo((Expression<String>) path, value));
                break;
            case LIKE:
                predicates.add(builder.like((Expression<String>) path, value));
                break;
            case NOT_LIKE:
                predicates.add(builder.notLike((Expression<String>) path, value));
                break;
        }
    }
}
