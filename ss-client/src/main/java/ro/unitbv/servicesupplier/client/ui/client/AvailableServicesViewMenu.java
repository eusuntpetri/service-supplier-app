package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.common.GetServicesInteractor;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.common.ServicesRequest;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
class AvailableServicesViewMenu extends OptionViewMenu implements GetServicesInteractor.View, HasBackOption {

   private List<ServiceDto> services;

   @Override
   protected String getViewTitle() {
      return "Available services";
   }

   @Override
   public void proceed() {
      initialize();
   }

   private void initialize() {
      if (services != null) {
         resume();
         return;
      }

      printWorkingMessage();

      ServicesRequest request = new ServicesRequest();

      new GetServicesInteractor(this).initiate(request);
   }

   private void resume() {
      if (services.isEmpty()) {
         System.out.println(" There are no services currently available.");
         goBack();
         return;
      }

      System.out.println();
      super.proceed();
   }

   @Override
   protected int getNumOfOptions() {
      return services.size();
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      optionNum--;

      return services.get(optionNum).toString();
   }

   @Override
   protected void doActionForOption(int option) {
      option--;
      ServiceDto service = services.get(option);

      new SubscribeToServiceViewMenu(this, service).proceed();
   }

   @Override
   public void onServiceLoadSuccess(List<ServiceDto> services) {
      this.services = services;
      resume();
   }

   @Override
   public void onNoServicesFound() {
      this.services = Collections.emptyList();
      resume();
   }

   @Override
   public void onServiceLoadFailure(String message) {
      System.out.println(" Service load failure: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      new ClientMainViewMenu().proceed();
   }
}
