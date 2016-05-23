package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.SubscriptionCancelRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.client.SubscriptionCancelFailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InconsistentDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;
import ro.unitbv.servicesupplier.server.techno.persistence.PrivatePersistenceManager;
import ro.unitbv.servicesupplier.server.techno.system.autopay.PaymentLedgerManager;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscriptionCancelProcessor implements RequestProcessor<SubscriptionCancelRequest> {

   @Override
   public ServerResponse process(SubscriptionCancelRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long clientId = request.getClientId();
      ClientAccount client = dm.findById(ClientAccount.class, clientId);

      if (client == null) {
         return new InvalidDataResponse("clientId");
      }

      long subscriptionId = request.getSubscriptionId();
      Subscription subscription = dm.findById(Subscription.class, subscriptionId);

      if (subscription == null) {
         return new InvalidDataResponse("subscriptionId");
      }
      if (subscription.getSubscriber().getId() != clientId) {
         return new InconsistentDataResponse(
               "Cannot cancel another client's subscription.");
      }
      if (subscription.hasUnpaidBills()) {
         return new SubscriptionCancelFailureResponse(
               "Cannot cancel subscription due to unpaid issued bills.");
      }

      subscription.cancel();

      PaymentLedgerManager.getInstance().concludeManagement(subscription);

      NotifierSystem.notifyAccount(client, "Your subscription to " +
            subscription.getService().getDescription() + " has been canceled.");

      return new SuccessResponse("Subscription cancel successful.");
   }
}
