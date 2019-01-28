package com.abhinav.learn_spring.models;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

public class StringListAttributeConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Arrays.asList(dbData.split("\\s*,\\s*"));
    }
}
