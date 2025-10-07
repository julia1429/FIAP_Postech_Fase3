package agendamentoAPI.postech_fase3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String name;

    private String cpf;

    private String email;

    private String phone;

}
