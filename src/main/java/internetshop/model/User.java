package internetshop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
}