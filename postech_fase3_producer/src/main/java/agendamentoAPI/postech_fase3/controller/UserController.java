package agendamentoAPI.postech_fase3.controller;

import agendamentoAPI.postech_fase3.dto.UserDTO;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDto) {
        User user = userService.create(userDto);
            return ResponseEntity.ok(toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        List<UserDTO> users = userService.list().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(toDTO(user));
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .specialty(user.getProfessional() != null ? user.getProfessional().getSpecialty() : null)
                .build();
    }
}