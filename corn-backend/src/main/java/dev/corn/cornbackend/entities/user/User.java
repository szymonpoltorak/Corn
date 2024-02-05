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
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;

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
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) object;
        return getUserId() == user.getUserId();
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
