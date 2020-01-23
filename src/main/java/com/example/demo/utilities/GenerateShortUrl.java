package com.example.demo.utilities;

import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
@Service
public class GenerateShortUrl {

  private static Map<String, ShortUrlStrategy> shortUrlStrategyImpls;
  private static String stratName;

  @Autowired
  public void setImpls(Map<String, ShortUrlStrategy> impls) {
    GenerateShortUrl.shortUrlStrategyImpls = impls;
  }

  @Value("${app.shorturl-strategy}")
  public void setStratName(String stratname) {
    GenerateShortUrl.stratName = stratname;
  }

  public static String generate(String input) {
    return shortUrlStrategyImpls.get(stratName).generateUrl(input);

  }

}
