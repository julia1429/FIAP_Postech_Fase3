package agendamentoAPI.postech_fase3.controller;

import agendamentoAPI.postech_fase3.dto.UserResponseDTO;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.service.UserService;
import agendamentoAPI.postech_fase3.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserConverter converter;

    @PostMapping
    @PreAuthorize("hasRole('ENFERMEIRO')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@RequestBody User user) {
        User saved = userService.create(user);
        return converter.toDTO(saved);
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    }
}