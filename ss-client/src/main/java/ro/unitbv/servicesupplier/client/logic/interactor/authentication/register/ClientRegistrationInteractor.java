package ro.unitbv.servicesupplier.client.logic.interactor.authentication.register;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.model.communication.request.authentication.ClientRegisterRequest;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 16-May-16.
 */
public class ClientRegistrationInteractor extends RegistrationInteractor<ClientRegisterRequest> {

   public ClientRegistrationInteractor(View view) {
      super(view);
   }

   @Override
   public void initiate(ClientRegisterRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      view.onRegistrationSuccess();
   }

}
