package wiinvent.com.vn.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckinRedisService {
    private final RedissonClient redissonClient;
    public RLock lock(String userId) {
        return redissonClient.getLock("lock:checkin:" + userId);
    }
}
