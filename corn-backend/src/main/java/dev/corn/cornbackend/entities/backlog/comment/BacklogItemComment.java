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

    @NotBlank(message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BLANK_COMMENT_MSG)
    @Size(max = 500, message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_WRONG_SIZE_COMMENT_MSG)
    private String comment;

    @ManyToOne
    @NotNull(message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_NULL_USER_MSG)
    private User user;

    @ManyToOne
    @NotNull(message = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_NULL_BACK_LOG_ITEM_MSG)
    private BacklogItem backlogItem;

    @Override
    public final String toJson() {
        return JsonMapper.toJson(this);
    }

    @Override
    public final String toPrettyJson() {
        return JsonMapper.toPrettyJson(this);
    }
}
