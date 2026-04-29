package com.aashi.saas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.service.TaskService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final TaskService taskService;

    @GetMapping("/task-summary")
    public ResponseEntity<String> getAiSummary() {

        String summary = taskService.getAiTaskSummary();

        return ResponseEntity.ok(summary);
    }
}