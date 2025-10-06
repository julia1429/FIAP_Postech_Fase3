
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(255),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE user_types_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE professionals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    specialty VARCHAR(150),
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_professional_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

