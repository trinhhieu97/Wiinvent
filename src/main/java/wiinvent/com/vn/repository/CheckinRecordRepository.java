package wiinvent.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiinvent.com.vn.domain.CheckinRecord;

import java.time.LocalDate;
import java.util.List;

public interface CheckinRecordRepository extends JpaRepository<CheckinRecord, String> {
    boolean existsByUserIdAndCheckinDate(String userId, LocalDate date);
    int countByUserIdAndMonthKey(String userId, String monthKey);
    List<CheckinRecord> findByUserIdAndMonthKey(String userId, String monthKey);
}
