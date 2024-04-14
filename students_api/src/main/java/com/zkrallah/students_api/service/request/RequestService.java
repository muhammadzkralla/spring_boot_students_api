package com.zkrallah.students_api.service.request;

import com.zkrallah.students_api.entity.Request;

import java.util.List;
import java.util.Set;

public interface RequestService {
    Request createRequest(Long userId, Long classID);

    List<Request> getRequests();

    Request getRequestById(Long requestId);

    Set<Request> getUserRequests(Long userId);

    Set<Request> getClassRequests(Long classId);
}
