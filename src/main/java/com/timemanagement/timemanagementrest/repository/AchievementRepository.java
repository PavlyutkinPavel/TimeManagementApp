package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Achievement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM achievements WHERE id = :achievementId")
    Optional<Achievement> findByIdAchievement(@Param("achievementId") Long achievementId);

    @Query(nativeQuery = true, value = "SELECT * FROM achievements")
    List<Achievement> findAllAchievements();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO achievements(id, achievements_name, achievements_description, achievements_styles) VALUES (:#{#achievement.id}, :#{#achievement.name}, :#{#achievement.description}, :#{#achievement.styles})")
    void saveAchievement(@Param("achievement") Achievement achievement);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE achievements SET achievements_name = :#{#achievement.name},  achievements_description = :#{#achievement.description}, achievements_styles = :#{#achievement.styles} WHERE id = :#{#achievement.id}")
    void saveAndFlushAchievement(@Param("achievement") Achievement achievement);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM achievements WHERE id = :achievementId")
    void deleteByIdAchievement(@Param("achievementId") Long achievementId);

    @Query(value = "SELECT NEXTVAL('achievements_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
