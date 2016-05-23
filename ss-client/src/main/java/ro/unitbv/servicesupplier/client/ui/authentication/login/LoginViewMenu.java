package ro.unitbv.servicesupplier.client.ui.authentication.login;

import ro.unitbv.servicesupplier.client.logic.interactor.authentication.login.LoginInteractor;
import ro.unitbv.servicesupplier.client.ui.authentication.AuthenticationViewMenu;
import ro.unitbv.servicesupplier.client.ui.client.ClientMainViewMenu;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.client.ui.provider.ProviderMainViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.authentication.LoginRequest;
import ro.unitbv.servicesupplier.model.dto.AccountCredentialDto;

/**
 * Created by Petri on 12-May-16.
 */
public class LoginViewMenu extends AuthenticationViewMenu implements LoginInteractor.View {

   @Override
   protected String getViewTitle() {
      return "Logging In";
   }

   @Override
   public void proceed() {
      renderView();

      String username = listenForUsername();
      String password = listenForPassword();

      printWorkingMessage();

      LoginRequest request = new LoginRequest(
            new AccountCredentialDto()
                  .setUsername(username)
                  .setPassword(password));

      new LoginInteractor(this).initiate(request);
   }

   @Override
   protected void renderView() {
      printMenuHeader();
   }

   @Override
   public void onSuccessfulClientLogin() {
      System.out.println(" Successful client authentication!");
      new ClientMainViewMenu().proceed();
   }

   @Override
   public void onSuccessfulProviderLogin() {
      System.out.println(" Successful provider authentication!");
      new ProviderMainViewMenu().proceed();
   }

   @Override
   public void onLoginFailure(String message) {
      System.out.println(" Login failed: " + message);
      new MainViewMenu().proceed();
   }
}
