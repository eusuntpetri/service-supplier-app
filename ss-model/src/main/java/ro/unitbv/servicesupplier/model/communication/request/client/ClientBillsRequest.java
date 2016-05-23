package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class ClientBillsRequest implements ServerRequest, Serializable {

   private long clientId;
   private Boolean choosePaidOnly;

   public ClientBillsRequest(long clientId, Boolean choosePaidOnly) {
      this.clientId = clientId;
      this.choosePaidOnly = choosePaidOnly;
   }

   public long getClientId() {
      return clientId;
   }

   public Boolean onlyPaidRequested() {
      return choosePaidOnly;
   }

   @Override
   public int getCode() {
      return Codes.GET_CLIENT_BILLS;
   }
}
