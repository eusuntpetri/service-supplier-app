package ro.unitbv.servicesupplier.server.system;

import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.techno.persistence.ScheduledPayment;

/**
 * Created by Petri on 19-May-16.
 */
public final class BillingSystem {

   private BillingSystem() {
      // Not instantiable;
   }

   public static void issueBill(Subscription subscription, double paymentDue) {
      DatabaseManager dm = DatabaseManager.getInstance();

      Bill bill = new Bill()
            .setSubscription(subscription)
            .setPaymentDue(paymentDue);

      dm.persist(bill);
      dm.refresh(subscription);

      NotifierSystem.notifyAccount(subscription.getSubscriber(), "You have received a bill for your subscription to " +
            subscription.getService().getDescription() + ". Total payment due: $" +
            (bill.getPaymentDue() + bill.getPaymentOverdue()));
   }

   public static void issuePartialBill(Subscription subscription, ScheduledPayment nextPayment) {
      int elapsedTime = nextPayment.getElapsedTime();

      if (elapsedTime > 0) {
         issueBill(subscription, elapsedTime * nextPayment.getPaymentPerTime());
      }
   }

}
