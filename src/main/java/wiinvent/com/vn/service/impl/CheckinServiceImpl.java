package wiinvent.com.vn.service.impl;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiinvent.com.vn.config.CheckinConfig;
import wiinvent.com.vn.constant.ResponseCode;
import wiinvent.com.vn.constant.enums.CheckinButtonState;
import wiinvent.com.vn.constant.enums.PointHistoryType;
import wiinvent.com.vn.domain.CheckinRecord;
import wiinvent.com.vn.domain.PointHistory;
import wiinvent.com.vn.domain.User;
import wiinvent.com.vn.dto.response.CheckinDayStatus;
import wiinvent.com.vn.dto.response.CheckinStatusResponse;
import wiinvent.com.vn.exception.ApplicationException;
import wiinvent.com.vn.redis.CheckinRedisService;
import wiinvent.com.vn.repository.CheckinRecordRepository;
import wiinvent.com.vn.repository.PointHistoryRepository;
import wiinvent.com.vn.repository.UserRepository;
import wiinvent.com.vn.service.base.CheckinService;
import wiinvent.com.vn.service.reward.RewardStrategy;
import wiinvent.com.vn.util.CheckinTimeUtil;
import wiinvent.com.vn.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static wiinvent.com.vn.util.DateTimeUtil.YEAR_MONTH_FORMATTER;

@Service
@RequiredArgsConstructor
public class CheckinServiceImpl implements CheckinService {
    private final CheckinRecordRepository checkinRepo;
    private final UserRepository userRepo;
    private final PointHistoryRepository historyRepo;
    private final RewardStrategy rewardStrategy;
    private final CheckinRedisService redisService;
    private final CheckinConfig checkinConfig;
    @Transactional
    public void checkin(String userId) {
        validateTime();
        RLock lock = redisService.lock(userId);
        lock.lock();

        try {
            LocalDate today = LocalDate.now();
            String monthKey = YearMonth.now().format(DateTimeUtil.YEAR_MONTH_FORMATTER);

            if (checkinRepo.existsByUserIdAndCheckinDate(userId, today)) {
                throw new ApplicationException(ResponseCode.ALREADY_CHECKED_IN);
            }


            int count = checkinRepo.countByUserIdAndMonthKey(userId, monthKey);
            if (count >= checkinConfig.getMaxDaysPerMonth()) throw new ApplicationException(ResponseCode.MAX_CHECKIN_REACHED);


            int seq = count + 1;
            int reward = rewardStrategy.reward(seq);

            checkinRepo.save(
                    CheckinRecord.builder()
                            .userId(userId)
                            .checkinDate(today)
                            .monthKey(monthKey)
                            .sequence(seq)
                            .rewardPoint(reward)
                            .build()
            );

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new ApplicationException(ResponseCode.USER_NOT_FOUND));
            user.setTotalPoint(user.getTotalPoint() + reward);

            historyRepo.save(
                    PointHistory.builder()
                            .userId(userId)
                            .changePoint(reward)
                            .type(PointHistoryType.CHECK_IN)
                            .description("Daily check-in")
                            .build()
            );

        } finally {
            lock.unlock();
        }
    }

    private void validateTime() {
        if (!CheckinTimeUtil.isValidCheckinTime(LocalTime.now())) {
            throw new ApplicationException(ResponseCode.OUT_OF_TIME);
        }
    }

    @Override
    public CheckinStatusResponse getStatus(String userId) {
        LocalDate today = LocalDate.now();
        String monthKey = YearMonth.now().format(YEAR_MONTH_FORMATTER);

        List<CheckinRecord> records = checkinRepo.findByUserIdAndMonthKey(userId, monthKey);

        Map<Integer, LocalDate> checkedMap = records.stream()
                .collect(Collectors.toMap(CheckinRecord::getSequence, CheckinRecord::getCheckinDate));

        int nextSeq = checkedMap.size() + 1;

        List<CheckinDayStatus> days = new ArrayList<>();
        boolean todayAssigned = false;

        int maxDaysPerMonth = checkinConfig.getMaxDaysPerMonth();

        for (int i = 1; i <= maxDaysPerMonth; i++) {
            boolean checked = checkedMap.containsKey(i);
            boolean isToday = false;

            if (!todayAssigned) {
                if (checked && checkedMap.get(i).equals(today)) {
                    isToday = true;
                    todayAssigned = true;
                } else if (!checked && i == nextSeq) {
                    isToday = true;
                    todayAssigned = true;
                }
            }

            days.add(new CheckinDayStatus(
                    i,
                    rewardStrategy.reward(i),
                    checked,
                    isToday
            ));
        }

        CheckinButtonState button = resolveButtonState(userId, today);

        return new CheckinStatusResponse(days, button);
    }


    private CheckinButtonState resolveButtonState(String userId, LocalDate today) {
        if (checkinRepo.existsByUserIdAndCheckinDate(userId, today)) {
            return CheckinButtonState.CHECKED;
        }

        String monthKey = YearMonth.now().format(DateTimeUtil.YEAR_MONTH_FORMATTER);
        int count = checkinRepo.countByUserIdAndMonthKey(userId, monthKey);
        if (count >= checkinConfig.getMaxDaysPerMonth()) {
            return CheckinButtonState.CHECKED;
        }

        boolean valid = CheckinTimeUtil.isValidCheckinTime(LocalTime.now());
        return valid ? CheckinButtonState.CHECKIN : CheckinButtonState.OUT_OF_TIME;
    }
}
