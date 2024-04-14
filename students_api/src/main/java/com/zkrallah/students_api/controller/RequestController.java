package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<?> getRequests() {
        return ResponseEntity.ok(requestService.getRequests());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<?> getRequest(@PathVariable Long requestId) {
        try {
            Request request = requestService.getRequestById(requestId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not fetch request: " + e.getMessage()));
        }
    }
}
