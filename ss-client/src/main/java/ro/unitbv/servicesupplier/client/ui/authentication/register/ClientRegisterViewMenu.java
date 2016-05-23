package ro.unitbv.servicesupplier.client.ui.authentication.register;

import ro.unitbv.servicesupplier.client.logic.interactor.authentication.register.ClientRegistrationInteractor;
import ro.unitbv.servicesupplier.client.ui.authentication.AuthenticationViewMenu;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.authentication.ClientRegisterRequest;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;

/**
 * Created by Petri on 15-May-16.
 */
class ClientRegisterViewMenu extends AuthenticationViewMenu implements ClientRegistrationInteractor.View {

   @Override
   protected String getViewTitle() {
      return "Registering New Client";
   }

   @Override
   public void proceed() {
      renderView();

      String username = listenForNewUsername();
      String password = listenForNewPassword();
      String firstName = listenForString(" Your first name: ");
      String lastName = listenForString("  Your last name: ");

      ClientRegisterRequest registerRequest =
            new ClientRegisterRequest(
                  new ClientAccountDto.Builder()
                        .withUsername(username)
                        .withPassword(password)
                        .withFirstName(firstName)
                        .withLastName(lastName)
                        .build());

      printWorkingMessage();

      new ClientRegistrationInteractor(this).initiate(registerRequest);
   }

   @Override
   protected void renderView() {
      printMenuHeader();
   }

   @Override
   public void onRegistrationSuccess() {
      System.out.println(" Registration successful! You may now proceed to authentication.");
      new MainViewMenu().proceed();
   }

   @Override
   public void onRegistrationFailure(String message) {
      System.out.println(" Registration failed: " + message);
      new MainViewMenu().proceed();
   }
}
