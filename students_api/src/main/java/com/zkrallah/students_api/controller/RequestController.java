package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Request>>> getRequests() {
        return ResponseEntity.ok(createSuccessResponse(requestService.getRequests()));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ApiResponse<Request>> getRequest(@PathVariable Long requestId) {
        try {
            Request request = requestService.getRequestById(requestId);
            return ResponseEntity.ok(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch request: " + e.getMessage()));
        }
    }
}
