package ro.unitbv.servicesupplier.server.processing.common;

import ro.unitbv.servicesupplier.model.communication.request.common.NoticesRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.NoticesResponse;
import ro.unitbv.servicesupplier.model.dto.NoticeDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;
import ro.unitbv.servicesupplier.repository.persistence.notice.Notice;
import ro.unitbv.servicesupplier.server.conversion.NoticeConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class NoticesRequestProcessor implements RequestProcessor<NoticesRequest> {

   @Override
   public ServerResponse process(NoticesRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getAccountId();
      Account account = dm.findById(Account.class, id);

      if (account == null) {
         return new InvalidDataResponse("accountId");
      }

      List<Notice> notices = account.getNotices();
      List<NoticeDto> noticeDetails = NoticeConverter.toDto(notices);

      noticeDetails.sort(null);
      if (noticeDetails.size() > 15) {
         noticeDetails = new ArrayList<>(noticeDetails.subList(noticeDetails.size() - 15, noticeDetails.size()));
      }

      return new NoticesResponse(noticeDetails);
   }
}
