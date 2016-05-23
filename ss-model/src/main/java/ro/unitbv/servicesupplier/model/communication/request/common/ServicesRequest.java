package ro.unitbv.servicesupplier.model.communication.request.common;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class ServicesRequest implements ServerRequest, Serializable {

   @Override
   public int getCode() {
      return Codes.GET_SERVICES;
   }
}
