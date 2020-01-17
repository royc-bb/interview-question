package com.example.demo.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.example.demo.utilities.GenerateShortUrl;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * A UrlEntity object represents a full length URL and it's short version.
 * 
 * <p>
 * A UrlEntity object logically consists of the fields described below.
 * 
 * <p>
 * <b>shortUrl:</b> A shortened version of the full length URL. This field is used for looking up
 * the full length URL from a database. It has a maximum length of 20 characters.
 * 
 * <p>
 * <b>longUrl:</b> The full length URL. It has a maximum length of 10,000 characters.
 * 
 * <p>
 * <b>timestamp:</b> The time the entity was created represented in milliseconds. The value of this
 * field is set at creation time based on the current system time.
 * 
 * <p>
 * <b>Creating a UrlEntity:</b> The UrlEntity class provides two constructors.
 * 
 * <p>
 * <b>UrlEntity(String longUrl)</b> Creates a UrlEntity with the provided full URL and automatically
 * generates a short URL and the Timestamp. <br>
 * <b>UrlEntity(String, String, long)</b> Used by Spring to create a UrlEntity based on a record
 * retrieved from a database. Also used by UrlEntity(String longUrl) to create a UrlEntity when only
 * the full length URL is provided.
 * 
 * @author Roy Cunningham
 *
 */
@Value
@NoArgsConstructor(force = true)
@Entity
@Table(name = "urls")
public class UrlEntity {

  @Id
  @Column(name = "shorturl", length = 20)
  String shortUrl;

  @Column(name = "longurl", length = 10000)
  String longUrl;

  @Column(name = "timestamp")
  long timestamp;

  public UrlEntity(String shortUrl, String longUrl, long timestamp) {
    if (shortUrl == null) {
      throw new IllegalArgumentException("shortUrl cannot be null");
    }
    if (shortUrl.length() > 20) {
      throw new IllegalArgumentException("shortUrl cannot be more than 20 characters in length");
    }
    if (longUrl == null) {
      throw new IllegalArgumentException("longUrl cannot be null");
    }
    if (longUrl.length() > 10000) {
      throw new IllegalArgumentException("longUrl cannot be more than 10,000 characters in length");
    }

    this.shortUrl = shortUrl;
    this.longUrl = longUrl;
    this.timestamp = timestamp;
  }

  public UrlEntity(String longUrl) {
    this(GenerateShortUrl.generate(), longUrl, System.currentTimeMillis());
  }


}
