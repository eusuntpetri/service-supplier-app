package ro.unitbv.servicesupplier.model.communication.response.failure.client;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class PaymentMethodAlreadySetResponse extends FailureResponse implements Serializable {
   public PaymentMethodAlreadySetResponse() {
      super("Payment method already set to requested value!");
   }

   @Override
   public int getCode() {
      return Codes.SAME_PAY_METHOD_VALUE;
   }
}
