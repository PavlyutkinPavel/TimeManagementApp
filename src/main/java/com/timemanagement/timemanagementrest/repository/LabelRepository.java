package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Label;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM labels WHERE id = :labelId")
    Optional<Label> findByIdLabel(@Param("labelId") Long labelId);

    @Query(nativeQuery = true, value = "SELECT * FROM labels")
    List<Label> findAllLabels();

    @Query(nativeQuery = true, value = "SELECT * FROM labels WHERE labels_category = :labelCategory")
    Optional<Label> findByCategory(String labelCategory);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO labels(id, labels_category, labels_priority, labels_style) VALUES (:#{#label.id}, :#{#label.category}, :#{#label.priority}, :#{#label.style})")
    void saveLabel(@Param("label") Label label);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE labels SET labels_category = :#{#label.category},  labels_priority = :#{#label.priority}, labels_style = :#{#label.style} WHERE id = :#{#label.id}")
    void saveAndFlushLabel(@Param("label") Label label);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM labels WHERE id = :labelId")
    void deleteByIdLabel(@Param("labelId") Long labelId);

    @Query(value = "SELECT NEXTVAL('labels_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
