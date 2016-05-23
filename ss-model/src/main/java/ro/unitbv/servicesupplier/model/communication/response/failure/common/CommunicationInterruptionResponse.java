package ro.unitbv.servicesupplier.model.communication.response.failure.common;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

/**
 * Created by Petri on 20-May-16.
 */
public class CommunicationInterruptionResponse extends FailureResponse {

   public CommunicationInterruptionResponse() {
      super("Communication with the server was interrupted!");
   }

   @Override
   public int getCode() {
      return 0;
   }
}
