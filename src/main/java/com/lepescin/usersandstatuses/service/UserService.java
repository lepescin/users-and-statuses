package com.lepescin.usersandstatuses.service;

import com.lepescin.usersandstatuses.dto.UserDto;
import com.lepescin.usersandstatuses.exception.NotFoundException;
import com.lepescin.usersandstatuses.model.Status;
import com.lepescin.usersandstatuses.model.User;
import com.lepescin.usersandstatuses.repository.UserRepository;
import com.lepescin.usersandstatuses.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final SchedulerService schedulerService;

    @Autowired
    public UserService(UserRepository userRepository, SchedulerService schedulerService) {
        this.userRepository = userRepository;
        this.schedulerService = schedulerService;
    }

    public User createUser(User user) {
        user.setStatus(Status.OFFLINE);
        User createdUser = userRepository.save(user);

        log.info("IN createUser - user: {} successfully created", createdUser);

        return createdUser;
    }

    public User fromDto(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return user;
    }

    public User getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("IN findById - no user found by id: {}", id);

            throw new NotFoundException("User not found");
        }

        log.info("IN findById - user: {} found by id: {}", user, id);
        return user;
    }

    public User updateUserStatus(User user, Status status) {
        user.setStatus(status);
        if (status.equals(Status.ONLINE)) {
            user.setStatusChanged(LocalDateTime.now());
            schedulerService.prepareScheduledSetUserStatusAwayJob(user);
        }
        User updatedUser = userRepository.save(user);

        log.info("IN updateUserStatus - user: {} successfully updateUserStatus", updatedUser);

        return updatedUser;
    }
}
