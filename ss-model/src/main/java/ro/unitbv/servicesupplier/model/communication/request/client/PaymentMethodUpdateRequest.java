package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class PaymentMethodUpdateRequest implements ServerRequest, Serializable {

   private long clientId;
   private long subscriptionId;
   private boolean solicitingAutomation;

   public PaymentMethodUpdateRequest(long clientId, long subscriptionId, boolean automaticPayment) {
      this.clientId = clientId;
      this.subscriptionId = subscriptionId;
      this.solicitingAutomation = automaticPayment;
   }

   public long getClientId() {
      return clientId;
   }

   public long getSubscriptionId() {
      return subscriptionId;
   }

   public boolean isSolicitingAutomation() {
      return solicitingAutomation;
   }

   @Override
   public int getCode() {
      return Codes.SWITCH_PAY_METHOD;
   }
}
