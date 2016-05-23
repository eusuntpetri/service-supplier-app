package ro.unitbv.servicesupplier.model.communication.response.success.client;

import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.BillDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class ClientBillsResponse extends SuccessResponse implements Serializable {

   private List<BillDto> clientBills;

   public ClientBillsResponse(List<BillDto> clientBills) {
      super("Client bills retrieved successfully.");
      this.clientBills = clientBills;
   }

   public List<BillDto> getBills() {
      return clientBills;
   }
}
