package com.example.demo;

import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * Testing to confirm that UrlEntity can be stored and retrieved from UrlRepository. The database is
 * an embedded temporary db.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class UrlRepositoryIntegrationTest {

  @Autowired
  private UrlRepository urlRepository;

  private UrlEntity urlEntity =
      new UrlEntity("fooooooooooooooooooo", StringUtils.repeat("b", 10000), Long.MAX_VALUE);

  @BeforeAll
  public void setup() {
    urlRepository.save(urlEntity);
  }

  /*
   * Save a UrlEntity to the database and return the saved UrlEntity
   * 
   * Confirm that the saved UrlEntity matches the returned UrlEntity
   */
  @Test
  public void whenSaveUrlEntity_thenReturnSavedUrlEntity() {
    UrlEntity urlEntity = new UrlEntity("valid", "foo", 9999L);
    assertThat(urlRepository.save(urlEntity)).isEqualTo(urlEntity);
  }

  /*
   * Search for UrlEntity in the database with a valid short url and retrieve it
   * 
   * Confirm the returned UrlEntity matches the UrlEntity that was requested
   */
  @Test
  public void whenFindByValidShortUrl_thenReturnUrlEntity() {
    assertThat(urlRepository.findById("fooooooooooooooooooo").get()).isEqualTo(urlEntity);
  }

  /*
   * Search for UrlEntity with an invalid short url
   * 
   * Confirm that no UrlEntity is returned
   */
  @Test
  public void whenFindByInvalidShortUrl_thenReturnEmptyOptional() {
    assertThat(urlRepository.findById("invalid").isPresent()).isFalse();
  }

  /*
   * Purge records older than 0 which will match no records
   * 
   * Confirm that the deleted record count is 0
   */
  @Test
  public void whenPurgeOldRecords_thenReturnZero() {
    assertThat(urlRepository.purgeOldRecords(0)).isEqualTo(0);
  }

  /*
   * Purge records older than the maximum value which will match the seed record and delete it
   * 
   * Confirm that the deleted record count is greater than 0
   */
  @Test
  @AfterAll
  public void whenPurgeOldRecords_thenReturnNumberOfRecordsPurged() {
    assertThat(urlRepository.purgeOldRecords(Long.MAX_VALUE)).isGreaterThan(0);
  }



}
