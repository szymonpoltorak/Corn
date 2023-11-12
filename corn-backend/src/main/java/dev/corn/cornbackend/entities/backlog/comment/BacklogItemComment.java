package dev.corn.cornbackend.entities.backlog.comment;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
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

    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
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
