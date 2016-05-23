package ro.unitbv.servicesupplier.server.processing.authentication.register;

import org.apache.commons.lang3.StringUtils;
import ro.unitbv.servicesupplier.model.communication.request.authentication.ClientRegisterRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.authentication.UsernameInUseResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.AccountCredentialDto;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.server.encryption.EncryptUtils;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;

/**
 * Created by Petri on 16-May-16.
 */
public class ClientRegistrationProcessor implements RequestProcessor<ClientRegisterRequest> {

   @Override
   public ServerResponse process(ClientRegisterRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      ClientAccountDto clientDetails = request.getAccountDetails();
      AccountCredentialDto credentials = clientDetails.getCredentials();

      if (StringUtils.isEmpty(credentials.getUsername())) {
         return new InvalidDataResponse("username");
      } else if (StringUtils.isEmpty(credentials.getPassword())) {
         return new InvalidDataResponse("password");
      } else if (StringUtils.isEmpty(clientDetails.getFirstName())) {
         return new InvalidDataResponse("first name");
      } else if (StringUtils.isEmpty(clientDetails.getLastName())) {
         return new InvalidDataResponse("last name");
      }

      if (dm.findAccountByUsername(credentials.getUsername()) != null) {
         return new UsernameInUseResponse();
      }

      clientDetails.getCredentials().setPassword(
            EncryptUtils.saltAndPepper(clientDetails.getCredentials().getPassword()));

      ClientAccount persistedAccount = new ClientAccount(clientDetails);
      dm.persist(persistedAccount);

      NotifierSystem.notifyAccount(persistedAccount, "Welcome to Service Supplier! " +
            "We make paying feel like playing!");

      return new SuccessResponse("Registration successful.");
   }
}
