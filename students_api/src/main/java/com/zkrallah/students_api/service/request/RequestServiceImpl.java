package com.zkrallah.students_api.service.request;

import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.RequestRepository;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final ClassService classService;

    @Override
    public Request createRequest(Long userId, Long classID) {
        User user = userService.getUserById(userId);
        Class requestedClass = classService.getClassById(classID);
        Optional<Request> request = requestRepository.findByUserAndRequestedClass(user, requestedClass);
        if (request.isPresent()) {
            throw new IllegalStateException("Request to " + requestedClass.getName() + " Already Exists!");
        }

        return requestRepository.save(createNewRequest(user, requestedClass));
    }

    private Request createNewRequest(User user, Class requestedClass) {
        Request request = new Request();
        Date now = new Date();

        request.setUser(user);
        request.setRequestedClass(requestedClass);
        request.setTimestamp(new Timestamp(now.getTime()));
        request.setStatus("WAITING");

        return request;
    }

    @Override
    public List<Request> getRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Request getRequestById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found."));
    }

    @Override
    public Set<Request> getUserRequests(Long userId) {
        User user = userService.getUserById(userId);
        return user.getRequests();
    }

    @Override
    public Set<Request> getClassRequests(Long classId) {
        Class _class = classService.getClassById(classId);
        return _class.getRequests();
    }

    @Override
    @Transactional
    public Request approveRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found."));

        User user = request.getUser();
        Class requestedClass = request.getRequestedClass();
        classService.addUserToClass(user.getId(), requestedClass.getId());
        request.setStatus("APPROVED");

        return request;
    }

    @Override
    @Transactional
    public Request declineRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found."));

        request.setStatus("DECLINED");

        return request;
    }
}
