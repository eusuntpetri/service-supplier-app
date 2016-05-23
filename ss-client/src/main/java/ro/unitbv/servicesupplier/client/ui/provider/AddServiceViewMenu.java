package ro.unitbv.servicesupplier.client.ui.provider;

import ro.unitbv.servicesupplier.client.logic.interactor.provider.OfferServiceInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.ViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.provider.OfferServiceRequest;
import ro.unitbv.servicesupplier.model.dto.ServiceDto;

/**
 * Created by Petri on 17-May-16.
 */
class AddServiceViewMenu extends ViewMenu implements OfferServiceInteractor.View {

   @Override
   protected String getViewTitle() {
      return "Adding a service";
   }

   @Override
   public void proceed() {
      renderView();

      String description = listenForString("Service description: ");
      double payment = listenForDouble("    Service payment: ");
      int paymentPeriod = listenForInt("     Payment period: ");

      printWorkingMessage();

      OfferServiceRequest offerServiceRequest =
            new OfferServiceRequest(LoginSession.getProvider().getId(),
                  new ServiceDto.Builder()
                        .withDescription(description)
                        .costing(payment)
                        .per(paymentPeriod)
                        .build());

      new OfferServiceInteractor(this).initiate(offerServiceRequest);
   }

   @Override
   protected void renderView() {
      printMenuHeader();
   }

   @Override
   public void onCreateServiceSuccess() {
      System.out.println(" Service created successfully!");
      new ProviderMainViewMenu().proceed();
   }

   @Override
   public void onCreateServiceFailure(String message) {
      System.out.printf(" Failed to create new service: " + message);
      new ProviderMainViewMenu().proceed();
   }
}
