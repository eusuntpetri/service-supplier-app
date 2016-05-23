package ro.unitbv.servicesupplier.model.communication.response.failure.authentication;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

/**
 * Created by Petri on 16-May-16.
 */
public class InvalidLoginResponse extends FailureResponse {

   public InvalidLoginResponse() {
      super("Invalid authentication credentials!");
   }

   @Override
   public int getCode() {
      return Codes.INVALID_LOGIN;
   }
}
