package wiinvent.com.vn.service.base;

import wiinvent.com.vn.dto.response.CheckinStatusResponse;

public interface CheckinService {
    void checkin(String userId);

    CheckinStatusResponse getStatus(String userId);
}
