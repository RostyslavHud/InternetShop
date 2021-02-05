package com.internetshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUserDTO {

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "{error.incorrect-password}")
    @NotEmpty(message = "{error.mandatory-password}")
    private String password;

    @NotBlank(message = "{error.mandatory-confirm-password}")
    private String confirmPassword;

    @AssertTrue(message = "{error.password-valid}")
    private boolean isConfirmedPassword() {
        return this.password.equals(this.confirmPassword);
    }
}
