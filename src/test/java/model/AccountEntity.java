package model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
public class AccountEntity {
    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @Email
    private String email;

    @JsonSerialize(using = InstantSerializer.class)
    @Column(name = "created_at")
    private Instant createdAt;
}
