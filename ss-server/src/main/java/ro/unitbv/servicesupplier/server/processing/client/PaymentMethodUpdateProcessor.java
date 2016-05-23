package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.PaymentMethodUpdateRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.client.PaymentMethodAlreadySetResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InconsistentDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;

/**
 * Created by Petri on 18-May-16.
 */
public class PaymentMethodUpdateProcessor implements RequestProcessor<PaymentMethodUpdateRequest> {

   @Override
   public ServerResponse process(PaymentMethodUpdateRequest request) {
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
         return new InconsistentDataResponse("Subscription of another client cannot be altered!");
      }

      boolean automaticPayment = request.isSolicitingAutomation();

      if (subscription.isPaidAutomatically() == automaticPayment) {
         return new PaymentMethodAlreadySetResponse();
      }

      if (automaticPayment) {
         subscription.automatePayment();
      } else {
         subscription.humanizePayment();
      }

      NotifierSystem.notifyAccount(client,
            "You have set " + (automaticPayment
                  ? "AUTOMATIC"
                  : "MANUAL") + " payment method " +
                  "for your subscription to " + subscription.getService().getDescription());

      return new SuccessResponse("Payment method switched successfully.");
   }
}
