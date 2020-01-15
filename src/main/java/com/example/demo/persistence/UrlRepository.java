package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UrlRepository extends JpaRepository<UrlEntity, String> {


  /**
   * Will delete records in the database that are older than the timestamp provided.
   * 
   * @param timestamp The time in milliseconds used as the basis for deletion.
   * @return The number of records that were deleted.
   */
  @Modifying
  @Transactional
  @Query(value = "delete from urls where `timestamp` < :timestamp", nativeQuery = true)
  public int purgeOldRecords(@Param("timestamp") long timestamp);

}
