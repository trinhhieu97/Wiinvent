package wiinvent.com.vn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wiinvent.com.vn.domain.PointHistory;

public interface PointHistoryRepository extends JpaRepository<PointHistory, String> {
    Page<PointHistory> findByUserId(String userId, Pageable pageable);
}
