package ro.unitbv.servicesupplier.client.ui.provider;

import ro.unitbv.servicesupplier.client.logic.interactor.common.GetServicesInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.provider.ProviderServicesRequest;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
class ProviderServicesViewMenu extends OptionViewMenu implements GetServicesInteractor.View, HasBackOption {

   private List<ServiceDto> providerServices;

   @Override
   protected String getViewTitle() {
      return "Your services";
   }

   @Override
   public void proceed() {
      initialize();
   }

   private void initialize() {
      if (providerServices != null) {
         resume();
         return;
      }

      printWorkingMessage();

      ProviderServicesRequest request =
            new ProviderServicesRequest(LoginSession.getProvider().getId());

      new GetServicesInteractor(this).initiate(request);
   }

   private void resume() {
      if (providerServices.isEmpty()) {
         System.out.println(" You have no active services!");
         goBack();
         return;
      }

      System.out.println();
      super.proceed();
   }

   @Override
   protected int getNumOfOptions() {
      return providerServices.size();
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      optionNum--;
      ServiceDto serviceDetails = providerServices.get(optionNum);

      return System.lineSeparator() +
            "\t\t" + serviceDetails.getDescription() + System.lineSeparator() +
            System.lineSeparator() +
            "\t\tPayment: $" + serviceDetails.getPayment() +
            " per " + serviceDetails.getPaymentPeriod() + " days" +
            System.lineSeparator();
   }

   @Override
   protected void doActionForOption(int option) {
      option--;
      new ServiceSubscriptionsViewMenu(this, providerServices.get(option)).proceed();
   }

   @Override
   public void onServiceLoadSuccess(List<ServiceDto> providerServices) {
      this.providerServices = providerServices;
      resume();
   }

   @Override
   public void onNoServicesFound() {
      this.providerServices = Collections.emptyList();
      resume();
   }

   @Override
   public void onServiceLoadFailure(String message) {
      System.out.println(" Failed to retrieve services: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      new ProviderMainViewMenu().proceed();
   }
}
