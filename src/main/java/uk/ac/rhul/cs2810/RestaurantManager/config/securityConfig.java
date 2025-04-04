package uk.ac.rhul.cs2810.RestaurantManager.config;

import static org.springframework.security.config.Customizer.withDefaults;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Config class for setting up security in the applicatioN.
 */
@Configuration
public class securityConfig {

  /**
   * Default constructor required by spring.
   */
  public securityConfig() {}

  /**
   * Configures the HTTP security filter chain.
   * 
   * @param http The HttpSecurity object to configure.
   * @return The configured SecurityFilterChain.
   * @throws Exception if an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable()).cors(withDefaults());

    return http.build();
  }

  /**
   * Configures the CORS policy for the application.
   * 
   * @return A CorsConfigurationSource defining CORS rules.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));
    config.setAllowCredentials(false);
    config.applyPermitDefaultValues();

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }
}
