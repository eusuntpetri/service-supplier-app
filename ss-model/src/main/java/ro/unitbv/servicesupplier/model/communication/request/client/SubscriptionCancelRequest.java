package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscriptionCancelRequest implements ServerRequest, Serializable {

   private long clientId;
   private long subscriptionId;

   public SubscriptionCancelRequest(long clientId, long subscriptionId) {
      this.clientId = clientId;
      this.subscriptionId = subscriptionId;
   }

   public long getClientId() {
      return clientId;
   }

   public long getSubscriptionId() {
      return subscriptionId;
   }

   @Override
   public int getCode() {
      return Codes.CANCEL_SUBSCRIPTION;
   }
}
