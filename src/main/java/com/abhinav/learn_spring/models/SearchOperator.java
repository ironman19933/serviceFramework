package com.abhinav.learn_spring.models;

public enum SearchOperator {
    EQUAL_TO("eq"),
    NOT_EQUAL_TO("ne"),
    IS_NULL("isNull"),
    IS_NOT_NULL("nn"),
    GREATER_THAN("gt"),
    GREATER_THAN_EQUAL_TO("ge"),
    LESS_THAN("lt"),
    LESS_THAN_EQUAL_TO("le"),
    LIKE("like"),
    NOT_LIKE("nl"),
    IN("in");

    public String name;

    SearchOperator(String msg) {
        this.name = msg;
    }

    public static SearchOperator value(String operator) {
        for (SearchOperator searchOperator : SearchOperator.values()) {
            if (searchOperator.name.equalsIgnoreCase(operator)) {
                return searchOperator;
            }
        }
        return null;
    }
}
