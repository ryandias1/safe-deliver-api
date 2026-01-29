package br.com.ryan.safe_deliver.entity;

import br.com.ryan.safe_deliver.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Role role;

    @OneToOne
    @JoinColumn(name = "hospital_id")
    @JsonBackReference
    private Hospital hospital;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role.equals(Role.HOSPITAL_MANAGER)) return List.of(new SimpleGrantedAuthority("ROLE_HOSPITAL_MANAGER"));
        if (this.role.equals(Role.DRIVER)) return List.of(new SimpleGrantedAuthority("ROLE_DRIVER"));
        if (this.role.equals(Role.SYS_ADMIN)) return List.of(new SimpleGrantedAuthority("ROLE_SYS_ADMIN"));
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
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
