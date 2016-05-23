package ro.unitbv.servicesupplier.client.logic.interactor.common;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.common.ServicesRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.ServicesResponse;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class GetServicesInteractor implements
      Interactor<ServicesRequest, ServicesResponse> {

   private View view;

   public GetServicesInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(ServicesRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(ServicesResponse response) {
      List<ServiceDto> services = response.getServices();

      if (services == null || services.isEmpty()) {
         view.onNoServicesFound();
         return;
      }

      view.onServiceLoadSuccess(services);
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onServiceLoadFailure(response.getMessage());
   }


   public interface View {

      void onServiceLoadSuccess(List<ServiceDto> services);

      void onNoServicesFound();

      void onServiceLoadFailure(String message);

   }
}
