package com.smart.data.msuser.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@Entity
@Table(name="phone")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phone {
    @Id
    @UuidGenerator
    private String id;

    @NotNull
    @Pattern(regexp = "[0-9]{9}$")
    private String number;

    @NotNull
    @NumberFormat
    private String citycode;

    @NotNull
    @NumberFormat
    private String countrycode;

    public Phone() {}

}
