package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.LAchievementUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LAchievementUserRepository extends JpaRepository<LAchievementUser, Long> {

    @Transactional
    @Modifying
    @Query(value = "CALL add_achievement_user(:achievementId, :userId)", nativeQuery = true)
    int setUserAchievement(Long achievementId, Long userId);

}
