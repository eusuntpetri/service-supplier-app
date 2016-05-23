package ro.unitbv.servicesupplier.client.logic.interactor.base;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 16-May-16.
 */
public interface Interactor<T extends ServerRequest, E extends SuccessResponse>
      extends RequestCallback<E> {

   void initiate(T request);

}
