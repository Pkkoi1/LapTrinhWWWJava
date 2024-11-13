package iuh.fit.se.backEnd.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "first_Name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "middle_Name", length = 50)
    private String middleName;

    @Size(max = 50)
    @Column(name = "last_Name", length = 50)
    private String lastName;

    @Size(max = 15)
    @Column(name = "mobile", length = 15)
    private String mobile;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 32)
    @NotNull
    @Column(name = "password_Hash", nullable = false, length = 32)
    private String passwordHash;

    @NotNull
    @Column(name = "registered_At", nullable = false)
    private Instant registeredAt;

    @Column(name = "last_Login")
    private Instant lastLogin;

    @Lob
    @Column(name = "intro")
    private String intro;

    @Lob
    @Column(name = "profile")
    private String profile;

    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new LinkedHashSet<>();

}