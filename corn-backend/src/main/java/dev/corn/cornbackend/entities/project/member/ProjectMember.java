package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.constants.ProjectMemberConstants;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.validators.interfaces.NoNullElements;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
public class ProjectMember implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectMemberId;

    @OneToMany
    @ToString.Exclude
    @NoNullElements(message = ProjectMemberConstants.PROJECT_MEMBER_BACKLOG_ITEM_NULL_ELEMENTS_MSG)
    private List<BacklogItem> backlogItems;

    @ManyToOne
    @NotNull(message = ProjectMemberConstants.PROJECT_MEMBER_PROJECT_NULL_MSG)
    private Project project;

    @ManyToOne
    @NotNull(message = ProjectMemberConstants.PROJECT_MEMBER_USER_NULL_MSG)
    private User user;

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProjectMember member)) {
            return false;
        }
        if (projectMemberId != member.projectMemberId || !Objects.equals(backlogItems, member.backlogItems)) {
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

        result = 31 * result + (backlogItems != null ? backlogItems.hashCode() : 0);
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
