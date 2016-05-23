package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 16-May-16.
 */
public class ClientSubscriptionsRequest implements ServerRequest, Serializable {

   private long clientId;

   public ClientSubscriptionsRequest(long clientId) {
      this.clientId = clientId;
   }

   public long getClientId() {
      return clientId;
   }

   @Override
   public int getCode() {
      return Codes.GET_CLIENT_SUBSCRIPTS;
   }
}
