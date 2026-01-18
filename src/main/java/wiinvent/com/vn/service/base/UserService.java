package wiinvent.com.vn.service.base;

import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.dto.response.UserProfileResponse;

public interface UserService {
    ResponseData<UserProfileResponse> getCurrent(String userId);
}
