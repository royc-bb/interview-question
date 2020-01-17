package com.example.demo;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.demo.persistence.RecordNotFoundException;
import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlRepository;
import com.example.demo.persistence.UrlService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UrlServiceUnitTest {

  /*
   * Ensure we are testing with the same configuration that will be used to run
   */
  @Value("${app.error-message-text-for-url-not-found}")
  private String recordNotFoundError;

  @Autowired
  private UrlService urlService;

  @MockBean
  private UrlRepository urlRepository;


  /*
   * Save a UrlEntity and return the same UrlEntity
   * 
   * Confirm that the returned UrlEntity has the same properties as the saved UrlEntity
   */
  @Test
  public void whenSaveValidUrlEntity_thenReturnUrlEntity() {
    UrlEntity mockUrlEntity = new UrlEntity("foo", "bar", 999L);
    Mockito.when(urlRepository.save(mockUrlEntity)).thenReturn(mockUrlEntity);

    UrlEntity result = urlService.save(new UrlEntity("foo", "bar", 999L));
    assertThat(result.getLongUrl()).isEqualTo("bar");
    assertThat(result.getShortUrl()).isEqualTo("foo");
    assertThat(result.getTimestamp()).isEqualTo(999L);
  }

  /*
   * Find a UrlEntity with a valid short Url and return a Optional<UrlEntity>
   * 
   * Confirm that the returned UrlEntity has the same properties as the saved UrlEntity
   */
  @Test
  public void whenFindByIdWithGoodShortUrl_thenReturnFoundUrlEntity() {
    UrlEntity mockUrlEntity = new UrlEntity("foo", "bar", 999L);

    try {
      Mockito.when(urlRepository.findById(mockUrlEntity.getShortUrl()))
          .thenReturn(Optional.of(mockUrlEntity));
      UrlEntity result = urlService.findById("foo");
      assertThat(result.getLongUrl()).isEqualTo("bar");
      assertThat(result.getShortUrl()).isEqualTo("foo");
      assertThat(result.getTimestamp()).isEqualTo(999L);
    } catch (RecordNotFoundException e) {
      // this can't happen unless the test is broken
    }

  }

  /*
   * Find a UrlEntity with an invalid short Url and return a RecordNotFoundException
   * 
   * Confirm that the RecordNotFoundException is thrown and has the correct message
   */
  @Test
  public void whenFindByIdWithBadShortUrl_thenReturnRecordNotFoundException() {
    Mockito.when(urlRepository.findById("foobar")).thenReturn(Optional.empty());
    RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> {
      urlService.findById("foobar");
    });
    assertThat(thrown.getMessage()).isEqualTo(recordNotFoundError);

  }

  /*
   * Purge records older than current system time (which would technically be all of them, but this
   * is a mocked unit test so there aren't any records...)
   * 
   * Confirm 1000 records were deleted
   */
  @Test
  public void whenPurgeOldRecords_thenReturnNumberOfRecordsDeleted() {
    Mockito.when(urlRepository.purgeOldRecords(Mockito.anyLong())).thenReturn(1000);
    assertThat(urlService.purgeOldRecords(System.currentTimeMillis())).isEqualTo(1000);
  }


}
