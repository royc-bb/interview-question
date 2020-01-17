package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.persistence.RecordNotFoundException;
import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlService;

/**
 * REST controller for the url shortener
 * 
 * Endpoint and query parameter names can be configured in the application properties or via 
 * server startup parameters.
 *  
 * @author Roy Cunningham
 *
 */
@RestController
@RequestMapping("${app.rest-endpoint-base-name}")
public class UrlShortenerController {
  
  @Value("${app.error-message-text-for-url-not-found}")
  private String recordNotFoundError;
  
  @Autowired
  UrlService urlService;
  
  /**
   * Handles GET requests with a full url passed in. Creates a UrlEntity and hands it off to be 
   * saved in the database.
   *   
   * @param longUrl
   * @return <p>String<p> The short url
   */
  @GetMapping("${app.rest-endpoint-name-for-shorturl}") 
  public String getShortUrl(@RequestParam(name="${app.rest-query-name-for-shorturl}") String longUrl) {
    //return new UrlEntity(longUrl).getShortUrl();
    return urlService.save(new UrlEntity(longUrl)).getShortUrl();
  }
  
  /**
   * Handles GET requests with a short url passed in. Looks that up in the database and returns 
   * the corresponding full url or an error if it's not found.
   * 
   * The error message is configurable in the application properties or by startup parameter.
   * 
   * @param shortUrl
   * @return <p>String</p> The full url or an error if not found
   */
  @GetMapping("${app.rest-endpoint-name-for-longurl}") 
  public String getLongUrl(@RequestParam(name="${app.rest-query-name-for-longurl}") String shortUrl) {
    try {
      return urlService.findById(shortUrl).getLongUrl();
    } catch (RecordNotFoundException e) {
      return recordNotFoundError;
    }
  }

}
