package ro.unitbv.servicesupplier.server.processing;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;

/**
 * Created by Petri on 16-May-16.
 */
public interface RequestProcessor<T extends ServerRequest> {

   ServerResponse process(T request);

}
