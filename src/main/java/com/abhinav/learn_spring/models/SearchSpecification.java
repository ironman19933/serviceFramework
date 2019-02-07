package com.abhinav.learn_spring.models;

import com.abhinav.learn_spring.utils.FilterUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Map;

@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {
    private Map<String, HashSet<String>> filters;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        Predicate[] predicates = FilterUtil.getPredicatesFromFilter(filters, root);
        criteriaQuery.where(predicates);
        return null;
    }
}
