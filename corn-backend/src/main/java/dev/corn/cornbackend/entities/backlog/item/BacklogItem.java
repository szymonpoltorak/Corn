package dev.corn.cornbackend.entities.backlog.item;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemType;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import dev.corn.cornbackend.utils.validators.interfaces.NoNullElements;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class BacklogItem implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long backlogItemId;

    @NotBlank(message = BacklogItemConstants.BACKLOG_ITEM_TITLE_BLANK_MSG)
    @Size(max = BacklogItemConstants.BACKLOG_ITEM_TITLE_MAX_SIZE,
            message = BacklogItemConstants.BACKLOG_ITEM_TITLE_WRONG_SIZE_MSG)
    private String title;

    @NotBlank(message = BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_BLANK_MSG)
    @Size(max = BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_MAX_SIZE,
            message = BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_WRONG_SIZE_MSG)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = BacklogItemConstants.BACKLOG_ITEM_STATUS_NULL_MSG)
    private ItemStatus status;

    @OneToMany
    @ToString.Exclude
    @NoNullElements(message = BacklogItemConstants.BACKLOG_ITEM_COMMENTS_NULL_ELEMENTS_MSG)
    private List<BacklogItemComment> comments;

    @ManyToOne
    @NotNull(message = BacklogItemConstants.BACKLOG_ITEM_ASSIGNEE_NULL_MSG)
    private ProjectMember assignee;

    @ManyToOne
    @NotNull(message = BacklogItemConstants.BACKLOG_ITEM_SPRINT_NULL_MSG)
    private Sprint sprint;

    @ManyToOne
    @NotNull(message = BacklogItemConstants.BACKLOG_ITEM_PROJECT_NULL_MSG)
    private Project project;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = BacklogItemConstants.BACKLOG_ITEM_TYPE_NULL_MSG)
    private ItemType itemType;

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
        if (this == object) {
            return true;
        }
        if (!(object instanceof BacklogItem)) {
            return false;
        }

        Class<?> oEffectiveClass = object instanceof HibernateProxy hibernateProxy ? hibernateProxy
                .getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy
                .getHibernateLazyInitializer().getPersistentClass() : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        BacklogItem that = (BacklogItem) object;

        return backlogItemId == that.backlogItemId;
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
