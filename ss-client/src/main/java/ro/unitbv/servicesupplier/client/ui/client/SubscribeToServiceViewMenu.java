package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.client.SubscribeToServiceInteractor;
import ro.unitbv.servicesupplier.client.logic.interactor.client.UpdatePaymentMethodInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.client.PaymentMethodUpdateRequest;
import ro.unitbv.servicesupplier.model.communication.request.client.SubscriptionRequest;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

/**
 * Created by Petri on 18-May-16.
 */
class SubscribeToServiceViewMenu extends OptionViewMenu implements
      SubscribeToServiceInteractor.View,
      UpdatePaymentMethodInteractor.View,
      HasBackOption {

   private AvailableServicesViewMenu previousMenu;
   private ServiceDto service;

   SubscribeToServiceViewMenu(AvailableServicesViewMenu previousMenu, ServiceDto service) {
      this.previousMenu = previousMenu;
      this.service = service;
   }

   @Override
   protected String getViewTitle() {
      return "Service " + service.getDescription();
   }

   @Override
   protected void renderView() {
      System.out.println(service.toString());
      super.renderView();
   }

   @Override
   protected int getNumOfOptions() {
      return 1;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      // option == 1;
      return "Subscribe";
   }

   @Override
   protected void doActionForOption(int option) {
      printWorkingMessage();

      SubscriptionRequest request = new SubscriptionRequest(
            LoginSession.getClient().getId(), service.getId());

      new SubscribeToServiceInteractor(this).initiate(request);
   }

   @Override
   public void onSubscriptionSuccess(long subscriptionId) {
      System.out.println(" You have successfully subscribed to this service!");

      promptForPaymentAutomation(subscriptionId);
   }

   private void promptForPaymentAutomation(long subscriptionId) {
      System.out.println();
      System.out.println(
            "\tWould you like to automate payment for this subscription?" +
                  System.lineSeparator() + System.lineSeparator() +
                  "\t1) Yes" + System.lineSeparator() +
                  "\t2) No" + System.lineSeparator());

      do {
         int choice = listenForInt("Select option: ");
         switch (choice) {
            case 1:
               printWorkingMessage();

               PaymentMethodUpdateRequest request = new PaymentMethodUpdateRequest(
                     LoginSession.getClient().getId(), subscriptionId, true);

               new UpdatePaymentMethodInteractor(this).initiate(request);
               return;
            case 2:
               goBack();
               return;
            default:
               printlnDoubleIndented("Invalid option!");
         }
      } while (true);
   }

   @Override
   public void onPaymentMethodUpdateSuccess(boolean wasAutomated) {
      System.out.println(" Successfully set AUTOMATIC as payment method for this subscription.");
      goBack();
   }

   @Override
   public void onPaymentMethodUpdateFailure(String message) {
      System.out.println(" Failed to automate payment for subscription: " + message);
      goBack();
   }

   @Override
   public void onSubscriptionFailure(String message) {
      System.out.println(" Failed to subscribe to service: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      previousMenu.proceed();
   }
}
