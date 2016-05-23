package ro.unitbv.servicesupplier.model.communication.response.success;

import ro.unitbv.servicesupplier.model.communication.response.base.BasicResponse;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class SuccessResponse extends BasicResponse implements Serializable {

   public SuccessResponse(String message) {
      super(true, message);
   }

   @Override
   public int getCode() {
      return Codes.OK;
   }
}
