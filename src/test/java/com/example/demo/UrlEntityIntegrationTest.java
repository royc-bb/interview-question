package com.example.demo;

import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlRepository;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlEntityIntegrationTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private UrlRepository urlRepository;

  /*
   * Create a UrlEntity with maximum values
   * 
   * Push UrlEntity to the mock database
   * 
   * Search for UrlEntity in the mock database and retrieve it
   * 
   * Confirm the data being saved to the mock database matches the data that is retrieved
   */
  @Test
  public void whenFindByShortUrl_thenReturnUrlEntity() {
    UrlEntity urlEntity = new UrlEntity("fooooooooooooooooooo", StringUtils.repeat("b", 10000), Long.MAX_VALUE);
    
    testEntityManager.persist(urlEntity);

    Optional<UrlEntity> foundUrlEntity = urlRepository.findById("fooooooooooooooooooo");
    
    assertThat(foundUrlEntity.get()).isEqualTo(urlEntity);
  }

}
