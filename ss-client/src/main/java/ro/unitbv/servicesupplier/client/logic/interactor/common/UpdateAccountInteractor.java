package ro.unitbv.servicesupplier.client.logic.interactor.common;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.model.communication.request.common.AccountRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.account.AccountResponse;
import ro.unitbv.servicesupplier.model.dto.AccountDto;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

/**
 * Created by Petri on 19-May-16.
 */
public class UpdateAccountInteractor implements
      Interactor<AccountRequest, AccountResponse> {

   private View view;

   public UpdateAccountInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(AccountRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(AccountResponse response) {
      AccountDto retrievedAccount = response.getAccountDetails();

      if (retrievedAccount instanceof ClientAccountDto) {
         ClientAccountDto retrieved = (ClientAccountDto) retrievedAccount;
         ClientAccountDto cached = LoginSession.getClient();

         cached.getCredentials()
               .setUsername(retrieved.getCredentials().getUsername());
         cached.setFirstName(retrieved.getFirstName());
         cached.setLastName(retrieved.getLastName());
         cached.setBalance(retrieved.getBalance());

         view.onAccountUpdateSuccess();
         return;
      }

      ProviderAccountDto retrieved = (ProviderAccountDto) retrievedAccount;
      ProviderAccountDto cached = LoginSession.getProvider();

      cached.getCredentials()
            .setUsername(retrieved.getCredentials().getUsername());
      cached.setName(retrieved.getName());


      view.onAccountUpdateSuccess();
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onAccountUpdateFailure(response.getMessage());
   }


   public interface View {

      void onAccountUpdateSuccess();

      void onAccountUpdateFailure(String message);

   }
}
