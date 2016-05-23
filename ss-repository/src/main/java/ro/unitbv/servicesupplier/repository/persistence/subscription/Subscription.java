package ro.unitbv.servicesupplier.repository.persistence.subscription;

import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri on 15-May-16.
 */
@Entity
public class Subscription implements Persistable {

   @Id
   @GeneratedValue
   private long id;
   @ManyToOne
   @JoinColumn(name = "CLIENT_ID")
   private ClientAccount subscriber;
   @ManyToOne
   @JoinColumn(name = "SERVICE_ID")
   private Service service;
   private Timestamp activationDate;
   private boolean isBlocked;
   private boolean isCanceled;
   private int periodSincePayment;
   private boolean paymentAutomated;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscription")
   private List<Bill> issuedBills;

   public Subscription() {
      this.activationDate = Timestamp.valueOf(LocalDateTime.now());
      this.issuedBills = new ArrayList<>();
   }

   @Override
   public long getId() {
      return id;
   }

   public ClientAccount getSubscriber() {
      return subscriber;
   }

   public Subscription setSubscriber(ClientAccount subscriber) {
      this.subscriber = subscriber;
      return this;
   }

   public Service getService() {
      return service;
   }

   public Subscription setService(Service service) {
      this.service = service;
      return this;
   }

   public Timestamp getActivationDate() {
      return activationDate;
   }

   public void cancel() {
      DatabaseManager.getInstance().beginTransaction();
      isCanceled = true;
      DatabaseManager.getInstance().commitTransaction();
   }

   public void activate() {
      DatabaseManager.getInstance().beginTransaction();
      isCanceled = false;
      activationDate = Timestamp.valueOf(LocalDateTime.now());
      DatabaseManager.getInstance().commitTransaction();
   }

   public boolean isCanceled() {
      return isCanceled;
   }

   public void block() {
      DatabaseManager.getInstance().beginTransaction();
      isBlocked = true;
      DatabaseManager.getInstance().commitTransaction();
   }

   public void unblock() {
      DatabaseManager.getInstance().beginTransaction();
      isBlocked = false;
      DatabaseManager.getInstance().commitTransaction();
   }

   public boolean isBlocked() {
      return isBlocked;
   }

   public int getPeriodSincePayment() {
      return periodSincePayment;
   }

   public void increasePeriodSincePayment() {
      DatabaseManager.getInstance().beginTransaction();
      periodSincePayment++;
      DatabaseManager.getInstance().commitTransaction();
   }

   public void resetPeriodSincePayment() {
      periodSincePayment = 0;
   }

   public boolean isPaidAutomatically() {
      return paymentAutomated;
   }

   public Subscription setAutomaticPayment(boolean value) {
      paymentAutomated = value;
      return this;
   }

   public void automatePayment() {
      DatabaseManager.getInstance().beginTransaction();
      paymentAutomated = true;
      DatabaseManager.getInstance().commitTransaction();
   }

   public void humanizePayment() {
      DatabaseManager.getInstance().beginTransaction();
      paymentAutomated = false;
      DatabaseManager.getInstance().commitTransaction();
   }

   public List<Bill> getIssuedBills() {
      return issuedBills;
   }

   public List<Bill> getUnpaidBills() {
      List<Bill> unpaidBills = new ArrayList<>();

      for (Bill bill : issuedBills) {
         if (!bill.isPaid()) {
            unpaidBills.add(bill);
         }
      }

      return unpaidBills;
   }

   public Bill getLastBill() {
      List<Bill> sortedBills = new ArrayList<>(issuedBills);

      sortedBills.sort(null);

      return sortedBills.isEmpty() ? null : sortedBills.get(sortedBills.size() - 1);
   }

   public boolean hasUnpaidBills() {
      for (Bill bill : issuedBills) {
         if (!bill.isPaid()) {
            return true;
         }
      }
      return false;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Subscription that = (Subscription) o;

      return id == that.id;
   }

   @Override
   public int hashCode() {
      return (int) (id ^ (id >>> 32));
   }
}
