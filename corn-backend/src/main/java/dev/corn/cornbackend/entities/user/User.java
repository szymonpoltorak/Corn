package dev.corn.cornbackend.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.corn.cornbackend.entities.user.interfaces.ServiceUser;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Jsonable, ServiceUser {
    @Serial
    private static final long serialVersionUID = 1236185595152412287L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String name;

    private String surname;

    private String username;

    @ToString.Exclude
    @JsonIgnore
    private String password;

    @ToString.Exclude
    @JsonIgnore
    private String salt;

    @Override
    public final String toJson() {
        return JsonMapper.toJson(this);
    }

    @Override
    public final String toPrettyJson() {
        return JsonMapper.toPrettyJson(this);
    }

    @Override
    public final String getFullName() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof User user) || userId != user.userId) {
            return false;
        }
        if (!Objects.equals(name, user.name) || !Objects.equals(surname, user.surname)) {
            return false;
        }
        if (!Objects.equals(username, user.username) || !Objects.equals(password, user.password)) {
            return false;
        }
        return Objects.equals(salt, user.salt);
    }

    @Override
    public final int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);

        return result;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws ClassNotFoundException, java.io.NotSerializableException {
        throw new java.io.NotSerializableException("dev.corn.cornbackend.entities.user.User");
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.NotSerializableException {
        throw new java.io.NotSerializableException("dev.corn.cornbackend.entities.user.User");
    }
}
