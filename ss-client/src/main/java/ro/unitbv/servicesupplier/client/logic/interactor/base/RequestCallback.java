package ro.unitbv.servicesupplier.client.logic.interactor.base;

import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 22-May-16.
 */
public interface RequestCallback<T extends SuccessResponse> {

   void onSuccess(T response);

   void onFailure(FailureResponse response);

}
