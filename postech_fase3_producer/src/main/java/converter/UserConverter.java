package converter;

import agendamentoAPI.postech_fase3.dto.UserRequestDTO;
import agendamentoAPI.postech_fase3.model.User;

public class UserConverter {

    public static UserRequestDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserRequestDTO dto = new UserRequestDTO();
        dto.setName(user.getNome());
        dto.setName(user.getCpf());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getTelefone());
        return dto;
    }

}
