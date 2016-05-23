package ro.unitbv.servicesupplier.server;

import ro.unitbv.servicesupplier.model.constants.ServerConstants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * Created by Petri on 22-May-16.
 */
class RequestSocketListener implements Runnable {

   private ServerSocket requestSocket;

   RequestSocketListener(ServerSocket requestSocket) {
      this.requestSocket = requestSocket;
   }

   @Override
   public void run() {

      while (!Thread.currentThread().isInterrupted()) {
         try {
            ServiceSupplierServer.getInstance().onClientConnected(requestSocket.accept());

         } catch (SocketException e) {
            if (!Thread.currentThread().isInterrupted()) {
               throw new RuntimeException(e);
            }
         } catch (IOException e) {
            System.out.println("Exception while trying to listen on port "
                  + ServerConstants.PORT + " or listening for a connection:");
            System.out.println(e.getMessage());
         }
      }
   }
}
