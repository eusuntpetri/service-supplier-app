package ro.unitbv.servicesupplier.model.communication.response.success.common;

import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class ServicesResponse extends SuccessResponse implements Serializable {

   private List<ServiceDto> services;

   public ServicesResponse(List<ServiceDto> services) {
      super("Services retrieved successfully.");
      this.services = services;
   }

   public List<ServiceDto> getServices() {
      return services;
   }
}
