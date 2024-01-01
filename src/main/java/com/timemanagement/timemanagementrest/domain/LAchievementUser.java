package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "l_achievements_users")
@Data
@Component
public class LAchievementUser {
    @Id
    @SequenceGenerator(name = "l_achievements_usersSeqGen", sequenceName = "l_achievements_users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "l_achievements_usersSeqGen")
    private Long id;

    @Column(name = "achievement_id")
    private Long achievementId;

    @Column(name = "users_id")
    private Long usersId;

}