package wiinvent.com.vn.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiinvent.com.vn.constant.ResponseCode;
import wiinvent.com.vn.domain.User;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.dto.response.UserProfileResponse;
import wiinvent.com.vn.exception.ApplicationException;
import wiinvent.com.vn.repository.UserRepository;
import wiinvent.com.vn.service.base.UserService;
import wiinvent.com.vn.util.ModelMapperUtil;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public ResponseData<UserProfileResponse> getCurrent(String userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResponseCode.USER_NOT_FOUND));
        return new ResponseData<>(ModelMapperUtil.toObject(user, UserProfileResponse.class));
    }

}
