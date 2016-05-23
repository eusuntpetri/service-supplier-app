package ro.unitbv.servicesupplier.model.communication.request.client;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class BillPaymentRequest implements ServerRequest, Serializable {

   private long clientId;
   private long billId;

   public BillPaymentRequest(long clientId, long billId) {
      this.clientId = clientId;
      this.billId = billId;
   }

   public long getClientId() {
      return clientId;
   }

   public long getBillId() {
      return billId;
   }

   @Override
   public int getCode() {
      return Codes.BILL_PAYMENT;
   }
}
