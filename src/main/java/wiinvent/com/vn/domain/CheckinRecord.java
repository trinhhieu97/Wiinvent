package wiinvent.com.vn.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "checkin_record")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckinRecord {
    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDate checkinDate;

    @Column(nullable = false)
    private String monthKey;

    @Column(nullable = false)
    private int sequence;

    @Column(nullable = false)
    private int rewardPoint;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
