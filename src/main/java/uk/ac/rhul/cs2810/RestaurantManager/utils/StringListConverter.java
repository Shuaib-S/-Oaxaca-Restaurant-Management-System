package uk.ac.rhul.cs2810.RestaurantManager.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return null;
    }
    return attribute.stream()
        .map(this::cleanAndEscape)
        .collect(Collectors.joining(","));
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isEmpty()) {
      return null;
    }
    String cleanData = dbData.replaceAll("^\\{(.*)\\}$", "$1");

    return Arrays.stream(cleanData.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
        .map(this::cleanAndUnescape)
        .collect(Collectors.toList());
  }

  private String cleanAndEscape(String s) {
    s = s.replaceAll("[{}]", "").trim();

    if (s.contains(",") || s.contains(" ")) {
      return "\"" + s.replace("\"", "\"\"") + "\"";
    }
    return s;
  }

  private String cleanAndUnescape(String s) {
    s = s.replaceAll("[{}]", "").trim();

    if (s.startsWith("\"") && s.endsWith("\"")) {
      s = s.substring(1, s.length() - 1);
      s = s.replace("\"\"", "\"");
    }
    return s;
  }
}
