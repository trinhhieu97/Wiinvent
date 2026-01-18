package wiinvent.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wiinvent.com.vn.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query("""
        UPDATE User u 
        SET u.totalPoint = u.totalPoint - :point 
        WHERE u.id = :userId AND u.totalPoint >= :point
    """)
    int deductPoints(@Param("userId") String userId, @Param("point") int point);
}
