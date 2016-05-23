package ro.unitbv.servicesupplier.repository.persistence.bill;

import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Petri on 17-May-16.
 */
@Entity
public class Bill implements Persistable, Comparable<Bill> {

   @Id
   @GeneratedValue
   private long id;
   @ManyToOne
   @JoinColumn(name = "SUBSCRIPT_ID")
   private Subscription subscription;
   private Timestamp issueDate;
   private double paymentDue;
   private double paymentOverdue;
   private boolean isPaid;

   public Bill() {
      this.issueDate = Timestamp.valueOf(LocalDateTime.now());
   }

   @Override
   public long getId() {
      return id;
   }

   public Subscription getSubscription() {
      return subscription;
   }

   public Bill setSubscription(Subscription subscription) {
      this.subscription = subscription;
      return this;
   }

   public Timestamp getIssueDate() {
      return issueDate;
   }

   public double getPaymentDue() {
      return paymentDue;
   }

   public Bill setPaymentDue(double paymentDue) {
      this.paymentDue = paymentDue;
      return this;
   }

   public double getPaymentOverdue() {
      return paymentOverdue;
   }

   public Bill setPaymentOverdue(double paymentOverdue) {
      this.paymentOverdue = paymentOverdue;
      return this;
   }

   public double getTotalDue() {
      return paymentDue + paymentOverdue;
   }

   public boolean isPaid() {
      return isPaid;
   }

   public void setPaid() {
      isPaid = true;
   }

   @Override
   public int compareTo(Bill o) {
      return this.issueDate.after(o.issueDate) ? 1 : -1;
   }
}
