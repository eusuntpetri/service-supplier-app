package ro.unitbv.servicesupplier.model.communication.response.failure.common;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;

import java.io.Serializable;

/**
 * Created by Petri on 16-May-16.
 */
public class InvalidDataResponse extends FailureResponse implements Serializable {

   public InvalidDataResponse(String fieldName) {
      super("Invalid data provided in field [" + fieldName + "]!");
   }

   @Override
   public int getCode() {
      return Codes.INVALID_DATA;
   }
}
