package wiinvent.com.vn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.service.base.CheckinService;
import wiinvent.com.vn.util.RequestContext;

@RestController
@RequestMapping("/api/checkin")
@RequiredArgsConstructor
public class CheckinController {
    private final CheckinService checkinService;

    @PostMapping
    public ResponseData<?> checkin() {
        checkinService.checkin(RequestContext.getUserId());
        return ResponseData.SUCCESS;
    }
}
