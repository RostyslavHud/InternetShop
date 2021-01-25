package com.internetshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDTO {

    @Pattern(regexp = "^[a-z0-9_-]{3,16}$",
            message = "Name shouldn't have special symbols and it's name should be from 3 to 16 symbols.")
    @NotEmpty(message = "Name is mandatory")
    private String name;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Password should be strong and it should have 1 big letter," +
                    " 1 small letter and 1 numeral and it's length should be min 8 symbols")
    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z]{2,}$",
            message = "First name name should have only symbols and it's length should be min 2 symbols")
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{2,}$",
            message = "Last name should have only symbols and it's length should be min 2 symbols")
    @NotBlank(message = "Last is mandatory")
    private String lastName;

    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})",
            message = "Your email isn't correct")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Min(14)
    private int age;

    @AssertTrue(message = "Password and Confirm password can be same")
    private boolean isConfirmedPassword() {
        return this.password.equals(this.confirmPassword);
    }

}
