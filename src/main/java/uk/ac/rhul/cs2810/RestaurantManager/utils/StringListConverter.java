package uk.ac.rhul.cs2810.RestaurantManager.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true) // Automatically applies to all List<String> fields
public class StringListConverter implements AttributeConverter<List<String>, String> {

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    return (attribute == null || attribute.isEmpty()) ? null : String.join(",", attribute);
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    return (dbData == null || dbData.isEmpty()) ? null : Arrays.asList(dbData.split(","));
  }
}
