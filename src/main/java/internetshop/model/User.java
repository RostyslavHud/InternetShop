package internetshop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import internetshop.enums.Role;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty(message = "Name is mandatory")
    @Column(name = "user_name", nullable = false)
    private String name;

    @NotEmpty(message = "Password os mandatory")
    @Column(name = "user_password", nullable = false)
    private String password;

    @Transient
    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;

    @Min(14)
    @Column(name = "age")
    private int age;

    @Column(name = "active")
    private boolean active;

    @Column(name = "registration_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime registrationDate;

    @AssertFalse(message = "Password and Confirm password can be same")
    private boolean isConfirmedPassword() {
        return !this.password.equals(this.getConfirmPassword());
    }

    public User() {
        super();
        this.active = false;
    }
}
