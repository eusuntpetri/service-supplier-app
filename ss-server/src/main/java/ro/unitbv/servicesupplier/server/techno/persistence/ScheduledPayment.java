package ro.unitbv.servicesupplier.server.techno.persistence;

import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.time.TimeConstants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Petri on 20-May-16.
 */
@Entity
public class ScheduledPayment implements PrivatelyPersistable {

   @Id
   @GeneratedValue
   private long id;

   private long subscriptionId;

   private long paymentTime;
   private double totalPaymentAssertion;
   private double paymentPerTimeUnit;
   private int elapsedTimeUnits;

   protected ScheduledPayment() {
      // Required by JPA.
   }

   public ScheduledPayment(long nextPaymentBaseTime, Subscription subscription) {
      this.subscriptionId = subscription.getId();

      int paymentPeriod = subscription.getService().getPaymentPeriod();
      this.paymentTime = nextPaymentBaseTime + paymentPeriod * TimeConstants.SWEEP_CYCLE_MILLIS;
      this.totalPaymentAssertion = subscription.getService().getPayment();
      this.paymentPerTimeUnit = totalPaymentAssertion / paymentPeriod;
   }

   @Override
   public long getId() {
      return id;
   }

   public long getSubscriptionId() {
      return subscriptionId;
   }

   public long getPaymentTime() {
      return paymentTime;
   }

   public double getPaymentAssertion() {
      return totalPaymentAssertion;
   }

   public double getPaymentPerTime() {
      return paymentPerTimeUnit;
   }

   public void increaseElapsedTime() {
      PrivatePersistenceManager.getInstance().beginTransaction();
      elapsedTimeUnits++;
      PrivatePersistenceManager.getInstance().commitTransaction();
   }

   public int getElapsedTime() {
      return elapsedTimeUnits;
   }
}