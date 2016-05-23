package ro.unitbv.servicesupplier.model.communication.response.success.common;

import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.NoticeDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class NoticesResponse extends SuccessResponse implements Serializable {

   private List<NoticeDto> notices;

   public NoticesResponse(List<NoticeDto> notices) {
      super("Notices retrieved successfully.");
      this.notices = notices;
   }

   public List<NoticeDto> getNotices() {
      return notices;
   }
}
