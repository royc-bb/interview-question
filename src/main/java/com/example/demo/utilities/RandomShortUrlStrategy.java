package com.example.demo.utilities;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Utility class to generate a short value to represent the full length URL.
 * 
 * <p>
 * Using generate() will provide a random string of alphanumeric characters based on the defined
 * length parameters.
 * 
 * <p>
 * TODO Provide for additional generating strategies
 * 
 * @author Roy Cunningham
 *
 */
@Service("RandomString")
public class RandomShortUrlStrategy implements ShortUrlStrategy {

  /**
   * Static fields can't be directly autowired by Spring so the workaround is to pass the values
   * into setter methods. These cannot be generated by Lombok, so are manually defined.
   */
  static int minShortUrlLength;
  static int maxShortUrlLength;

  @Value("${app.min-shorturl-length}")
  public void setMinShortUrlLength(int minShortUrlLength) {
    RandomShortUrlStrategy.minShortUrlLength = minShortUrlLength;
  }

  @Value("${app.max-shorturl-length}")
  public void setMaxShortUrlLength(int maxShortUrlLength) {
    RandomShortUrlStrategy.maxShortUrlLength = maxShortUrlLength;
  }

  /**
   * @return <b>String</b> Random alphanumeric string of length specified in application properties
   */
  @Override
  public String generateUrl(String input) {
    return RandomStringUtils.randomAlphanumeric(minShortUrlLength, maxShortUrlLength);
  }

}