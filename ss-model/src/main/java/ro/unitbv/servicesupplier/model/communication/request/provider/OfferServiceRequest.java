package ro.unitbv.servicesupplier.model.communication.request.provider;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class OfferServiceRequest implements ServerRequest, Serializable {

   private long providerId;
   private ServiceDto serviceDetails;

   public OfferServiceRequest(long providerId, ServiceDto serviceDetails) {
      this.providerId = providerId;
      this.serviceDetails = serviceDetails;
   }

   public long getProviderId() {
      return providerId;
   }

   public ServiceDto getServiceDetails() {
      return serviceDetails;
   }

   @Override
   public int getCode() {
      return Codes.OFFER_SERVICE;
   }
}
