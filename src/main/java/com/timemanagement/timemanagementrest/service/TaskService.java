package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Task;
import com.timemanagement.timemanagementrest.domain.dto.TaskDTO;
import com.timemanagement.timemanagementrest.domain.dto.TaskDescriptionDTO;
import com.timemanagement.timemanagementrest.domain.dto.TaskPriorityDTO;
import com.timemanagement.timemanagementrest.domain.dto.TaskProgressDTO;
import com.timemanagement.timemanagementrest.exception.TaskNotFoundException;
import com.timemanagement.timemanagementrest.repository.TaskRepository;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final SecurityService securityService;
    private final LabelService labelService;
    private final Task task;

    @Autowired
    public TaskService(TaskRepository taskRepository, SecurityService securityService, LabelService labelService, Task task) {
        this.taskRepository = taskRepository;
        this.securityService = securityService;
        this.labelService = labelService;
        this.task = task;
    }

    public List<Task> getTasks() {
        return taskRepository.findAllTasks();
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findByIdTask(id);
    }

    public List<Task> getMyTasks(Long currentId){
        List<Task> allTasks = taskRepository.findAllTasks();
        List<Task> myTasksList = new ArrayList<>(){};
        for (Task t: allTasks) {
            if(t.getUserId() == currentId){
                myTasksList.add(t);
            }
        }
        return  myTasksList; }

    public void createTask(TaskDTO taskDTO, Principal principal) {
        task.setId(taskRepository.getNextSequenceValue());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCreationDate(LocalDateTime.now());
        task.setDueDate(taskDTO.getDueDate());
        LocalDateTime creationDate = task.getCreationDate();
        LocalDateTime dueDate = task.getDueDate();
        Duration duration = Duration.between(creationDate, dueDate);
        long timeInMilliseconds = duration.toMillis();
        task.setTimeForTasks(timeInMilliseconds);
        task.setCheck(false);
        task.setProgress(0);
        task.setUserId(securityService.getUserIdByLogin(principal.getName()));
        task.setCategoryId(labelService.getLabelByLabelCategory(taskDTO.getCategory()).getId());
        task.setIsOverdue(timeInMilliseconds < 0);
        taskRepository.saveTask(task);
    }

    public void updateTask(Task task) {
        taskRepository.saveAndFlushTask(task);
    }

    public void updateTaskDescription(TaskDescriptionDTO taskDescriptionDTO) {
        Task myTask  = taskRepository.findByIdTask(taskDescriptionDTO.getId()).orElseThrow(TaskNotFoundException::new);
        myTask.setDescription(taskDescriptionDTO.getNewDescription());
        taskRepository.saveAndFlushTask(task);
    }
    public void updateTaskPriority(TaskPriorityDTO taskPriorityDTO) {
        Task myTask  = taskRepository.findByIdTask(taskPriorityDTO.getId()).orElseThrow(TaskNotFoundException::new);
        myTask.setCategoryId(labelService.getLabelByLabelCategory(taskPriorityDTO.getCategory()).getId());
        taskRepository.saveAndFlushTask(task);
    }

    public void setTaskProgress(TaskProgressDTO taskProgressDTO) {
        Task myTask  = taskRepository.findByIdTask(taskProgressDTO.getId()).orElseThrow(TaskNotFoundException::new);
        myTask.setProgress(taskProgressDTO.getProgress());
        taskRepository.saveAndFlushTask(myTask);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteByIdTask(id);
    }

}