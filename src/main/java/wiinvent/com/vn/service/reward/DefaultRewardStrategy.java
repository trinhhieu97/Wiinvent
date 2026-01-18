package wiinvent.com.vn.service.reward;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultRewardStrategy implements RewardStrategy{
    private static final List<Integer> REWARD = List.of(1,2,3,5,8,13,21);
    @Override
    public int reward(int sequence) {
        return REWARD.get(sequence - 1);
    }
}
