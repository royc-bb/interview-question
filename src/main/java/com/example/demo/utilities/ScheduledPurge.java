package com.example.demo.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.demo.persistence.UrlService;

/**
 * Utility class that purges old records from the database based on application configuration
 * 
 * @author Roy Cunningham
 *
 */
@Component
public class ScheduledPurge {
  
  @Autowired
  UrlService urlService;
  
  @Value("${app.max-record-age}")
  private long maxRecordAge;
  
  /**
   * Scheduled task that will delete old records from the database. It has a startup delay of
   * 1 minute to allow the application to get started before the database is hit. 
   * 
   * The scheduled frequency and the age of the records to delete are application configurable
   */
  @Scheduled(fixedDelayString = "${app.purge-record-frequency}", initialDelay = 6000)
  public void purge() {
    System.out.println("purging...");
    urlService.purgeOldRecords(java.lang.System.currentTimeMillis() - (maxRecordAge * 60000L));
  }

}