package ro.unitbv.servicesupplier.repository.persistence.notice;

import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Petri on 17-May-16.
 */
@Entity
public class Notice implements Persistable, Comparable<Notice> {

   @Id
   @GeneratedValue
   private long id;
   @ManyToOne
   @JoinColumn(name = "ACCOUNT_ID")
   private Account account;
   private Timestamp issueDate;
   private String message;

   public Notice() {
      this.issueDate = Timestamp.valueOf(LocalDateTime.now());
   }

   @Override
   public long getId() {
      return id;
   }

   public Account getAccount() {
      return account;
   }

   public Notice setAccount(Account account) {
      this.account = account;
      return this;
   }

   public Timestamp getIssueDate() {
      return issueDate;
   }

   public String getMessage() {
      return message;
   }

   public Notice setMessage(String message) {
      this.message = message;
      return this;
   }

   @Override
   public int compareTo(Notice o) {
      return this.issueDate.after(o.issueDate) ? 1 : -1;
   }
}
