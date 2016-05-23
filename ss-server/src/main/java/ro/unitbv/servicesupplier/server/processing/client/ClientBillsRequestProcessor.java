package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.ClientBillsRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.client.ClientBillsResponse;
import ro.unitbv.servicesupplier.model.dto.BillDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.conversion.BillConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class ClientBillsRequestProcessor implements RequestProcessor<ClientBillsRequest> {

   @Override
   public ServerResponse process(ClientBillsRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getClientId();
      ClientAccount client = dm.findById(ClientAccount.class, id);

      if (client == null) {
         return new InvalidDataResponse("clientId");
      }

      List<Bill> bills = new ArrayList<>();
      Boolean onlyPaid = request.onlyPaidRequested();

      for (Subscription subscription : client.getSubscriptions()) {
         if (onlyPaid == null) {
            bills.addAll(subscription.getIssuedBills());
            continue;
         }
         for (Bill bill : subscription.getIssuedBills()) {
            if (onlyPaid && bill.isPaid()) {
               bills.add(bill);
            } else if (!onlyPaid && !bill.isPaid()) {
               bills.add(bill);
            }
         }
      }

      List<BillDto> billDetails = BillConverter.toDto(bills);
      billDetails.sort(null);
      if ((onlyPaid == null || onlyPaid) && billDetails.size() > 15) {
         billDetails = new ArrayList<>(billDetails.subList(billDetails.size() - 15, billDetails.size()));
      }

      return new ClientBillsResponse(billDetails);
   }
}
