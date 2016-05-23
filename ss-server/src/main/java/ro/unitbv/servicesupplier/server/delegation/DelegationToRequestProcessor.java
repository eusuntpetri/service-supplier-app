package ro.unitbv.servicesupplier.server.delegation;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.communication.request.ServerRequest.Codes;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.BadRequestResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.CommunicationInterruptionResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.processing.authentication.login.LoginProcessor;
import ro.unitbv.servicesupplier.server.processing.authentication.register.ClientRegistrationProcessor;
import ro.unitbv.servicesupplier.server.processing.authentication.register.ProviderRegistrationProcessor;
import ro.unitbv.servicesupplier.server.processing.client.*;
import ro.unitbv.servicesupplier.server.processing.common.AccountRequestProcessor;
import ro.unitbv.servicesupplier.server.processing.common.NoticesRequestProcessor;
import ro.unitbv.servicesupplier.server.processing.common.ServicesRequestProcessor;
import ro.unitbv.servicesupplier.server.processing.provider.OfferServiceProcessor;
import ro.unitbv.servicesupplier.server.processing.provider.ProviderServicesRequestProcessor;
import ro.unitbv.servicesupplier.server.processing.provider.ServiceSubscriptionsRequestProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri on 15-May-16.
 */
public class DelegationToRequestProcessor implements Runnable {

   private static final Map<Integer, RequestProcessor> requestProcessors;

   static {
      requestProcessors = new HashMap<Integer, RequestProcessor>() {{

         put(Codes.REGISTER_CLIENT,
               new ClientRegistrationProcessor());
         put(Codes.REGISTER_PROVIDER,
               new ProviderRegistrationProcessor());
         put(Codes.LOGIN,
               new LoginProcessor());
         put(Codes.GET_ACCOUNT,
               new AccountRequestProcessor());
         put(Codes.GET_SERVICES,
               new ServicesRequestProcessor());
         put(Codes.GET_NOTICES,
               new NoticesRequestProcessor());
         put(Codes.GET_CLIENT_SUBSCRIPTS,
               new ClientSubscriptionsRequestProcessor());
         put(Codes.GET_CLIENT_BILLS,
               new ClientBillsRequestProcessor());
         put(Codes.POPULATE_ACCOUNT,
               new AccountPopulationProcessor());
         put(Codes.BILL_PAYMENT,
               new BillPaymentProcessor());
         put(Codes.SUBSCRIBE,
               new SubscriptionProcessor());
         put(Codes.SWITCH_PAY_METHOD,
               new PaymentMethodUpdateProcessor());
         put(Codes.CANCEL_SUBSCRIPTION,
               new SubscriptionCancelProcessor());
         put(Codes.GET_PROVIDER_SERVICES,
               new ProviderServicesRequestProcessor());
         put(Codes.OFFER_SERVICE,
               new OfferServiceProcessor());
         put(Codes.GET_SERVICE_SUBSCRIPTIONS,
               new ServiceSubscriptionsRequestProcessor());
      }};
   }

   private Socket clientSocket;

   public DelegationToRequestProcessor(Socket clientSocket) {
      this.clientSocket = clientSocket;
   }

   @Override
   public void run() {
      ObjectOutputStream out;

      try {
         out = new ObjectOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
         e.printStackTrace();
         return;
      }

      try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

         ServerRequest clientRequest = (ServerRequest) in.readObject();

         RequestProcessor requestProcessor = requestProcessors.get(clientRequest.getCode());

         if (requestProcessor == null) {
            sendBadRequestResponse(out);
            return;
         }

         @SuppressWarnings("unchecked")
         ServerResponse response = requestProcessor.process(clientRequest);

         respond(out, response);

      } catch (IOException e) {
         e.printStackTrace();
         sendInterruptionResponse(out);
      } catch (ClassNotFoundException | ClassCastException e) {
         System.err.println(e.getMessage());
         sendBadRequestResponse(out);
      } finally {
         close(out);
         close(clientSocket);
      }
   }

   private void sendBadRequestResponse(ObjectOutputStream out) {
      respond(out, new BadRequestResponse());
   }

   private void sendInterruptionResponse(ObjectOutputStream out) {
      respond(out, new CommunicationInterruptionResponse());
   }

   private void respond(ObjectOutputStream out, ServerResponse response) {
      try {
         out.writeObject(response);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void close(Closeable thing) {
      if (thing == null) return;
      try {
         thing.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
