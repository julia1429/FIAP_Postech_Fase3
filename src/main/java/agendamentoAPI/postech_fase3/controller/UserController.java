package agendamentoAPI.postech_fase3.controller;

import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    }
}