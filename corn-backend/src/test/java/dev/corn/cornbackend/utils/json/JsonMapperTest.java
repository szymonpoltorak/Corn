package dev.corn.cornbackend.utils.json;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.utils.json.JsonMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonMapperTest {
    private static class NotSerializableJsonable implements Jsonable {
        private transient String name;

        @Override
        public final String toJson() {
            return null;
        }

        @Override
        public final String toPrettyJson() {
            return null;
        }
    }

    @Test
    final void test_toJson_shouldProperlyMapToJson() {
        // given
        Jsonable jsonable = BacklogItem
                .builder()
                .backlogItemId(0L)
                .project(Project.builder().name("name").projectId(0L).build())
                .comments(List.of(BacklogItemComment.builder().comment("comment").build()))
                .build();

        // when
        String actual = JsonMapper.toJson(jsonable);

        // then
        assertNotNull(actual, "JsonMapper.toJson should not return null");
        assertTrue(actual.contains("\"backlogItemId\":0"), "JsonMapper.toJson should map backlogItemId");
        assertTrue(actual.contains("\"comment\":\"comment\""), "JsonMapper.toJson should map comment");
    }

    @Test
    final void test_toJson_shouldReturnEmptyObjectOnNonSerializableObject() {
        // given
        Jsonable jsonable = new NotSerializableJsonable();
        String expected = "{}";

        // when
        String actual = JsonMapper.toJson(jsonable);

        // then
        assertEquals(expected, actual);
    }

    @Test
    final void test_toPrettyJson_shouldReturnPrettyJson() {
        // given
        Jsonable jsonable = BacklogItem
                .builder()
                .backlogItemId(0L)
                .project(Project.builder().name("name").projectId(0L).build())
                .comments(List.of(BacklogItemComment.builder().comment("comment").build()))
                .build();

        // when
        String actual = JsonMapper.toPrettyJson(jsonable);

        // then
        assertNotNull(actual, "JsonMapper.toPrettyJson should not return null");
        assertTrue(actual.contains("\"backlogItemId\" : 0"), "JsonMapper.toPrettyJson should map backlogItemId");
        assertTrue(actual.contains("\"comment\" : \"comment\""), "JsonMapper.toPrettyJson should map comment");
    }

    @Test
    final void test_toPrettyJson_shouldReturnEmptyObjectOnNonSerializableObject() {
        // given
        Jsonable jsonable = new NotSerializableJsonable();
        String expected = "{}";

        // when
        String actual = JsonMapper.toPrettyJson(jsonable);

        // then
        assertEquals(expected, actual,
                "JsonMapper.toPrettyJson should return empty object on non serializable object");
    }
}
