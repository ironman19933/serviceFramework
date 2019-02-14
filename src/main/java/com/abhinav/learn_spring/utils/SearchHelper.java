package com.abhinav.learn_spring.utils;

import com.abhinav.learn_spring.exceptions.ServiceException;
import com.abhinav.learn_spring.models.SearchOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SearchHelper {
    @NotNull
    public static Map<SearchOperator, Map<String, String>> parseSearchParams(String filters) throws ServiceException {
        Map<SearchOperator, Map<String, String>> params = new HashMap<>();
        Map<String, String> inParams = new HashMap<>();
        Map<String, String> eqParams = new HashMap<>();
        Map<String, String> neParams = new HashMap<>();
        Map<String, String> isNullParams = new HashMap<>();
        Map<String, String> isNotNullParams = new HashMap<>();
        Map<String, String> gtParams = new HashMap<>();
        Map<String, String> ltParams = new HashMap<>();
        Map<String, String> leParams = new HashMap<>();
        Map<String, String> geParams = new HashMap<>();
        Map<String, String> likeParams = new HashMap<>();
        Map<String, String> nlParams = new HashMap<>();

        if (StringUtils.isEmpty(filters)) {
            return params;
        } else {
            String[] filterArray = filters.split(";");
            for (String filter : filterArray) {
                try {
                    String key, value;
                    Integer index = filter.indexOf(":");
                    if (index == -1) {
                        key = filter;
                        value = null;
                    } else {
                        String[] splitFilter = filter.split(":");
                        value = splitFilter[1];
                        key = splitFilter[0];
                    }
                    String[] keyAndOperator = key.split("\\.");
                    String keyName = keyAndOperator[0];
                    String operator = keyAndOperator[1];
                    SearchOperator searchOperator = SearchOperator.value(operator);
                    if (Objects.isNull(searchOperator)) {
                        throw new ServiceException("No Valid Search Operator Found");
                    }
                    switch (searchOperator) {
                        case IN:
                            inParams.put(keyName, value);
                            break;
                        case EQUAL_TO:
                            eqParams.put(keyName, value);
                            break;
                        case IS_NULL:
                            isNullParams.put(keyName, value);
                            break;
                        case IS_NOT_NULL:
                            isNotNullParams.put(keyName, value);
                            break;
                        case NOT_EQUAL_TO:
                            neParams.put(keyName, value);
                            break;
                        case GREATER_THAN:
                            gtParams.put(keyName, value);
                            break;
                        case LESS_THAN:
                            ltParams.put(keyName, value);
                            break;
                        case LESS_THAN_EQUAL_TO:
                            leParams.put(keyName, value);
                            break;
                        case GREATER_THAN_EQUAL_TO:
                            geParams.put(keyName, value);
                            break;
                        case LIKE:
                            likeParams.put(keyName, value);
                            break;
                        case NOT_LIKE:
                            nlParams.put(keyName, value);
                            break;
                    }
                    params.put(SearchOperator.IN, inParams);
                    params.put(SearchOperator.EQUAL_TO, eqParams);
                    params.put(SearchOperator.IS_NULL, isNullParams);
                    params.put(SearchOperator.IS_NOT_NULL, isNotNullParams);
                    params.put(SearchOperator.NOT_EQUAL_TO, neParams);
                    params.put(SearchOperator.GREATER_THAN, gtParams);
                    params.put(SearchOperator.LESS_THAN, ltParams);
                    params.put(SearchOperator.LESS_THAN_EQUAL_TO, leParams);
                    params.put(SearchOperator.GREATER_THAN_EQUAL_TO, geParams);
                    params.put(SearchOperator.LIKE, likeParams);
                    params.put(SearchOperator.NOT_LIKE, nlParams);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    throw new ServiceException("Search Params are not in proper format");
                }
            }
        }
        return params;
    }

    public static Predicate[] getPredicatesFromSearchParams(Map<SearchOperator, Map<String, String>> searchParams, Root root, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (Map.Entry<SearchOperator, Map<String, String>> entry : searchParams.entrySet()) {
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
            result = filterVal.stream().map(value -> Enum.valueOf(enumClass, value)).collect(Collectors.toList());
        }
        return result;
    }

    private static void addPredicate(SearchOperator searchOperator, String key, String value, Root root, List<Predicate> predicates, CriteriaBuilder builder) {
        DateFormat formatter = new SimpleDateFormat(Constants.SEARCH_DATE_FORMAT_LONG);
        DateFormat formatterShort = new SimpleDateFormat(Constants.SEARCH_DATE_FORMAT_SHORT);

        Path path = getPath(key, root);

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
                } else if (path.getJavaType().isAssignableFrom(Date.class)) {
                    try {
                        predicates.add(builder.equal((Expression<Date>) path, formatter.parse(value)));
                    } catch (ParseException e) {
                        try {
                            predicates.add(builder.equal((Expression<Date>) path, formatterShort.parse(value)));
                        } catch (ParseException e1) {
                            log.error("Failed to parse the date of Equal operator");
                        }
                    }
                } else if (path.getJavaType().isAssignableFrom(Boolean.class)) {
                    value = value.equals("1") ? "true" : "false";
                    predicates.add(builder.equal(path, Boolean.parseBoolean(value)));
                } else {
                    predicates.add(builder.equal(path, value));
                }
                break;
            case NOT_EQUAL_TO:
                if (path.getJavaType().isEnum()) {
                    predicates.add(builder.notEqual(path, Enum.valueOf(path.getJavaType(), value)));
                } else if (path.getJavaType().getName().lastIndexOf("Date") > -1) {
                    try {
                        predicates.add(builder.notEqual((Expression<Date>) path, formatter.parse(value)));
                    } catch (ParseException e) {
                        try {
                            predicates.add(builder.notEqual((Expression<Date>) path, formatterShort.parse(value)));
                        } catch (ParseException e1) {
                            log.error("Failed to parse the date of Not Equal operator");
                        }
                    }
                } else if (path.getJavaType().isAssignableFrom(Boolean.class)) {
                    value = value.equals("1") ? "true" : "false";
                    predicates.add(builder.notEqual(path, Boolean.parseBoolean(value)));
                } else {
                    predicates.add(builder.notEqual(path, value));
                }
                break;
            case IS_NULL:
                predicates.add(builder.isNull(path));
                break;
            case IS_NOT_NULL:
                predicates.add(builder.isNotNull(path));
                break;
            case GREATER_THAN:
                if (path.getJavaType().isAssignableFrom(Date.class)) {
                    try {
                        predicates.add(builder.greaterThan((Expression<Date>) path, formatter.parse(value)));
                    } catch (ParseException e) {
                        try {
                            predicates.add(builder.greaterThan((Expression<Date>) path, formatterShort.parse(value)));
                        } catch (ParseException e1) {
                            log.error("Failed to parse the date value of Greater Than operator");
                        }
                    }
                } else {
                    predicates.add(builder.greaterThan((Expression<String>) path, value));
                }
                break;
            case LESS_THAN:
                if (path.getJavaType().isAssignableFrom(Date.class)) {
                    try {
                        predicates.add(builder.lessThan((Expression<Date>) path, formatter.parse(value)));
                    } catch (ParseException e) {
                        try {
                            predicates.add(builder.lessThan((Expression<Date>) path, formatterShort.parse(value)));
                        } catch (ParseException e1) {
                            log.error("Failed to parse the date value of Less Than operator");
                        }
                    }
                } else {
                    predicates.add(builder.lessThan((Expression<String>) path, value));
                }
                break;
            case GREATER_THAN_EQUAL_TO:
                if (path.getJavaType().isAssignableFrom(Date.class)) {
                    try {
                        predicates.add(builder.greaterThanOrEqualTo((Expression<Date>) path, formatter.parse(value)));
                    } catch (ParseException e) {
                        try {
                            predicates.add(builder.greaterThanOrEqualTo((Expression<Date>) path, formatterShort.parse(value)));
                        } catch (ParseException e1) {
                            log.error("Failed to parse the date value of Greater Than Equal To operator");
                        }
                    }
                } else {
                    predicates.add(builder.greaterThanOrEqualTo((Expression<String>) path, value));
                }
                break;
            case LESS_THAN_EQUAL_TO:
                if (path.getJavaType().isAssignableFrom(Date.class)) {
                    try {
                        predicates.add(builder.lessThanOrEqualTo((Expression<Date>) path, formatter.parse(value)));
                    } catch (ParseException e) {
                        try {
                            predicates.add(builder.lessThanOrEqualTo((Expression<Date>) path, formatterShort.parse(value)));
                        } catch (ParseException e1) {
                            log.error("Failed to parse the date value of Less Than Equal To operator");
                        }
                    }
                } else {
                    predicates.add(builder.lessThanOrEqualTo((Expression<String>) path, value));
                }
                break;
            case LIKE:
                if (path.getJavaType().isEnum()) {
                    predicates.add(builder.equal((Expression<String>) path, Enum.valueOf(path.getJavaType(), value)));
                } else if (path.getJavaType().isAssignableFrom(Boolean.class)) {
                    value = value.equals("1") ? "true" : "false";
                    predicates.add(builder.equal(path, Boolean.parseBoolean(value)));
                } else {
                    predicates.add(builder.like((Expression<String>) path, value + "%"));
                }
                break;
            case NOT_LIKE:
                if (path.getJavaType().isEnum()) {
                    predicates.add(builder.equal((Expression<String>) path, Enum.valueOf(path.getJavaType(), value)));
                } else if (path.getJavaType().isAssignableFrom(Boolean.class)) {
                    value = value.equals("1") ? "false" : "true";
                    predicates.add(builder.equal(path, Boolean.parseBoolean(value)));
                } else {
                    predicates.add(builder.notLike((Expression<String>) path, value + "%"));
                }
                break;
        }
    }

    private static Path getPath(String key, Root root) {
        Path path = root;
        String secondKey;
        Integer index = key.indexOf("-");
        if (index > -1) {
            String[] data = key.split("-");
            key = data[0];
            secondKey = data[1];
            path = path.get(key);
            if (Collection.class.isAssignableFrom(path.getJavaType())) {
                path = root.join(key).get(secondKey);
            } else {
                path = path.get(secondKey);
            }
        } else {
            path = path.get(key);
        }
        return path;
    }

    public static Pageable getPageRequest(Integer page, Integer fetchSize, String sortBy, String sortOrder) {
        Sort sort;
        if (sortOrder.equals(Sort.Direction.ASC.name())) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, sortBy));
        } else {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, sortBy));
        }
        return new PageRequest(page, fetchSize, sort);
    }
}
