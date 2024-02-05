package dev.corn.cornbackend.entities.project;

import dev.corn.cornbackend.entities.project.constants.ProjectConstants;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import dev.corn.cornbackend.utils.validators.interfaces.NoNullElements;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectId;

    @Column(unique = true)
    @NotBlank(message = ProjectConstants.PROJECT_NAME_BLANK_MSG)
    @Size(max = ProjectConstants.PROJECT_NAME_MAX_SIZE,
            message = ProjectConstants.PROJECT_NAME_WRONG_SIZE_MSG)
    private String name;

    @OneToMany
    @ToString.Exclude
    @NoNullElements(message = ProjectConstants.PROJECT_SPRINTS_NULL_ELEMENTS_MSG)
    private List<Sprint> sprints;

    @ManyToOne
    @NotNull(message = ProjectConstants.PROJECT_OWNER_NULL_MSG)
    private User owner;

    @Override
    public final String toJson() {
        return JsonMapper.toJson(this);
    }

    @Override
    public final String toPrettyJson() {
        return JsonMapper.toPrettyJson(this);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Project project = (Project) object;
        return getProjectId() == project.getProjectId();
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
