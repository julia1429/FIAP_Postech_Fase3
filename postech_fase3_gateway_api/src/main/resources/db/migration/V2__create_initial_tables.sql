CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_time DATETIME NOT NULL,
    user_id BIGINT NOT NULL,
    professional_id BIGINT NOT NULL,

    CONSTRAINT fk_appointment_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_professional FOREIGN KEY (professional_id) REFERENCES professionals(id) ON DELETE CASCADE
);

CREATE TABLE appointments_histories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(30),
    notes TEXT
);
