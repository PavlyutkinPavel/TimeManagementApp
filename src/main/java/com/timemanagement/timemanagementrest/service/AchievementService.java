package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Achievement;
import com.timemanagement.timemanagementrest.domain.LAchievementUser;
import com.timemanagement.timemanagementrest.domain.dto.AchievementDTO;
import com.timemanagement.timemanagementrest.repository.AchievementRepository;
import com.timemanagement.timemanagementrest.repository.LAchievementUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final Achievement achievement;
    private final LAchievementUserRepository lAchievementUserRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, Achievement achievement, LAchievementUserRepository lAchievementUserRepository) {
        this.achievementRepository = achievementRepository;
        this.achievement = achievement;
        this.lAchievementUserRepository = lAchievementUserRepository;
    }

    public List<Achievement> getAchievements() {
        return achievementRepository.findAllAchievements();
    }

    public Optional<Achievement> getAchievement(Long id) {
        return achievementRepository.findByIdAchievement(id);
    }

    public List<LAchievementUser> getCurrentAchievement(Long id) {
        List<LAchievementUser> lAchievementUsers = lAchievementUserRepository.findAll();
        List<LAchievementUser> newList = new ArrayList<>(){};
        for (LAchievementUser l: lAchievementUsers) {
            if(l.getUsersId() == id){
                newList.add(l);
            }
        }
        return newList;
    }

    public void createAchievement(AchievementDTO achievementDTO) {
        achievement.setId(achievementRepository.getNextSequenceValue());
        achievement.setName(achievementDTO.getName());
        achievement.setDescription(achievementDTO.getDescription());
        achievement.setStyles(achievementDTO.getStyles());
        achievementRepository.saveAchievement(achievement);
    }

    public void updateAchievement(Achievement achievement) {
        achievementRepository.saveAndFlushAchievement(achievement);
    }

    public  void  updateUserAchievement(Long achievementId, Long userId){
        lAchievementUserRepository.setUserAchievement(achievementId, userId);
    }

    public void deleteAchievementById(Long id) {
        achievementRepository.deleteByIdAchievement(id);
    }

}