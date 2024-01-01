package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE id = :taskId")
    Optional<Task> findByIdTask(@Param("taskId") Long taskId);

    @Query(nativeQuery = true, value = "SELECT * FROM tasks")
    List<Task> findAllTasks();

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE user_id = : currentId")
    List<Task> findMyTasks(Long currentId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO tasks(id, tasks_title, tasks_description,tasks_creation_date, tasks_due_date, time_for_task, tasks_check , progress, user_id, category_id, is_overdue) VALUES (:#{#task.id}, :#{#task.title}, :#{#task.description},:#{#task.creationDate}, :#{#task.dueDate}, :#{#task.timeForTasks}, :#{#task.check}, :#{#task.progress} , :#{#task.userId} , :#{#task.categoryId} , :#{#task.isOverdue})")
    void saveTask(@Param("task") Task task);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tasks SET tasks_title = :#{#task.title},  tasks_description = :#{#task.description}, tasks_creation_date = :#{#task.creationDate},tasks_due_date = :#{#task.dueDate},time_for_task = :#{#task.timeForTasks}, tasks_check = :#{#task.check}, progress = :#{#task.progress}, user_id = :#{#task.userId},  category_id = :#{#task.categoryId}, is_overdue = :#{#task.isOverdue} WHERE id = :#{#task.id}")
    void saveAndFlushTask(@Param("task") Task task);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tasks WHERE id = :taskId")
    void deleteByIdTask(@Param("taskId") Long taskId);

    @Query(value = "SELECT NEXTVAL('tasks_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
