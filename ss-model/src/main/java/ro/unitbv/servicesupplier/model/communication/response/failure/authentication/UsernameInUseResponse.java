package ro.unitbv.servicesupplier.model.communication.response.failure.authentication;

import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 18-May-16.
 */
public class UsernameInUseResponse extends FailureResponse implements Serializable {

   public UsernameInUseResponse() {
      super("Username already in use!");
   }

   @Override
   public int getCode() {
      return ServerResponse.Codes.USERNAME_IN_USE;
   }
}
