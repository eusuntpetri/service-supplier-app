package ro.unitbv.servicesupplier.model.communication.response.base;

import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public abstract class BasicResponse implements ServerResponse, Serializable {

   private boolean successful;
   private String message;

   public BasicResponse(boolean successful, String message) {
      this.successful = successful;
      this.message = message;
   }

   @Override
   public boolean isSuccessful() {
      return successful;
   }

   public String getMessage() {
      return message;
   }

}
