package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.SubscriptionRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.client.DuplicateSubscriptionResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.IdResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;
import ro.unitbv.servicesupplier.server.techno.persistence.PrivatePersistenceManager;
import ro.unitbv.servicesupplier.server.techno.system.autopay.PaymentLedgerManager;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscriptionProcessor implements RequestProcessor<SubscriptionRequest> {

   @Override
   public ServerResponse process(SubscriptionRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long clientId = request.getClientId();
      ClientAccount client = dm.findById(ClientAccount.class, clientId);

      if (client == null) {
         return new InvalidDataResponse("clientId");
      }

      long serviceId = request.getServiceId();
      Service service = dm.findById(Service.class, serviceId);

      if (service == null) {
         return new InvalidDataResponse("serviceId");
      }

      Subscription existingSubscription = client.getSubscriptionTo(service);

      if (existingSubscription != null) {
         if (!existingSubscription.isCanceled()) {
            return new DuplicateSubscriptionResponse();
         }

         existingSubscription.resetPeriodSincePayment();
         existingSubscription.humanizePayment();
         existingSubscription.activate();

         PaymentLedgerManager.getInstance().manage(existingSubscription);

         NotifierSystem.notifyAccount(client,
               "You have reactivated your subscription to " + service.getDescription() + ".");

         return response("reactivated", existingSubscription.getId());
      }

      Subscription subscription = client.subscribeToService(service, false);

      PaymentLedgerManager.getInstance().manage(subscription);

      NotifierSystem.notifyAccount(client,
            "You have subscribed to a new service: " + service.getDescription());

      return response("created", subscription.getId());
   }

   private ServerResponse response(String operation, long subscriptionId) {
      return new IdResponse("Subscription " + operation + " successfully. " +
            "Subscription's id attached to response.", subscriptionId);
   }
}
