package ro.unitbv.servicesupplier.model.communication.request.provider;

import ro.unitbv.servicesupplier.model.communication.request.common.ServicesRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class ProviderServicesRequest extends ServicesRequest implements Serializable {

   private long providerId;

   public ProviderServicesRequest(long providerId) {
      this.providerId = providerId;
   }

   public long getProviderId() {
      return providerId;
   }

   @Override
   public int getCode() {
      return Codes.GET_PROVIDER_SERVICES;
   }
}
