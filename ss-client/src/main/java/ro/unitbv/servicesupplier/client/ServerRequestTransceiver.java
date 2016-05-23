package ro.unitbv.servicesupplier.client;

import ro.unitbv.servicesupplier.client.logic.interactor.base.RequestCallback;
import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.constants.ServerConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Petri on 16-May-16.
 */
class ServerRequestTransceiver<T extends SuccessResponse> implements Runnable {

   private ServerRequest request;
   private RequestCallback<T> callback;

   ServerRequestTransceiver(ServerRequest request, RequestCallback<T> callback) {
      this.request = request;
      this.callback = callback;
   }

   @Override
   @SuppressWarnings("unchecked")
   public void run() {
      try (Socket socket = new Socket(ServerConstants.ADDRESS, ServerConstants.PORT);
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

         out.writeObject(request);

         ServerResponse response = (ServerResponse) in.readObject();

         if (response.isSuccessful()) {
            callback.onSuccess((T) response);
         } else {
            callback.onFailure((FailureResponse) response);
         }
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
      }
   }
}
