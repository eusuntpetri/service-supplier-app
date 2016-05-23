package ro.unitbv.servicesupplier.model.communication.response.failure.common;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class InconsistentDataResponse extends FailureResponse implements Serializable {

   public InconsistentDataResponse(String message) {
      super(message);
   }

   @Override
   public int getCode() {
      return Codes.INCONSISTENT_DATA;
   }
}
