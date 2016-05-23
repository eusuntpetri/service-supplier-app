package ro.unitbv.servicesupplier.server.conversion;

import ro.unitbv.servicesupplier.model.dto.NoticeDto;
import ro.unitbv.servicesupplier.repository.persistence.notice.Notice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class NoticeConverter {

   public static List<NoticeDto> toDto(List<Notice> notices) {
      if (notices == null || notices.isEmpty()) return Collections.emptyList();

      List<NoticeDto> noticeDetails = new ArrayList<>();

      for (Notice notice : notices) {
         noticeDetails.add(toDto(notice));
      }

      return noticeDetails;
   }

   public static NoticeDto toDto(Notice notice) {
      return new NoticeDto(notice.getId(), notice.getMessage(), notice.getIssueDate());
   }

}
