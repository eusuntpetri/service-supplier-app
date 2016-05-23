package ro.unitbv.servicesupplier.server;

import ro.unitbv.servicesupplier.model.constants.ServerConstants;
import ro.unitbv.servicesupplier.server.delegation.DelegationToRequestProcessor;
import ro.unitbv.servicesupplier.server.techno.system.TechnoSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Petri on 15-May-16.
 */
public final class ServiceSupplierServer {

   private static final ServiceSupplierServer SERVER_INSTANCE = new ServiceSupplierServer();

   private final ExecutorService REQUEST_SOCKET_LISTENER_EXECUTOR;
   private final ExecutorService REQUEST_PROCESSOR_DELEGATION_EXECUTOR;
   private final ExecutorService TECHNO_SYSTEM_EXECUTOR;

   private ServerSocket requestSocket;
   private boolean isRunning = false;

   private ServiceSupplierServer() {
      // Singleton.
      REQUEST_SOCKET_LISTENER_EXECUTOR = Executors.newSingleThreadExecutor();
      REQUEST_PROCESSOR_DELEGATION_EXECUTOR = Executors.newCachedThreadPool();
      TECHNO_SYSTEM_EXECUTOR = Executors.newSingleThreadExecutor();
   }

   public static ServiceSupplierServer getInstance() {
      return SERVER_INSTANCE;
   }

   public void startServer() {
      if (isRunning) return;

      try {
         requestSocket = new ServerSocket(ServerConstants.PORT);
      } catch (IOException e) {
         System.out.println("Error starting server on port " + ServerConstants.PORT + ":");
         e.printStackTrace();
         return;
      }

      TECHNO_SYSTEM_EXECUTOR.execute(TechnoSystem.getInstance());
      REQUEST_SOCKET_LISTENER_EXECUTOR.execute(new RequestSocketListener(requestSocket));

      this.isRunning = true;
   }

   void onClientConnected(Socket clientSocket) {
      REQUEST_PROCESSOR_DELEGATION_EXECUTOR.execute(new DelegationToRequestProcessor(clientSocket));
   }

   public void shutdownServer() {
      if (!isRunning) return;

      shutdown(REQUEST_PROCESSOR_DELEGATION_EXECUTOR);
      TECHNO_SYSTEM_EXECUTOR.shutdownNow();
      REQUEST_SOCKET_LISTENER_EXECUTOR.shutdownNow();
      close(requestSocket);

      this.isRunning = false;
   }

   private void shutdown(ExecutorService executor) {
      executor.shutdown();

      try {
         if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
            executor.shutdownNow();
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   private void close(ServerSocket socket) {
      try {
         socket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) throws IOException {
      getInstance().startServer();

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Type \"terminate\" to shutdown the server.");
      while (!in.readLine().equalsIgnoreCase("terminate")) {
         System.out.println("Unrecognized command.");
      }

      getInstance().shutdownServer();
   }

}
