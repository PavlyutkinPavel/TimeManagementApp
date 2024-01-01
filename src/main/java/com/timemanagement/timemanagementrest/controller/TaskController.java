package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Task;
import com.timemanagement.timemanagementrest.domain.dto.TaskDTO;
import com.timemanagement.timemanagementrest.domain.dto.TaskDescriptionDTO;
import com.timemanagement.timemanagementrest.domain.dto.TaskPriorityDTO;
import com.timemanagement.timemanagementrest.domain.dto.TaskProgressDTO;
import com.timemanagement.timemanagementrest.exception.TaskNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Task Controller", description = "makes all operations with tasks")
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final SecurityService securityService;

    public TaskController(TaskService taskService, SecurityService securityService) {
        this.taskService = taskService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all tasks(for admins)")
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<Task> tasks = taskService.getTasks();
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(tasks, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get all tasks(for admins and authorized users)")
    @GetMapping("/my_tasks")
    public ResponseEntity<List<Task>> getTasksOfCurrentUser(Principal principal) {
        Long currentId = securityService.getUserIdByLogin(principal.getName());
        List<Task> tasks = taskService.getMyTasks(currentId);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    @Operation(summary = "get specific task(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.getTask(id).orElseThrow(TaskNotFoundException::new);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(summary = "create task(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createTask(@Valid @RequestBody TaskDTO taskDTO, Principal principal) {
        taskService.createTask(taskDTO, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update tasks(for all admins and task's author)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateTask(@Valid @RequestBody Task task, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (task.getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            taskService.updateTask(task);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "update task description(for all admins and task's author)")
    @PutMapping("/new_description")
    public ResponseEntity<HttpStatus> updateTaskDescription(@Valid @RequestBody TaskDescriptionDTO taskDescriptionDTO, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (taskDescriptionDTO.getMyLogin() == principal.getName())) {
            taskService.updateTaskDescription(taskDescriptionDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "update task priority(for all admins and task's author)")
    @PutMapping("/new_priority")
    public ResponseEntity<HttpStatus> updateTaskPriority(@Valid @RequestBody TaskPriorityDTO taskPriorityDTO, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (taskPriorityDTO.getMyLogin() == principal.getName())) {
            taskService.updateTaskPriority(taskPriorityDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "update task priority(for all admins and task's author)")
    @PutMapping("/set_progress")
    public ResponseEntity<HttpStatus> setTaskProgress(@Valid @RequestBody TaskProgressDTO taskProgressDTO, Principal principal) {
        Long taskCreatorId = taskService.getTask(taskProgressDTO.getId()).orElseThrow(TaskNotFoundException::new).getUserId();
        Long httpRequestSenderId = securityService.getUserIdByLogin(principal.getName());
        if (securityService.checkIfAdmin(principal.getName()) || httpRequestSenderId == taskCreatorId) {
            taskService.setTaskProgress(taskProgressDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete tasks(for all admins and task's author)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (getTask(id).getBody().getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            taskService.deleteTaskById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
