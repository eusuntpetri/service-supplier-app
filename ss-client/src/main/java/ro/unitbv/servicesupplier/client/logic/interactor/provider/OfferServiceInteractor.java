package ro.unitbv.servicesupplier.client.logic.interactor.provider;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.provider.OfferServiceRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 17-May-16.
 */
public class OfferServiceInteractor implements
      Interactor<OfferServiceRequest, SuccessResponse> {

   private View view;

   public OfferServiceInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(OfferServiceRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      view.onCreateServiceSuccess();
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onCreateServiceFailure(response.getMessage());
   }


   public interface View {

      void onCreateServiceSuccess();

      void onCreateServiceFailure(String message);

   }
}
