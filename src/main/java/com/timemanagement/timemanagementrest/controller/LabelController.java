package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Label;
import com.timemanagement.timemanagementrest.domain.dto.LabelDTO;
import com.timemanagement.timemanagementrest.exception.LabelNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Label Controller", description = "makes all operations with labels")
@RestController
@RequestMapping("/label")
public class LabelController {
    private final LabelService labelService;
    private final SecurityService securityService;

    public LabelController(LabelService labelService, SecurityService securityService) {
        this.labelService = labelService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all labels(for all authorized users)")
    @GetMapping
    public ResponseEntity<List<Label>> getLabels() {
        List<Label> labels = labelService.getLabels();
        if (labels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(labels, HttpStatus.OK);
        }
    }

    @Operation(summary = "get specific label(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Label> getLabel(@PathVariable Long id) {
        Label label = labelService.getLabel(id).orElseThrow(LabelNotFoundException::new);
        return new ResponseEntity<>(label, HttpStatus.OK);
    }

    @Operation(summary = "create label(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createLabel(@RequestBody LabelDTO labelDTO) {
        labelService.createLabel(labelDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update labels(for all admins and label's author)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateLabel(@RequestBody Label label, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            labelService.updateLabel(label);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete labels(for all admins and label's author)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLabel(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            labelService.deleteLabelById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
