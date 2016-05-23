package ro.unitbv.servicesupplier.server.processing.provider;

import org.apache.commons.lang3.StringUtils;
import ro.unitbv.servicesupplier.model.communication.request.provider.OfferServiceRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ProviderAccount;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;

/**
 * Created by Petri on 17-May-16.
 */
public class OfferServiceProcessor implements RequestProcessor<OfferServiceRequest> {

   @Override
   public ServerResponse process(OfferServiceRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getProviderId();
      ProviderAccount provider = dm.findById(ProviderAccount.class, id);

      if (provider == null) {
         return new InvalidDataResponse("providerId");
      }

      ServiceDto serviceDetails = request.getServiceDetails();
      if (StringUtils.isEmpty(serviceDetails.getDescription())) {
         return new InvalidDataResponse("description");
      } else if (serviceDetails.getPayment() <= 0) {
         return new InvalidDataResponse("payment");
      } else if (serviceDetails.getPaymentPeriod() <= 0) {
         return new InvalidDataResponse("paymentPeriod");
      }

      Service service = new Service(serviceDetails);

      provider.offerService(service);

      NotifierSystem.notifyAccount(provider,
            "You have offered a new service: " + serviceDetails.getDescription());

      return new SuccessResponse("Service created successfully.");
   }
}
