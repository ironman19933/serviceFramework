package com.livspace.service_framework.models;

import com.livspace.service_framework.utils.SearchHelper;
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
    private Map<SearchOperator, Map<String, String>> searchParams;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        Predicate[] predicates = SearchHelper.getPredicatesFromSearchParams(searchParams, root, builder);
        criteriaQuery.where(predicates);
        return null;
    }
}
