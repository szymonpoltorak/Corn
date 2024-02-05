package dev.corn.cornbackend.entities.backlog.comment;

import dev.corn.cornbackend.entities.backlog.comment.constants.BacklogItemCommentConstants;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BacklogItemComment implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long backlogItemCommentId;

    @NotBlank(message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_BLANK_MSG)
    @Size(max = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_MAX_SIZE,
            message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_WRONG_SIZE_MSG)
    private String comment;

    @ManyToOne
    @NotNull(message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_USER_NULL_MSG)
    private User user;

    @ManyToOne
    @NotNull(message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_NULL_MSG)
    private BacklogItem backlogItem;

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
        BacklogItemComment that = (BacklogItemComment) object;
        return getBacklogItemCommentId() == that.getBacklogItemCommentId();
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
