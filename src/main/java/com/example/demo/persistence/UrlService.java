package com.example.demo.persistence;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provides abstracted database access for required operations.
 * 
 * <p>Supported operations:
 * <br><b>save(UrlEntity):</b> saves a new UrlEntity to the database
 * <br><b>findById(String):</b> retrieves a UrlEntity from a database based on the short URL value
 * <br><b>purgeOldRecords(long):</b> purges records older than provided timestamp (in milliseconds)
 * 
 * @author Roy Cunningham
 *
 */
@Service
public class UrlService {

  @Autowired
  UrlRepository urlRepository;

  @Value("${app.error-message-text-for-url-not-found}")
  private String recordNotFoundError;

  /**
   * Saves a UrlEntity to the database
   * 
   * @param urlEntity The UrlEntity to save
   * @return <b>UrlEntity</b> that was saved
   */
  public UrlEntity save(UrlEntity urlEntity) {
    return urlRepository.save(urlEntity);
  }

  /**
   * Retrieves a UrlEntity from the database based on the provided short Url.
   * 
   * @param shortUrl String value that represents the short Url. This is the primary key in the
   * database.
   * @return <b>UrlEntity</b> if one is found
   * @throws RecordNotFoundException if the provided short Url is not in the database
   */
  public UrlEntity findById(String shortUrl) throws RecordNotFoundException {
    Optional<UrlEntity> urlEntity = urlRepository.findById(shortUrl);

    if (urlEntity.isPresent()) {
      return urlEntity.get();
    } else {
      throw new RecordNotFoundException(recordNotFoundError);
    }
  }

  /**
   * Purge records from the database that have a timestamp older than the provided timestamp.
   * @param timestamp long value representing a timestamp in milliseconds
   * @return <b>int</b> representing the number of deleted records
   */
  public int purgeOldRecords(long timestamp) {
    return urlRepository.purgeOldRecords(timestamp);

  }


}
