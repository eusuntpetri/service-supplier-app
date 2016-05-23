package ro.unitbv.servicesupplier.client.logic.interactor.authentication.login;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.model.communication.request.authentication.LoginRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.account.AccountResponse;
import ro.unitbv.servicesupplier.model.dto.AccountDto;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

/**
 * Created by Petri on 16-May-16.
 */
public class LoginInteractor implements Interactor<LoginRequest, AccountResponse> {

   private View view;

   public LoginInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(LoginRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(AccountResponse accountResponse) {
      AccountDto account = accountResponse.getAccountDetails();

      if (account instanceof ClientAccountDto) {
         LoginSession.begin((ClientAccountDto) account);

         view.onSuccessfulClientLogin();
         return;
      }

      LoginSession.begin((ProviderAccountDto) account);

      view.onSuccessfulProviderLogin();
   }

   @Override
   public void onFailure(FailureResponse response) {

      if (response.getCode() == ServerResponse.Codes.INVALID_LOGIN) {
         view.onLoginFailure("Invalid login credentials.");
         return;
      }

      view.onLoginFailure(response.getMessage());
   }

   public interface View {

      void onSuccessfulClientLogin();

      void onSuccessfulProviderLogin();

      void onLoginFailure(String message);
   }
}
