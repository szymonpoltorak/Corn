package dev.corn.cornbackend.api.backlog.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentController;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentService;
import dev.corn.cornbackend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static dev.corn.cornbackend.api.backlog.comment.constants.BacklogItemCommentMappings.BACKLOG_ITEM_COMMENT_ADD_MAPPING;
import static dev.corn.cornbackend.api.backlog.comment.constants.BacklogItemCommentMappings.BACKLOG_ITEM_COMMENT_API_MAPPING;
import static dev.corn.cornbackend.api.backlog.comment.constants.BacklogItemCommentMappings.BACKLOG_ITEM_COMMENT_DELETE_MAPPING;
import static dev.corn.cornbackend.api.backlog.comment.constants.BacklogItemCommentMappings.BACKLOG_ITEM_COMMENT_GET_MAPPING;
import static dev.corn.cornbackend.api.backlog.comment.constants.BacklogItemCommentMappings.BACKLOG_ITEM_COMMENT_UPDATE_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(BACKLOG_ITEM_COMMENT_API_MAPPING)
public class BacklogItemCommentControllerImpl implements BacklogItemCommentController {

    private final BacklogItemCommentService backlogItemCommentService;

    @Override
    @PostMapping(BACKLOG_ITEM_COMMENT_ADD_MAPPING)
    public final BacklogItemCommentResponse addNewComment(
            @RequestBody BacklogItemCommentRequest request,
            @AuthenticationPrincipal User user) {
        return backlogItemCommentService.addNewComment(request, user);
    }

    @Override
    @PutMapping(BACKLOG_ITEM_COMMENT_UPDATE_MAPPING)
    public final BacklogItemCommentResponse updateComment(
            @RequestParam long commentId,
            @RequestBody String comment) {
        return backlogItemCommentService.updateComment(commentId, comment);
    }

    @Override
    @DeleteMapping(BACKLOG_ITEM_COMMENT_DELETE_MAPPING)
    public final BacklogItemCommentResponse deleteComment(@RequestParam long commentId) {
        return backlogItemCommentService.deleteComment(commentId);
    }

    @Override
    @GetMapping(BACKLOG_ITEM_COMMENT_GET_MAPPING)
    public final BacklogItemCommentResponse getComment(@RequestParam long commentId) {
        return backlogItemCommentService.getComment(commentId);
    }


}
