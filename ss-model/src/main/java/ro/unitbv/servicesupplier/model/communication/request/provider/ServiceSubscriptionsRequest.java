package ro.unitbv.servicesupplier.model.communication.request.provider;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class ServiceSubscriptionsRequest implements ServerRequest, Serializable {

   private long serviceId;

   public ServiceSubscriptionsRequest(long serviceId) {
      this.serviceId = serviceId;
   }

   public long getServiceId() {
      return serviceId;
   }

   @Override
   public int getCode() {
      return Codes.GET_SERVICE_SUBSCRIPTIONS;
   }
}
