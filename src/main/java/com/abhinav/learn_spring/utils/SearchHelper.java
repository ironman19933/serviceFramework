package com.abhinav.learn_spring.utils;

import com.abhinav.learn_spring.exceptions.ServiceException;
import com.abhinav.learn_spring.models.SearchOperator;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.*;

public class SearchHelper {
    @NotNull
    public static Map<String, Map<String, String>> parseSearchParams(String filters) throws ServiceException {
        Map<String, Map<String, String>> params = new HashMap<>();
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
                    }
                    params.put(SearchOperator.IN.name, inParams);
                    params.put(SearchOperator.EQUAL_TO.name, eqParams);
                    params.put(SearchOperator.IS_NULL.name, isNullParams);
                    params.put(SearchOperator.IS_NOT_NULL.name, isNotNullParams);
                    params.put(SearchOperator.NOT_EQUAL_TO.name, neParams);
                    params.put(SearchOperator.GREATER_THAN.name, gtParams);
                    params.put(SearchOperator.LESS_THAN.name, ltParams);
                    params.put(SearchOperator.LESS_THAN_EQUAL_TO.name, leParams);
                    params.put(SearchOperator.GREATER_THAN_EQUAL_TO.name, geParams);
                    params.put(SearchOperator.LIKE.name, likeParams);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    throw new ServiceException("Search Params are not in proper format");
                }
            }
        }
        return params;
    }
}
