package ro.unitbv.servicesupplier.server.processing.common;

import ro.unitbv.servicesupplier.model.communication.request.common.ServicesRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.ServicesResponse;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;
import ro.unitbv.servicesupplier.server.conversion.ServiceConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class ServicesRequestProcessor implements RequestProcessor<ServicesRequest> {

   @Override
   public ServerResponse process(ServicesRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      List<Service> services = dm.findAll(Service.class);
      List<ServiceDto> serviceDetails = ServiceConverter.toDto(services);

      return new ServicesResponse(serviceDetails);
   }
}
