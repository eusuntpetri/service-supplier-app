package ro.unitbv.servicesupplier.model.communication.response.failure.client;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class DuplicateSubscriptionResponse extends SubscriptionFailureResponse implements Serializable {

   public DuplicateSubscriptionResponse() {
      super("Client already subscribed to provided service.");
   }

   @Override
   public int getCode() {
      return Codes.ALREADY_SUBSCRIBED;
   }
}
