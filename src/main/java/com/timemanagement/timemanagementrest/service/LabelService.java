package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Label;
import com.timemanagement.timemanagementrest.domain.dto.LabelDTO;
import com.timemanagement.timemanagementrest.exception.LabelNotFoundException;
import com.timemanagement.timemanagementrest.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {
    private final LabelRepository labelRepository;
    private final Label label;

    @Autowired
    public LabelService(LabelRepository labelRepository, Label label) {
        this.labelRepository = labelRepository;
        this.label = label;
    }

    public List<Label> getLabels() {
        return labelRepository.findAllLabels();
    }

    public Optional<Label> getLabel(Long id) {
        return labelRepository.findByIdLabel(id);
    }

    public void createLabel(LabelDTO labelDTO) {
        label.setPriority("NOT_IMPORTANT");
        label.setId(labelRepository.getNextSequenceValue());
        label.setCategory(labelDTO.getCategory());
        label.setStyle(labelDTO.getStyle());
        labelRepository.saveLabel(label);
    }

    public void updateLabel(Label label) {
        labelRepository.saveAndFlushLabel(label);
    }

    public void deleteLabelById(Long id) {
        labelRepository.deleteByIdLabel(id);
    }

    public Label getLabelByLabelCategory(String labelCategory){
        return labelRepository.findByCategory(labelCategory).orElseThrow(LabelNotFoundException::new);
    }
}