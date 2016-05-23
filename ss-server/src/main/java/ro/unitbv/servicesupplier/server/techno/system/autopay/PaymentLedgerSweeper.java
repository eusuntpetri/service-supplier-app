package ro.unitbv.servicesupplier.server.techno.system.autopay;

import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.system.BillingSystem;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;
import ro.unitbv.servicesupplier.server.techno.persistence.PrivatePersistenceManager;
import ro.unitbv.servicesupplier.server.techno.persistence.ScheduledPayment;
import ro.unitbv.servicesupplier.server.techno.system.TechnoSystem;
import ro.unitbv.servicesupplier.server.time.TimeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Petri on 19-May-16.
 */
public final class PaymentLedgerSweeper implements Runnable {

   private static final PaymentLedgerSweeper INSTANCE = new PaymentLedgerSweeper();

   private static final PaymentLedgerManager LEDGER_MANAGER = PaymentLedgerManager.getInstance();
   private boolean sweeping;

   private PaymentLedgerSweeper() {
      // Singleton.
   }

   public static PaymentLedgerSweeper getInstance() {
      return INSTANCE;
   }

   public void waitWhileSweeping() {
      synchronized (LEDGER_MANAGER) {
         while (INSTANCE.sweeping) {
            try {
               LEDGER_MANAGER.wait();
            } catch (InterruptedException e) {
               System.err.println("Thread waiting on ledger sweep was interrupted!");
               e.printStackTrace();
            }
         }
      }
   }

   @Override
   public void run() {
      synchronized (LEDGER_MANAGER) {
         this.sweeping = false;
         LEDGER_MANAGER.notifyAll();
      }
      if (!sleepFullTime()) {
         DatabaseManager.getInstance().close();
         PrivatePersistenceManager.getInstance().close();
         return;
      }
      this.sweeping = true;
      sweepLedger();
      if (TechnoSystem.isTerminated()) {
         DatabaseManager.getInstance().close();
         PrivatePersistenceManager.getInstance().close();
         return;
      }
      run();
   }

   private void sweepLedger() {

      commenceBillingSweep();

      AutomaticPaymentSystem.commenceSweep();
   }

   private void commenceBillingSweep() {
      long sweepTime = System.currentTimeMillis();
      DatabaseManager dm = DatabaseManager.getInstance();
      PrivatePersistenceManager ppm = PrivatePersistenceManager.getInstance();

      for (Map.Entry<Long, ScheduledPayment> entry : LEDGER_MANAGER.getEntriesForSweep()) {
         Subscription subscription = dm.findById(Subscription.class, entry.getKey());

         if (subscription.isBlocked()) {
            increasePenalty(subscription);
            return;
         }

         ScheduledPayment scheduledPayment = entry.getValue();
         scheduledPayment = ppm.refresh(scheduledPayment);

         subscription.increasePeriodSincePayment();
         scheduledPayment.increaseElapsedTime();

         if (sweepTime >= scheduledPayment.getPaymentTime()) {

            BillingSystem.issueBill(subscription, scheduledPayment.getPaymentAssertion());
            ppm.remove(scheduledPayment);

            if (subscription.getUnpaidBills().size() == 3) {
               blockAndPenalize(subscription);
            } else {
               ScheduledPayment nextPayment = new ScheduledPayment(sweepTime, subscription);
               ppm.persist(nextPayment);
               entry.setValue(nextPayment);
            }
         }
      }
   }

   private void blockAndPenalize(Subscription subscription) {
      subscription.block();
      increaseOverduePayment(subscription);

      NotifierSystem.notifyAccount(subscription.getSubscriber(),
            "Your subscription to " + subscription.getService().getDescription() +
                  " has been blocked due to multiple unpaid bills! " +
                  "Service will be discontinued pending overdue bill payment!");
   }

   private void increasePenalty(Subscription subscription) {
      increaseOverduePayment(subscription);

      NotifierSystem.notifyAccount(subscription.getSubscriber(),
            "Overdue payment has been increased for your blocked subscription to " +
                  subscription.getService().getDescription() + "! " +
                  "Please pay overdue bills to reactivate services.");
   }

   private void increaseOverduePayment(Subscription subscription) {
      DatabaseManager dm = DatabaseManager.getInstance();

      List<Bill> bills = new ArrayList<>(subscription.getUnpaidBills());
      bills.sort(null);

      Bill penalizedBill = bills.get(0);
      double overdue = penalizedBill.getPaymentOverdue();

      dm.beginTransaction();
      penalizedBill.setPaymentOverdue(overdue +
            (penalizedBill.getPaymentDue() + overdue) / 20);
      dm.commitTransaction();
   }


   private long sleepStartTime;

   private boolean sleepFullTime() {
      sleepStartTime = System.currentTimeMillis();
      try {
         TimeUnit.MILLISECONDS.sleep(TimeConstants.SWEEP_CYCLE_MILLIS);
         return true;
      } catch (InterruptedException e) {
         if (TechnoSystem.isTerminated()) {
            return false;
         }
         printInterruptionMessage(e);
         return sleepRemainingTime();
      }
   }

   private boolean sleepRemainingTime() {
      try {
         TimeUnit.MILLISECONDS.sleep(
               TimeConstants.SWEEP_CYCLE_MILLIS - (System.currentTimeMillis() - sleepStartTime));
         return true;
      } catch (InterruptedException e) {
         if (TechnoSystem.isTerminated()) {
            return false;
         }
         printInterruptionMessage(e);
         return sleepRemainingTime();
      }
   }

   private void printInterruptionMessage(InterruptedException e) {
      System.err.println("Ledger Sweeper's sleep cycle interrupted! Printing stack trace and resuming.");
      e.printStackTrace();
   }

}
