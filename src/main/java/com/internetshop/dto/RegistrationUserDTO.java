package com.internetshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDTO {

    @Pattern(regexp = "^[a-z0-9_-]{3,16}$",
            message = "{error.incorrect-name}")
    @NotEmpty(message = "{error.mandatory-name}")
    private String name;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "{error.incorrect-password}")
    @NotEmpty(message = "{error.mandatory-password}")
    private String password;

    @NotBlank(message = "{error.mandatory-confirm-password}")
    private String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z]{2,}$",
            message = "{error.incorrect-first-name}")
    @NotBlank(message = "{error.mandatory-first-name}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{2,}$",
            message = "{error.incorrect-last-name}")
    @NotBlank(message = "{error.mandatory-last-name}")
    private String lastName;

    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})",
            message = "{error.incorrect-email}")
    @NotBlank(message = "{error.mandatory-email}")
    private String email;

    @Min(14)
    private int age;

    @AssertTrue(message = "{error.password-valid}")
    private boolean isConfirmedPassword() {
        return this.password.equals(this.confirmPassword);
    }

}
