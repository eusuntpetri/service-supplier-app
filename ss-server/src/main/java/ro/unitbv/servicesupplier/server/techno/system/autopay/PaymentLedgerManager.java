package ro.unitbv.servicesupplier.server.techno.system.autopay;

import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.system.BillingSystem;
import ro.unitbv.servicesupplier.server.techno.persistence.PrivatePersistenceManager;
import ro.unitbv.servicesupplier.server.techno.persistence.ScheduledPayment;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Petri on 19-May-16.
 */
public final class PaymentLedgerManager {

   private static final PaymentLedgerManager INSTANCE = new PaymentLedgerManager();

   private static final PaymentLedgerSweeper LEDGER_SWEEPER = PaymentLedgerSweeper.getInstance();

   private final Map<Long, ScheduledPayment> subscriptionPaymentLedger = new ConcurrentHashMap<>();

   private PaymentLedgerManager() {
      // Singleton.
      reload();
   }

   public static PaymentLedgerManager getInstance() {
      return INSTANCE;
   }

   private void reload() {
      PrivatePersistenceManager ppm = PrivatePersistenceManager.getInstance();
      List<ScheduledPayment> scheduledPayments = ppm.findAll(ScheduledPayment.class);

      for (ScheduledPayment scheduledPayment : scheduledPayments) {
         subscriptionPaymentLedger.put(scheduledPayment.getSubscriptionId(), scheduledPayment);
      }

      ppm.close();
   }

   public void manage(Subscription subscription) {
      if (subscription == null) return;
      LEDGER_SWEEPER.waitWhileSweeping();

      if (isManaging(subscription)) {
         return;
      }

      ScheduledPayment nextPayment = new ScheduledPayment(System.currentTimeMillis(), subscription);
      PrivatePersistenceManager.getInstance().persist(nextPayment);
      subscriptionPaymentLedger.put(subscription.getId(), nextPayment);
   }

   public boolean isManaging(Subscription subscription) {
      return subscriptionPaymentLedger.containsKey(subscription.getId());
   }

   public void concludeManagement(Subscription subscription) {
      if (subscription == null) return;
      LEDGER_SWEEPER.waitWhileSweeping();

      if (!isManaging(subscription)) {
         return;
      }

      ScheduledPayment scheduledPayment = getScheduledPayment(subscription.getId());
      BillingSystem.issuePartialBill(subscription, scheduledPayment);
      PrivatePersistenceManager.getInstance().remove(scheduledPayment);
      subscriptionPaymentLedger.remove(subscription.getId());
   }

   public ScheduledPayment getScheduledPayment(long subscriptionId) {
      ScheduledPayment scheduledPayment = subscriptionPaymentLedger.get(subscriptionId);
      scheduledPayment = PrivatePersistenceManager.getInstance().refresh(scheduledPayment);
      return scheduledPayment;
   }

   Set<Map.Entry<Long, ScheduledPayment>> getEntriesForSweep() {
      return subscriptionPaymentLedger.entrySet();
   }
}
