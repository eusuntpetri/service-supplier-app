package ro.unitbv.servicesupplier.server.processing.provider;

import ro.unitbv.servicesupplier.model.communication.request.provider.ProviderServicesRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.ServicesResponse;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ProviderAccount;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;
import ro.unitbv.servicesupplier.server.conversion.ServiceConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class ProviderServicesRequestProcessor implements RequestProcessor<ProviderServicesRequest> {

   @Override
   public ServerResponse process(ProviderServicesRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getProviderId();
      ProviderAccount provider = dm.findById(ProviderAccount.class, id);

      if (provider == null) {
         return new InvalidDataResponse("providerId");
      }

      List<Service> services = provider.getProvidedServices();
      List<ServiceDto> serviceDetails = ServiceConverter.toDto(services);

      return new ServicesResponse(serviceDetails);
   }
}
