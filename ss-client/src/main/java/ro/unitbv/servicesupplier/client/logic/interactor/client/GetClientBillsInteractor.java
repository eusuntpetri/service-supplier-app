package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.client.ClientBillsRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.client.ClientBillsResponse;
import ro.unitbv.servicesupplier.model.dto.BillDto;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class GetClientBillsInteractor implements
      Interactor<ClientBillsRequest, ClientBillsResponse> {

   private View view;

   public GetClientBillsInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(ClientBillsRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(ClientBillsResponse response) {
      List<BillDto> bills = response.getBills();

      if (bills == null || bills.isEmpty()) {
         view.onNoBillsFound();
         return;
      }

      view.onBillsLoadSuccess(bills);
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onBillsLoadFailure(response.getMessage());
   }


   public interface View {

      void onBillsLoadSuccess(List<BillDto> bills);

      void onNoBillsFound();

      void onBillsLoadFailure(String message);
   }
}
