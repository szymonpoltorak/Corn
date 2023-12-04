package dev.corn.cornbackend.entities.user;

import dev.corn.cornbackend.entities.user.constants.UserConstants;
import dev.corn.cornbackend.entities.user.interfaces.ServiceUser;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Jsonable, ServiceUser {
    @Serial
    private static final long serialVersionUID = 1236185595152412287L;
    private static final String DEV_CORN_CORNBACKEND_ENTITIES_USER_USER = "dev.corn.cornbackend.entities.user.User";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @NotBlank(message = UserConstants.USER_NAME_BLANK_MSG)
    @Size(max = UserConstants.USER_NAME_MAX_SIZE,
            message = UserConstants.USER_NAME_WRONG_SIZE_MSG)
    private String name;

    @NotBlank(message = UserConstants.USER_SURNAME_BLANK_MSG)
    @Size(max = UserConstants.USER_SURNAME_MAX_SIZE,
            message = UserConstants.USER_SURNAME_WRONG_SIZE_MSG)
    private String surname;

    @NotBlank(message = UserConstants.USER_USERNAME_BLANK_MSG)
    @Size(max = UserConstants.USER_USERNAME_MAX_SIZE,
            message = UserConstants.USER_USERNAME_WRONG_SIZE_MSG)
    private String username;

    @Setter
    @Builder.Default
    private transient Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return Objects.equals(username, user.username);
    }

    @Override
    public final int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);

        return result;
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws ClassNotFoundException, java.io.NotSerializableException {
        throw new java.io.NotSerializableException(DEV_CORN_CORNBACKEND_ENTITIES_USER_USER);
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.NotSerializableException {
        throw new java.io.NotSerializableException(DEV_CORN_CORNBACKEND_ENTITIES_USER_USER);
    }
}
