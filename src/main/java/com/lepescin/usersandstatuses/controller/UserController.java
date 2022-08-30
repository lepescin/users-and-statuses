package com.lepescin.usersandstatuses.controller;

import com.lepescin.usersandstatuses.dto.StatusRequestDto;
import com.lepescin.usersandstatuses.dto.StatusResponseDto;
import com.lepescin.usersandstatuses.dto.UserDto;
import com.lepescin.usersandstatuses.model.Status;
import com.lepescin.usersandstatuses.model.User;
import com.lepescin.usersandstatuses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        User user = userService.fromDto(userDto);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/changeStatus")
    public ResponseEntity changeStatus(@RequestBody StatusRequestDto requestDto) {
        User user = userService.getUserById(requestDto.getUserId());
        String oldStatus = user.getStatus().toString();

        User updatedUser = userService.updateUserStatus(user, Status.valueOf(requestDto.getNewStatus()));
        String newStatus = updatedUser.getStatus().toString();

        StatusResponseDto responseDto = new StatusResponseDto(updatedUser.getId(), oldStatus, newStatus);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
