package com.abhinav.learn_spring.utils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class FilterUtil {
    public static Predicate[] getPredicatesFromFilter(Map<String, HashSet<String>> filterMap, Root root) {
        if (filterMap == null)
            return new Predicate[0];
        ArrayList<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, HashSet<String>> entry : filterMap.entrySet()) {
            if (entry.getValue().isEmpty())
                continue;
            Path path = root;
            String[] keys = entry.getKey().split("\\.");
            for (String key : keys) {
                path = path.get(key);
                if (Collection.class.isAssignableFrom(path.getJavaType())) {
                    path = root.joinCollection(key);
                }
            }
            if (path.getJavaType().isEnum()) {
                Collection<?> filterValuesList = getFilterEnum(path.getJavaType(), entry.getValue());
                predicates.add(path.in(filterValuesList));
            } else {
                predicates.add(path.in(entry.getValue()));
            }
        }
        Predicate[] predicateArray = new Predicate[predicates.size()];
        return predicates.toArray(predicateArray);
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
}
