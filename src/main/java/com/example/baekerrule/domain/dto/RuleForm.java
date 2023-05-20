package com.example.baekerrule.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class RuleForm {
    @NotBlank
    @Size(max = 10, min = 1)
    private String name;

    @Size(max = 30)
    private String about;

    @NotEmpty
    @Size(min = 1, max = 10)
    private String xp;

    @NotEmpty
    private String count;

    @NotBlank
    private String provider;

    @NotEmpty
    private String difficulty;
}
