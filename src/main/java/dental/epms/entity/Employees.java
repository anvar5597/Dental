package dental.epms.entity;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.utils.TableName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Setter
@Getter
@ToString
@Table(name = TableName.EMPLOYEES,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "login"),
                @UniqueConstraint(columnNames = "email")})
public class Employees implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank

    private String firstName;

    @NotBlank

    private String lastName;

    @NotBlank

    private String patronymic;

    @NotBlank
    private String login;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String password;

    private LocalDate birthDay;

    private String phoneNumber;

    private String address;

    @OneToMany(mappedBy = "employees",cascade = CascadeType.ALL)
    private List<PatientHistoryEntity> entities = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private ERole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
