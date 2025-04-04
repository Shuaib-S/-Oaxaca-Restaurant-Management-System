package uk.ac.rhul.cs2810.RestaurantManager.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter to persist and retrieve List values as a single String in the database.
 */
@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {

  /**
   * Default constructor.
   */
  public StringListConverter() {}

  /**
   * Converts a List of strings into a single comma separated String.
   *
   * @param attribute The list of strings to convert.
   * @return A comma separated string.
   */
  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return null;
    }
    return attribute.stream()
        .map(this::cleanAndEscape)
        .collect(Collectors.joining(","));
  }

  /**
   * Converts a comma separated String from the database back into a List of strings.
   *
   * @param dbData The string data retrieved from the database.
   * @return A list of strings.
   */
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

  /**
   * Escapes and formats a string.
   *
   * @param s The string to clean and escape.
   * @return The escaped string.
   */
  private String cleanAndEscape(String s) {
    s = s.replaceAll("[{}]", "").trim();

    if (s.contains(",") || s.contains(" ")) {
      return "\"" + s.replace("\"", "\"\"") + "\"";
    }
    return s;
  }

  /**
   * Unescapes a database string back to its original form.
   *
   * @param s The string to clean and unescape.
   * @return The unescaped string.
   */
  private String cleanAndUnescape(String s) {
    s = s.replaceAll("[{}]", "").trim();

    if (s.startsWith("\"") && s.endsWith("\"")) {
      s = s.substring(1, s.length() - 1);
      s = s.replace("\"\"", "\"");
    }
    return s;
  }
}
