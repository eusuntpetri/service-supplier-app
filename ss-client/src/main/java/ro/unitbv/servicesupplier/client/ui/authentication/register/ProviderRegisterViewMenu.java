package ro.unitbv.servicesupplier.client.ui.authentication.register;

import ro.unitbv.servicesupplier.client.logic.interactor.authentication.register.ProviderRegistrationInteractor;
import ro.unitbv.servicesupplier.client.logic.interactor.authentication.register.RegistrationInteractor;
import ro.unitbv.servicesupplier.client.ui.authentication.AuthenticationViewMenu;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.authentication.ProviderRegisterRequest;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

/**
 * Created by Petri on 15-May-16.
 */
class ProviderRegisterViewMenu extends AuthenticationViewMenu implements RegistrationInteractor.View {

   @Override
   protected String getViewTitle() {
      return "Registering New Provider";
   }

   @Override
   public void proceed() {
      renderView();

      String username = listenForNewUsername();
      String password = listenForNewPassword();
      String name = listenForString("Enter your company name: ");

      printWorkingMessage();

      ProviderRegisterRequest registerRequest =
            new ProviderRegisterRequest(
                  new ProviderAccountDto.Builder()
                        .withUsername(username)
                        .withPassword(password)
                        .withName(name)
                        .build());

      new ProviderRegistrationInteractor(this).initiate(registerRequest);
   }

   @Override
   protected void renderView() {
      printMenuHeader();
   }

   @Override
   public void onRegistrationSuccess() {
      System.out.println(" Registration successful! You may proceed to authentication.");
      new MainViewMenu().proceed();
   }

   @Override
   public void onRegistrationFailure(String message) {
      System.out.println(" Registration failed: " + message);
      new MainViewMenu().proceed();
   }
}
