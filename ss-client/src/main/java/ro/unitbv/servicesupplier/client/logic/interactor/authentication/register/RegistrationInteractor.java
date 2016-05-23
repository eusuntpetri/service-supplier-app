package ro.unitbv.servicesupplier.client.logic.interactor.authentication.register;

import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 16-May-16.
 */
public abstract class RegistrationInteractor<T extends ServerRequest> implements
      Interactor<T, SuccessResponse> {

   protected View view;

   RegistrationInteractor(View view) {
      this.view = view;
   }

   @Override
   public void onFailure(FailureResponse response) {

      if (response.getCode() == ServerResponse.Codes.USERNAME_IN_USE) {
         view.onRegistrationFailure("The username you have chosen is already in use.");
         return;
      }

      view.onRegistrationFailure(response.getMessage());
   }


   public interface View {

      void onRegistrationSuccess();

      void onRegistrationFailure(String message);

   }
}
