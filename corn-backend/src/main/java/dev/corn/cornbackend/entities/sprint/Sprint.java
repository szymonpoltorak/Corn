package dev.corn.cornbackend.entities.sprint;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.constants.SprintConstants;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import dev.corn.cornbackend.utils.validators.interfaces.LaterThan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@LaterThan(firstDateGetterName = SprintConstants.SPRINT_FIRST_DATE_GETTER_NAME,
        secondDateGetterName = SprintConstants.SPRINT_SECOND_DATE_GETTER_NAME,
        message = SprintConstants.SPRINT_LATER_THAN_MSG)
public class Sprint implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sprintId;

    @ManyToOne
    @NotNull(message = SprintConstants.SPRINT_PROJECT_NULL_MSG)
    private Project project;

    @NotBlank(message = SprintConstants.SPRINT_NAME_BLANK_MSG)
    @Size(max = SprintConstants.SPRINT_NAME_MAX_SIZE,
            message = SprintConstants.SPRINT_NAME_WRONG_SIZE_MSG)
    private String sprintName;

    @NotBlank(message = SprintConstants.SPRINT_DESCRIPTION_BLANK_MSG)
    @Size(max = SprintConstants.SPRINT_DESCRIPTION_MAX_SIZE,
            message = SprintConstants.SPRINT_DESCRIPTION_WRONG_SIZE_MSG)
    private String sprintDescription;

    @NotNull(message = SprintConstants.SPRINT_START_DATE_NULL_MSG)
    @FutureOrPresent(message = SprintConstants.SPRINT_START_DATE_FUTURE_OR_PRESENT_MSG)
    private LocalDate sprintStartDate;

    @NotNull(message = SprintConstants.SPRINT_END_DATE_NULL_MSG)
    @Future(message = SprintConstants.SPRINT_END_DATE_FUTURE_MSG)
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
