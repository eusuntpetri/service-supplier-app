package ro.unitbv.servicesupplier.model.communication.response.failure.client;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscriptionCancelFailureResponse extends FailureResponse implements Serializable {

   public SubscriptionCancelFailureResponse(String message) {
      super(message);
   }

   @Override
   public int getCode() {
      return Codes.SUBSCRIPT_CANCEL_FAIL;
   }
}
