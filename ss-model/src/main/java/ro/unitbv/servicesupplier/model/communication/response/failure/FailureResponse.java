package ro.unitbv.servicesupplier.model.communication.response.failure;

import ro.unitbv.servicesupplier.model.communication.response.base.BasicResponse;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public abstract class FailureResponse extends BasicResponse implements Serializable {

   public FailureResponse(String message) {
      super(false, message);
   }
}
