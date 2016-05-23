package ro.unitbv.servicesupplier.client.ui.provider;

import ro.unitbv.servicesupplier.client.logic.interactor.provider.GetServiceSubscriptionsInteractor;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.provider.ServiceSubscriptionsRequest;
import ro.unitbv.servicesupplier.model.dto.BillDto;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
class ServiceSubscriptionsViewMenu extends OptionViewMenu implements GetServiceSubscriptionsInteractor.View, HasBackOption {

   private ProviderServicesViewMenu previousMenu;
   private ServiceDto serviceDetails;
   private List<SubscriptionDto> serviceSubscriptions;

   ServiceSubscriptionsViewMenu(ProviderServicesViewMenu previousMenu, ServiceDto serviceDetails) {
      this.previousMenu = previousMenu;
      this.serviceDetails = serviceDetails;
   }

   @Override
   protected String getViewTitle() {
      return "Subscriptions to service " + serviceDetails.getDescription();
   }

   @Override
   public void proceed() {
      initialize();
   }

   private void initialize() {
      if (serviceSubscriptions != null) {
         resume();
         return;
      }

      printWorkingMessage();

      ServiceSubscriptionsRequest request =
            new ServiceSubscriptionsRequest(serviceDetails.getId());

      new GetServiceSubscriptionsInteractor(this).initiate(request);
   }

   private void resume() {
      if (serviceSubscriptions.isEmpty()) {
         System.out.print(" There are no subscriptions to this service yet.");
         goBack();
         return;
      }

      System.out.println();
      super.proceed();
   }

   @Override
   protected int getNumOfOptions() {
      return serviceSubscriptions.size();
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      optionNum--;

      SubscriptionDto subscriptionDetails = serviceSubscriptions.get(optionNum);
      ClientAccountDto clientDetails = subscriptionDetails.getSubscriberDetails();
      List<BillDto> bills = subscriptionDetails.getIssuedBills();

      final int[] unpaidBills = {0};
      final double[] paymentDue = {0};
      final double[] paymentOverdue = {0};
      bills.stream().filter(bill -> !bill.isPaid()).forEach(bill -> {
         unpaidBills[0]++;
         paymentDue[0] += bill.getPaymentDue();
         paymentOverdue[0] += bill.getPaymentOverdue();
      });

      return System.lineSeparator() +
            "\t\tClient: " + clientDetails.getFirstName() + " " + clientDetails.getLastName() +
            System.lineSeparator() + System.lineSeparator() +
            "\t\tSubscribed since: " + subscriptionDetails.getActivationDate() + System.lineSeparator() +
            System.lineSeparator() +
            "\t\t    Last payment: " + subscriptionDetails.getPeriodSincePayment() + " days ago (" +
            "payment: $" + serviceDetails.getPayment() + " per " + serviceDetails.getPaymentPeriod() + " days)" +
            System.lineSeparator() +
            "\t\t     Payment due: $" + paymentDue[0] +
            (unpaidBills[0] > 0 ? " (" + unpaidBills[0] + " unpaid bills)" : "") + System.lineSeparator() +
            (paymentOverdue[0] > 0 ? "\t\t Payment overdue: $" + paymentOverdue[0] : "") + System.lineSeparator();
   }

   @Override
   protected void doActionForOption(int option) {
      printlnDoubleIndented("No action available.");
      listenForOption();
   }

   @Override
   public void onSubscriptionsLoadSuccess(List<SubscriptionDto> subscriptions) {
      this.serviceSubscriptions = subscriptions;
      resume();
   }

   @Override
   public void onNoSubscriptionsFound() {
      this.serviceSubscriptions = Collections.emptyList();
      resume();
   }

   @Override
   public void onSubscriptionsLoadFailure(String message) {
      System.out.println(" Failed to retrieve subscriptions: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      previousMenu.proceed();
   }
}
