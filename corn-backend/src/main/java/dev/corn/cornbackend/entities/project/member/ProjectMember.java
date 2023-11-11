package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectMemberId;

    @ManyToOne
    private BacklogItem backlogItem;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProjectMember member)) {
            return false;
        }
        if (projectMemberId != member.projectMemberId || !Objects.equals(backlogItem, member.backlogItem)) {
            return false;
        }
        if (!Objects.equals(project, member.project)) {
            return false;
        }
        return Objects.equals(user, member.user);
    }

    @Override
    public final int hashCode() {
        int result = (int) (projectMemberId ^ (projectMemberId >>> 32));

        result = 31 * result + (backlogItem != null ? backlogItem.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);

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
