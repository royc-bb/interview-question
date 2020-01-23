package com.example.demo.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import com.example.demo.utilities.GenerateShortUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
 * <b>Creating a UrlEntity:</b> The UrlEntity class uses the builder pattern and 2 builders are
 * available:
 * 
 * <p>
 * <b>UrlEntityBuilder.buildShortUrl</b> Creates a UrlEntity with the provided full URL and
 * automatically generates a short URL and the Timestamp. <br>
 * <b>UrlEntityBuilder.build</b> All fields must be populated before calling this method.
 * 
 * @author Roy Cunningham
 *
 */
/*
 * @Value
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
@Entity
@Table(name = "urls")
public class UrlEntity {

  @Id
  @Column(name = "shorturl", length = 130)
  @NonNull
  String shortUrl;

  @Column(name = "longurl", length = 10000)
  @Size(max = 10000)
  @NonNull
  String longUrl;

  @Column(name = "timestamp")
  long timestamp;

  public static class UrlEntityBuilder {
    /**
     * Used to create a UrlEntity with only a full length Url. The short Url and a timestamp will be
     * generated.
     * 
     * @return a new UrlEntity
     * 
     */
    public UrlEntity buildShortUrl() {
      if (longUrl.length() > 10000) {
        throw new IllegalArgumentException(
            "longUrl cannot be more than 10,000 characters in length");
      }
      return new UrlEntity(GenerateShortUrl.generate(longUrl), longUrl, System.currentTimeMillis());
    }

    public UrlEntity build() {
      if (shortUrl.length() > 20) {
        throw new IllegalArgumentException("shortUrl cannot be more than 20 characters in length");
      }
      if (longUrl.length() > 10000) {
        throw new IllegalArgumentException(
            "longUrl cannot be more than 10,000 characters in length");
      }

      return new UrlEntity(shortUrl, longUrl, timestamp);
    }

  }


}
