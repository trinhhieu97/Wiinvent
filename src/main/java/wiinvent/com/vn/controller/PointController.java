package wiinvent.com.vn.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import wiinvent.com.vn.domain.PointHistory;
import wiinvent.com.vn.dto.common.BasePageResponse;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.dto.response.PointHistoryResponse;
import wiinvent.com.vn.repository.PointHistoryRepository;
import wiinvent.com.vn.service.base.PointService;
import wiinvent.com.vn.util.ModelMapperUtil;
import wiinvent.com.vn.util.RequestContext;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @Operation(
            summary = "Get point history",
            description = "Returns paginated history of points for the current user"
    )
    @GetMapping("/history")
    public ResponseData<BasePageResponse<PointHistoryResponse>> history(Pageable pageable) {
        BasePageResponse<PointHistoryResponse> result = pointService.getPointHistory(pageable);
        return new ResponseData<>(result);
    }

    @Operation(
            summary = "Deduct points from user",
            description = "Deducts a specified number of points from the currently logged-in user."
    )
    @PostMapping("/deduct")
    public ResponseData<?> deduct(@RequestParam int point) {
        pointService.deduct(RequestContext.getUserId(), point);
        return ResponseData.SUCCESS;
    }
}
