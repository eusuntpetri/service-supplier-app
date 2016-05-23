package ro.unitbv.servicesupplier.server.techno.system.autopay;

import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;

import java.util.List;

/**
 * Created by Petri on 21-May-16.
 */
public final class AutomaticPaymentSystem {

   private AutomaticPaymentSystem() {
      // Not instantiable.
   }

   static void commenceSweep() {
      List<ClientAccount> clients = DatabaseManager.getInstance().findAll(ClientAccount.class);

      for (ClientAccount account : clients) {
         attemptAutomaticPayment(account);
      }
   }

   public static void attemptAutomaticPayment(ClientAccount account) {
      if (account == null) return;

      List<Bill> autoPayBills = account.getAutomaticallyPayableBills();
      autoPayBills.sort(null);

      for (Bill bill : autoPayBills) {
         if (account.getBalance() >= bill.getTotalDue()) {
            payBillForClient(account, bill);
            Subscription subscription = bill.getSubscription();
            if(subscription.isBlocked() && bill.getPaymentOverdue() > 0) {
               subscription.unblock();
               NotifierSystem.notifySubscriptionUnblocked(subscription);
            }
         }
      }
   }

   private static void payBillForClient(ClientAccount account, Bill bill) {
      DatabaseManager dm = DatabaseManager.getInstance();
      dm.beginTransaction();
      try {
         account.retract(bill.getTotalDue());
         bill.setPaid();
         bill.getSubscription().resetPeriodSincePayment();
         dm.commitTransaction();

      } catch (Exception e) {
         System.err.println("Automatic bill payment interrupted! Rolling back and skipping...");
         e.printStackTrace();
         dm.rollbackTransaction();
         return;
      }

      NotifierSystem.notifyAccount(account, "Your bill to " +
            bill.getSubscription().getService().getProvider().getName() +
            " for " + bill.getSubscription().getService().getDescription() +
            " has been automatically paid for. " +
            "Total: $" + (bill.getPaymentDue() + bill.getPaymentOverdue()));
   }
}
