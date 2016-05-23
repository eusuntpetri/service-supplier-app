package ro.unitbv.servicesupplier.model.communication.response.success.common;

import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

import java.io.Serializable;

/**
 * Created by Petri on 20-May-16.
 */
public class IdResponse extends SuccessResponse implements Serializable {

   private long id;

   public IdResponse(String message, long id) {
      super(message);
      this.id = id;
   }

   public long getId() {
      return id;
   }
}
