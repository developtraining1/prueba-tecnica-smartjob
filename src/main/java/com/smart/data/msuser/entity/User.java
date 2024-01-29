package com.smart.data.msuser.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@Table(name="users")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @UuidGenerator
    private String id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z ]+$")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9+_.-]+@(domain.cl)$")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime created;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime modified;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="phone_id")
    private List<Phone> phones;

    public User() {}

}
