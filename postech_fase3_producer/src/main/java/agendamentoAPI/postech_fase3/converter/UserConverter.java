package agendamentoAPI.postech_fase3.converter;

import agendamentoAPI.postech_fase3.dto.UserResponseDTO;
import agendamentoAPI.postech_fase3.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getNome(),
                user.getNome(),
                user.getEmail(),
                user.getTelefone()
        );
    }

}
