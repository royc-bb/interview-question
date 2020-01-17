package com.example.demo;

import com.example.demo.persistence.UrlEntity;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UrlEntityUnitTest {

  /*
   * Ensure we are testing with the same configuration that will be used to run
   */
  @Value("${app.min-shorturl-length}")
  private int minShortUrlLength;
  @Value("${app.max-shorturl-length}")
  private int maxShortUrlLength;

  /*
   * Create a UrlEntity with a single valid value
   * 
   * Confirm that UrlEntity(String) is generating the correct length short URL and that it does not
   * contain any improper characters.
   * 
   * Confirm that UrlEntity is applying the proper timestamp value.
   */
  @Test
  public void testSingleValueConstructor() {
    UrlEntity singleEntityConstructor = new UrlEntity("foo");

    assertThat(singleEntityConstructor.getLongUrl()).isEqualTo("foo");
    assertThat(singleEntityConstructor.getShortUrl()).doesNotContainAnyWhitespaces()
        .hasSizeGreaterThan(minShortUrlLength - 1).hasSizeLessThan(maxShortUrlLength + 1)
        .doesNotContainPattern(Pattern.compile("[^a-zA-Z0-9]"));
    assertThat(singleEntityConstructor.getTimestamp()).isBetween(System.currentTimeMillis() - 5000,
        System.currentTimeMillis());
  }

  /*
   * Create a UrlEntity with all good values
   * 
   * Confirm that UrlEntity(String, String, long) is storing the provided data correctly and that
   * the fields return properly
   */
  @Test
  public void allValueConstructor_WithGoodValues() {
    UrlEntity urlEntity = new UrlEntity("foo", "bar", 99999L);

    assertThat(urlEntity.getLongUrl()).isEqualTo("bar");
    assertThat(urlEntity.getShortUrl()).isEqualTo("foo");
    assertThat(urlEntity.getTimestamp()).isEqualTo(99999L);
  }

  /*
   * Confirm that UrlEntity(String, String, long) is throwing exceptions appropriately
   */
  @Test
  public void allValueConstructor_NullShortUrlThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new UrlEntity(null, "bar", 99999L);
    });
  }

  @Test
  public void allValueConstructor_NullLongUrlThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new UrlEntity("foo", null, 99999L);
    });
  }

  @Test
  public void allValueConstructor_TooLongShortUrlThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new UrlEntity(StringUtils.repeat("f", 21), "bar", 99999L);
    });
  }

  @Test
  public void allValueConstructor_TooLongLongUrlThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new UrlEntity("foo", StringUtils.repeat("b", 10001), 99999L);
    });
  }


}
