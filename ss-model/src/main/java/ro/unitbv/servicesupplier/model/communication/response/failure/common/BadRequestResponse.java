package ro.unitbv.servicesupplier.model.communication.response.failure.common;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 16-May-16.
 */
public class BadRequestResponse extends FailureResponse implements Serializable {
   public BadRequestResponse() {
      super("Invalid or unrecognized operation requested.");
   }

   @Override
   public int getCode() {
      return Codes.BAD_REQUEST;
   }
}
