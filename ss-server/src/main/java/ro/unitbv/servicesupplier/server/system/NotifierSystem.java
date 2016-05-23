package ro.unitbv.servicesupplier.server.system;

import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;
import ro.unitbv.servicesupplier.repository.persistence.notice.Notice;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;

/**
 * Created by Petri on 19-May-16.
 */
public final class NotifierSystem {

   private NotifierSystem() {
      // Not instantiable.
   }

   public static void notifyAccount(Account account, String message) {
      DatabaseManager dm = DatabaseManager.getInstance();

      Notice notice = new Notice()
            .setAccount(account)
            .setMessage(message);

      dm.persist(notice);
      dm.refresh(account);
   }

   public static void notifySubscriptionUnblocked(Subscription subscription) {
      NotifierSystem.notifyAccount(subscription.getSubscriber(), "Your subscription to " +
            subscription.getService().getDescription() + " has been unblocked!");
   }

}
