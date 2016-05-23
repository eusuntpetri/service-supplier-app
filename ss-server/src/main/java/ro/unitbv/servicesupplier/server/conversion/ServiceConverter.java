package ro.unitbv.servicesupplier.server.conversion;

import ro.unitbv.servicesupplier.model.dto.ServiceDto;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petri on 17-May-16.
 */
public class ServiceConverter {

   public static List<ServiceDto> toDto(List<Service> services) {
      if (services == null || services.isEmpty()) return Collections.emptyList();

      return services.stream()
            .map(ServiceConverter::toDto)
            .collect(Collectors.toList());
   }

   public static ServiceDto toDto(Service service) {
      return new ServiceDto.Builder()
            .withId(service.getId())
            .fromProvider(service.getProvider().getName())
            .withDescription(service.getDescription())
            .costing(service.getPayment())
            .per(service.getPaymentPeriod())
            .build();
   }

}
