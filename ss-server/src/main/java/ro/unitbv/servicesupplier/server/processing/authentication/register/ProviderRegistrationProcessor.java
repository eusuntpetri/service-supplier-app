package ro.unitbv.servicesupplier.server.processing.authentication.register;

import org.apache.commons.lang3.StringUtils;
import ro.unitbv.servicesupplier.model.communication.request.authentication.ProviderRegisterRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.authentication.UsernameInUseResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.AccountCredentialDto;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ProviderAccount;
import ro.unitbv.servicesupplier.server.encryption.EncryptUtils;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;

/**
 * Created by Petri on 16-May-16.
 */
public class ProviderRegistrationProcessor implements RequestProcessor<ProviderRegisterRequest> {

   @Override
   public ServerResponse process(ProviderRegisterRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      ProviderAccountDto providerDetails = request.getAccountDetails();
      AccountCredentialDto credentials = providerDetails.getCredentials();

      if (StringUtils.isEmpty(credentials.getUsername())) {
         return new InvalidDataResponse("username");
      } else if (StringUtils.isEmpty(credentials.getPassword())) {
         return new InvalidDataResponse("password");
      } else if (StringUtils.isEmpty(providerDetails.getName())) {
         return new InvalidDataResponse("first name");
      }

      if (dm.findAccountByUsername(credentials.getUsername()) != null) {
         return new UsernameInUseResponse();
      }

      providerDetails.getCredentials().setPassword(
            EncryptUtils.saltAndPepper(providerDetails.getCredentials().getPassword()));

      ProviderAccount persistedAccount = new ProviderAccount(providerDetails);
      dm.persist(persistedAccount);

      NotifierSystem.notifyAccount(persistedAccount, "Welcome to Service Supplier! " +
            "We are happy to service your services!");

      return new SuccessResponse("Registration successful.");
   }
}
