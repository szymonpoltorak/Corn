package dev.corn.cornbackend.entities.backlog.item;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class BacklogItem implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long backlogItemId;

    private String title;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private ItemStatus status;

    @OneToMany
    @ToString.Exclude
    private List<BacklogItemComment> comments;

    @ManyToOne
    private ProjectMember assignee;

    @ManyToOne
    private Sprint sprint;

    @ManyToOne
    private Project project;

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BacklogItem that) || backlogItemId != that.backlogItemId) {
            return false;
        }
        if (!Objects.equals(description, that.description) || !Objects.equals(title, that.title)) {
            return false;
        }
        if (!Objects.equals(comments, that.comments) || status != that.status) {
            return false;
        }
        if (!Objects.equals(sprint, that.sprint) || !Objects.equals(assignee, that.assignee)) {
            return false;
        }
        return Objects.equals(project, that.project);
    }

    @Override
    public final int hashCode() {
        int result = (int) (backlogItemId ^ (backlogItemId >>> 32));

        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (sprint != null ? sprint.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);

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
