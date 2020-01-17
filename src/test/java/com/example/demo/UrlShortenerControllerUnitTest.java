package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.demo.persistence.RecordNotFoundException;
import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UrlShortenerControllerUnitTest {

  /*
   * Ensure we are testing with the same configuration that will be used to run
   */
  @Value("${app.error-message-text-for-url-not-found}")
  private String recordNotFoundError;

  @Autowired
  UrlShortenerController controller;

  @MockBean
  UrlService urlService;

  /*
   * Provide a full url and return a short url. The normally generated shortUrl value is mocked up
   * in this case.
   * 
   * Confirm that the short url matches the mocked value.
   */
  @Test
  public void whenRequestShortUrl_thenReturnShortUrl() {
    UrlEntity urlEntity = new UrlEntity("awesome", "http://foo.bar", 999L);

    Mockito.when(urlService.save(Mockito.any())).thenReturn(urlEntity);
    String result = controller.getShortUrl("http://foo.bar");
    assertThat(result).isEqualTo(urlEntity.getShortUrl());
  }

  /*
   * Provide a valid short url and return the corresponding full url.
   * 
   * Confirm that the returned full url matches the expected value.
   */
  @Test
  public void whenRequestLongUrlWithValidShortUrl_thenReturnLongUrl() {
    try {
      Mockito.when(urlService.findById("awesome"))
          .thenReturn(new UrlEntity("http://some.web.address/"));
      String result = controller.getLongUrl("awesome");
      assertThat(result).isEqualTo("http://some.web.address/");
    } catch (RecordNotFoundException e) {
      // this shouldn't happen unless there's an error with the test
    }
  }

  /*
   * Provide an invalid short url and return the configured error message.
   * 
   * Confirm that the returned error message matches the configured value.
   */
  @Test
  public void whenRequestLongUrlWithInvalidShortUrl_thenReturnError() {
    try {
      Mockito.when(urlService.findById("foobar")).thenThrow(RecordNotFoundException.class);
      assertThat(controller.getLongUrl("foobar")).isEqualTo(recordNotFoundError);
    } catch (RecordNotFoundException e) {
      // this shouldn't happen unless there's an error with the test
    }
  }

}
