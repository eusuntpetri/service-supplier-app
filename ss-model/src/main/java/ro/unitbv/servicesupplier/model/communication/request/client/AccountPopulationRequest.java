package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class AccountPopulationRequest implements ServerRequest, Serializable {

   private long clientId;
   private double amount;

   public AccountPopulationRequest(long clientId, double amount) {
      this.clientId = clientId;
      this.amount = amount;
   }

   public long getClientId() {
      return clientId;
   }

   public double getAmount() {
      return amount;
   }

   @Override
   public int getCode() {
      return Codes.POPULATE_ACCOUNT;
   }
}
