package dev.corn.cornbackend.api;

import dev.corn.cornbackend.config.jwtprocessing.JwtAuthed;
import dev.corn.cornbackend.entities.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    @GetMapping("userinfo")
    public User userinfo(@JwtAuthed User user) {
        return user;
    }

}
