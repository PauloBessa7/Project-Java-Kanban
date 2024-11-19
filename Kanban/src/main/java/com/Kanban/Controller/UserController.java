package com.Kanban.Controller;

import com.Kanban.DTO.CreateUserDTO;
import com.Kanban.DTO.JwtTokenDTO;
import com.Kanban.DTO.LoginUserDTO;
import com.Kanban.Modal.User;
import com.Kanban.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JwtTokenDTO authenticateUser(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.authenticateUser(loginUserDto);
        return token;
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserDTO createUserDto) {
        User user = userService.createUser(createUserDto);
        if(user != null)
            return user;
        throw new RuntimeException("Erro ao criar usuario");
    }

}