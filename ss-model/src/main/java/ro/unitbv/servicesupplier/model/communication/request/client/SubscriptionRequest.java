package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscriptionRequest implements ServerRequest, Serializable {

   private long clientId;
   private long serviceId;

   public SubscriptionRequest(long clientId, long serviceId) {
      this.clientId = clientId;
      this.serviceId = serviceId;
   }

   public long getServiceId() {
      return serviceId;
   }

   public long getClientId() {
      return clientId;
   }

   @Override
   public int getCode() {
      return Codes.SUBSCRIBE;
   }
}
