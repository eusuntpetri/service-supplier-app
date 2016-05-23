package ro.unitbv.servicesupplier.model.communication.response;

/**
 * Created by Petri on 15-May-16.
 */
public interface ServerResponse {

   boolean isSuccessful();

   int getCode();

   interface Codes {

      int OK = 1;
      int BAD_REQUEST = 2;
      int INTERRUPTED = 3;
      int USERNAME_IN_USE = 4;
      int INVALID_DATA = 5;
      int INVALID_LOGIN = 6;
      int INCONSISTENT_DATA = 7;
      int BALANCE_LOW = 8;
      int SUBSCRIPT_CREATE_FAIL = 9;
      int ALREADY_SUBSCRIBED = 10;
      int SUBSCRIPT_CANCEL_FAIL = 11;
      int SAME_PAY_METHOD_VALUE = 12;

   }

}
