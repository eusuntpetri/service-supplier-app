package ro.unitbv.servicesupplier.server.processing.provider;

import ro.unitbv.servicesupplier.model.communication.request.provider.ServiceSubscriptionsRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.SubscriptionsResponse;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.conversion.SubscriptionConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class ServiceSubscriptionsRequestProcessor implements RequestProcessor<ServiceSubscriptionsRequest> {

   @Override
   public ServerResponse process(ServiceSubscriptionsRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getServiceId();
      Service service = dm.findById(Service.class, id);

      if (service == null) {
         return new InvalidDataResponse("serviceId");
      }

      List<Subscription> subscriptions = service.getSubscriptions();
      List<SubscriptionDto> subscriptionDetails = SubscriptionConverter.toDto(subscriptions);

      return new SubscriptionsResponse(subscriptionDetails);
   }
}
