package ro.unitbv.servicesupplier.model.communication.request;

/**
 * Created by Petri on 15-May-16.
 */
public interface ServerRequest {

   int getCode();

   interface Codes {

      int REGISTER_CLIENT = 1;
      int REGISTER_PROVIDER = 2;
      int LOGIN = 3;

      int GET_ACCOUNT = 10;
      int GET_SERVICES = 11;
      int GET_NOTICES = 12;

      int GET_CLIENT_SUBSCRIPTS = 100;
      int GET_CLIENT_BILLS = 101;
      int POPULATE_ACCOUNT = 102;
      int BILL_PAYMENT = 103;
      int SUBSCRIBE = 104;
      int SWITCH_PAY_METHOD = 105;
      int CANCEL_SUBSCRIPTION = 106;

      int GET_PROVIDER_SERVICES = 200;
      int OFFER_SERVICE = 201;
      int GET_SERVICE_SUBSCRIPTIONS = 202;

   }

}
