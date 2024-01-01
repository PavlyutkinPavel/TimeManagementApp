package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Achievement;
import com.timemanagement.timemanagementrest.domain.LAchievementUser;
import com.timemanagement.timemanagementrest.domain.dto.AchievementDTO;
import com.timemanagement.timemanagementrest.exception.AchievementNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.AchievementService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Achievement Controller", description = "makes all operations with achievements")
@RestController
@RequestMapping("/achievement")
public class AchievementController {
    private final AchievementService achievementService;
    private final SecurityService securityService;

    public AchievementController(AchievementService achievementService, SecurityService securityService) {
        this.achievementService = achievementService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all achievements(for all authorized users)")
    @GetMapping
    public ResponseEntity<List<Achievement>> getAchievements() {
        List<Achievement> achievements = achievementService.getAchievements();
        if (achievements.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        }
    }

    @Operation(summary = "get specific achievement(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Achievement> getAchievement(@PathVariable Long id) {
        Achievement achievement = achievementService.getAchievement(id).orElseThrow(AchievementNotFoundException::new);
        return new ResponseEntity<>(achievement, HttpStatus.OK);
    }

    @Operation(summary = "get specific achievement(for all authorized users)")
    @GetMapping("/my_achievements")
    public ResponseEntity<List<LAchievementUser>> getCurrentUserAchievement(@RequestParam Long id) {
        List<LAchievementUser> achievements = achievementService.getCurrentAchievement(id);
        if (achievements.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        }
    }

    @Operation(summary = "create achievement(for all admins)")
    @PostMapping
    public ResponseEntity<HttpStatus> createAchievement(@Valid @RequestBody AchievementDTO achievementDTO, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            achievementService.createAchievement(achievementDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "update achievements(for all admins)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateAchievement(@Valid @RequestBody Achievement achievement, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            achievementService.updateAchievement(achievement);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "update achievements(for all admins)")
    @PutMapping("/set_user_achievement")
    public ResponseEntity<HttpStatus> updateUserAchievement(@RequestParam Long achievementId, @RequestParam Long userId, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            achievementService.updateUserAchievement(achievementId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete achievements(for all admins)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAchievement(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            achievementService.deleteAchievementById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
