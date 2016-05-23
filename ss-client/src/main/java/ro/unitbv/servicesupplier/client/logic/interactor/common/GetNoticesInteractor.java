package ro.unitbv.servicesupplier.client.logic.interactor.common;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.common.NoticesRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.NoticesResponse;
import ro.unitbv.servicesupplier.model.dto.NoticeDto;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class GetNoticesInteractor implements
      Interactor<NoticesRequest, NoticesResponse> {

   private View view;

   public GetNoticesInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(NoticesRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(NoticesResponse response) {
      List<NoticeDto> notices = response.getNotices();

      if (notices == null || notices.isEmpty()) {
         view.onNoNoticesFound();
         return;
      }
      view.onNoticesLoadSuccess(response.getNotices());
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onNoticesLoadFailure(response.getMessage());
   }


   public interface View {

      void onNoticesLoadSuccess(List<NoticeDto> notices);

      void onNoNoticesFound();

      void onNoticesLoadFailure(String message);

   }
}
