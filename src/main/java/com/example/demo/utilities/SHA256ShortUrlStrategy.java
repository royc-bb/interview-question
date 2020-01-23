package com.example.demo.utilities;

import org.apache.commons.codec.digest.DigestUtils;
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
@Service("SHA256")
public class SHA256ShortUrlStrategy implements ShortUrlStrategy {

  /**
   * Static fields can't be directly autowired by Spring so the workaround is to pass the values
   * into setter methods. These cannot be generated by Lombok, so are manually defined.
   */
  static int minShortUrlLength;
  static int maxShortUrlLength;

  @Value("${app.min-shorturl-length}")
  public void setMinShortUrlLength(int minShortUrlLength) {
    SHA256ShortUrlStrategy.minShortUrlLength = minShortUrlLength;
  }

  @Value("${app.max-shorturl-length}")
  public void setMaxShortUrlLength(int maxShortUrlLength) {
    SHA256ShortUrlStrategy.maxShortUrlLength = maxShortUrlLength;
  }

  /**
   * @return <b>String</b> Random alphanumeric string of length specified in application properties
   */
  @Override
  public String generateUrl(String input) {
    return DigestUtils.sha256Hex(input);
    // return RandomStringUtils.randomAlphanumeric(minShortUrlLength, maxShortUrlLength);
  }

}