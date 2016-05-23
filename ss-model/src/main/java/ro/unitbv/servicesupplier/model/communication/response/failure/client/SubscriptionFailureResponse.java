package ro.unitbv.servicesupplier.model.communication.response.failure.client;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscriptionFailureResponse extends FailureResponse implements Serializable {

   public SubscriptionFailureResponse(String message) {
      super(message);
   }

   @Override
   public int getCode() {
      return Codes.SUBSCRIPT_CREATE_FAIL;
   }
}
