package dev.corn.cornbackend.entities.project;

import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

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

    private String name;

    @OneToMany
    @ToString.Exclude
    private List<Sprint> sprint;

    @ManyToOne
    private User owner;

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Project project)) {
            return false;
        }
        if (projectId != project.projectId || !Objects.equals(name, project.name)) {
            return false;
        }
        return Objects.equals(sprint, project.sprint);
    }

    @Override
    public final int hashCode() {
        int result = (int) (projectId ^ (projectId >>> 32));

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sprint != null ? sprint.hashCode() : 0);

        return result;
    }

    @Override
    public final String toJson() {
        return JsonMapper.toJson(this);
    }

    @Override
    public final String toPrettyJson() {
        return JsonMapper.toPrettyJson(this);
    }
}
