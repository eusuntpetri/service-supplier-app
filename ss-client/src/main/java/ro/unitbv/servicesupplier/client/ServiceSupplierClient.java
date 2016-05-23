package ro.unitbv.servicesupplier.client;

import ro.unitbv.servicesupplier.client.logic.interactor.base.RequestCallback;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Petri on 15-May-16.
 */
public final class ServiceSupplierClient implements Runnable {

   private static final ExecutorService CLIENT_EXECUTOR = Executors.newSingleThreadExecutor();
   private static final ExecutorService SERVER_REQUEST_EXECUTOR = Executors.newCachedThreadPool();

   private static final ServiceSupplierClient CLIENT_INSTANCE = new ServiceSupplierClient();

   private boolean isRunning = false;

   private ServiceSupplierClient() {
      // Singleton.
   }

   public static ServiceSupplierClient getInstance() {
      return CLIENT_INSTANCE;
   }

   public void startClient() {
      if (isRunning) return;

      CLIENT_EXECUTOR.execute(CLIENT_INSTANCE);
   }

   @Override
   public void run() {
      isRunning = true;

      new MainViewMenu().proceed();
   }

   public <T extends SuccessResponse> void sendRequest(ServerRequest request,
                                                       RequestCallback<T> callback) {

      SERVER_REQUEST_EXECUTOR.execute(new ServerRequestTransceiver<>(request, callback));
   }

   public void shutdownClient() {
      if (!isRunning) return;

      SERVER_REQUEST_EXECUTOR.shutdown();
      try {
         if (!SERVER_REQUEST_EXECUTOR.awaitTermination(3, TimeUnit.SECONDS)) {
            SERVER_REQUEST_EXECUTOR.shutdownNow();
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      CLIENT_EXECUTOR.shutdownNow();
      isRunning = false;
   }

   public static void main(String[] args) {
      getInstance().startClient();
   }
}
