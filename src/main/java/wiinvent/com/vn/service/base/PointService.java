package wiinvent.com.vn.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wiinvent.com.vn.dto.common.BasePageResponse;
import wiinvent.com.vn.dto.response.PointHistoryResponse;

public interface PointService {
    BasePageResponse<PointHistoryResponse> getPointHistory(Pageable pageable);
    void deduct(String userId, int point);
}
