package wiinvent.com.vn.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.dto.response.CheckinStatusResponse;
import wiinvent.com.vn.dto.response.UserProfileResponse;
import wiinvent.com.vn.service.base.CheckinService;
import wiinvent.com.vn.service.base.UserService;
import wiinvent.com.vn.util.RequestContext;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CheckinService checkinService;

    @Operation(
            summary = "Get current user profile",
            description = "Returns the profile of the currently logged-in user"
    )
    @GetMapping("/me")
    public ResponseData<UserProfileResponse> profile() {
        return userService.getCurrent(RequestContext.getUserId());
    }

    @Operation(
            summary = "Get current user check-in status",
            description = "Returns the check-in status for the current user, including days checked and button state."
    )
    @GetMapping("/status")
    public ResponseData<CheckinStatusResponse> status() {
        return new ResponseData<>(checkinService.getStatus(RequestContext.getUserId()));
    }
}
