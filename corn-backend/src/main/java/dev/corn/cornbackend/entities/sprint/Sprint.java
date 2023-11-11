package dev.corn.cornbackend.entities.sprint;

import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sprint implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sprintId;

    private String sprintName;

    private String sprintDescription;

    private LocalDate sprintStartDate;

    private LocalDate sprintEndDate;

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Sprint sprint)) {
            return false;
        }
        if (sprintId != sprint.sprintId || !Objects.equals(sprintName, sprint.sprintName)) {
            return false;
        }
        if (!Objects.equals(sprintDescription, sprint.sprintDescription)) {
            return false;
        }
        if (!Objects.equals(sprintStartDate, sprint.sprintStartDate)) {
            return false;
        }
        return Objects.equals(sprintEndDate, sprint.sprintEndDate);
    }

    @Override
    public final int hashCode() {
        int result = (int) (sprintId ^ (sprintId >>> 32));

        result = 31 * result + (sprintName != null ? sprintName.hashCode() : 0);
        result = 31 * result + (sprintDescription != null ? sprintDescription.hashCode() : 0);
        result = 31 * result + (sprintStartDate != null ? sprintStartDate.hashCode() : 0);
        result = 31 * result + (sprintEndDate != null ? sprintEndDate.hashCode() : 0);

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
