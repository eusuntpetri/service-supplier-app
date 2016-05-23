package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.ClientSubscriptionsRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.SubscriptionsResponse;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.conversion.SubscriptionConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

import java.util.List;

/**
 * Created by Petri on 16-May-16.
 */
public class ClientSubscriptionsRequestProcessor implements RequestProcessor<ClientSubscriptionsRequest> {

   @Override
   public ServerResponse process(ClientSubscriptionsRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getClientId();
      ClientAccount client = dm.findById(ClientAccount.class, id);

      if (client == null) {
         return new InvalidDataResponse("clientId");
      }

      List<Subscription> subscriptions = client.getActiveSubscriptions();
      List<SubscriptionDto> subscriptionDetails = SubscriptionConverter.toDto(subscriptions);

      return new SubscriptionsResponse(subscriptionDetails);
   }
}
