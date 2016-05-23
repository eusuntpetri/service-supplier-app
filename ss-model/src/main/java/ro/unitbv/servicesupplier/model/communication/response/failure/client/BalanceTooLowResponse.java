package ro.unitbv.servicesupplier.model.communication.response.failure.client;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

/**
 * Created by Petri on 17-May-16.
 */
public class BalanceTooLowResponse extends FailureResponse {

   public BalanceTooLowResponse() {
      super("Balance too low for requested operation.");
   }

   @Override
   public int getCode() {
      return Codes.BALANCE_LOW;
   }
}
