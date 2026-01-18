package wiinvent.com.vn.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiinvent.com.vn.constant.ResponseCode;
import wiinvent.com.vn.constant.enums.PointHistoryType;
import wiinvent.com.vn.domain.PointHistory;
import wiinvent.com.vn.dto.common.BasePageResponse;
import wiinvent.com.vn.dto.response.PointHistoryResponse;
import wiinvent.com.vn.exception.ApplicationException;
import wiinvent.com.vn.repository.PointHistoryRepository;
import wiinvent.com.vn.repository.UserRepository;
import wiinvent.com.vn.service.base.PointService;
import wiinvent.com.vn.util.ModelMapperUtil;
import wiinvent.com.vn.util.RequestContext;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final UserRepository userRepo;
    private final PointHistoryRepository historyRepo;
    @Transactional
    public void deduct(String userId, int point) {
        int updated = userRepo.deductPoints(userId, point);
        if (updated == 0) {
            throw new ApplicationException(ResponseCode.INSUFFICIENT_POINT);
        }

        historyRepo.save(PointHistory.builder()
                .userId(userId)
                .changePoint(-point)
                .type(PointHistoryType.DEDUCT)
                .description("Deduct point")
                .build()
        );
    }

    public BasePageResponse<PointHistoryResponse> getPointHistory(Pageable pageable) {
        String userId = RequestContext.getUserId();
        Page<PointHistory> page = historyRepo.findByUserId(userId, pageable);
        return BasePageResponse.fromPage(page, p -> ModelMapperUtil.toObject(p, PointHistoryResponse.class));
    }
}
