package ro.unitbv.servicesupplier.client.logic.interactor.authentication.register;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.model.communication.request.authentication.ProviderRegisterRequest;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 16-May-16.
 */
public class ProviderRegistrationInteractor extends RegistrationInteractor<ProviderRegisterRequest> {

   public ProviderRegistrationInteractor(View view) {
      super(view);
   }

   @Override
   public void initiate(ProviderRegisterRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      view.onRegistrationSuccess();
   }
}
