package wiinvent.com.vn.service.base;

import wiinvent.com.vn.dto.request.LoginRequest;
import wiinvent.com.vn.dto.request.RegisterRequest;
import wiinvent.com.vn.dto.response.AccessTokenResponse;
import wiinvent.com.vn.dto.common.ResponseData;

public interface AuthService {
    ResponseData<AccessTokenResponse> login(LoginRequest request);

    ResponseData<AccessTokenResponse> register(RegisterRequest request);
}
