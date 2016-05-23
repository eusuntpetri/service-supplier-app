package ro.unitbv.servicesupplier.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Petri on 17-May-16.
 */
public class NoticeDto implements Serializable, Comparable<NoticeDto> {

   private long id;
   private Timestamp issueDate;
   private String message;

   public NoticeDto(long id, String message, Timestamp issueDate) {
      this.id = id;
      this.message = message;
      this.issueDate = issueDate;
   }

   public long getId() {
      return id;
   }

   public String getMessage() {
      return message;
   }

   public Timestamp getIssueDate() {
      return issueDate;
   }

   @Override
   public int compareTo(NoticeDto o) {
      return this.id > o.id ? 1 : -1;
   }
}
