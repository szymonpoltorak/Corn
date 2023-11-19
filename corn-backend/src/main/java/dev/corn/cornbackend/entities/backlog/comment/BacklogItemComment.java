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

import java.util.Objects;

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
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BacklogItemComment that) || backlogItemCommentId != that.backlogItemCommentId) {
            return false;
        }
        if (!Objects.equals(comment, that.comment) || !Objects.equals(user, that.user)) {
            return false;
        }
        return Objects.equals(backlogItem, that.backlogItem);
    }

    @Override
    public final int hashCode() {
        int result = (int) (backlogItemCommentId ^ (backlogItemCommentId >>> 32));

        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (backlogItem != null ? backlogItem.hashCode() : 0);

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
