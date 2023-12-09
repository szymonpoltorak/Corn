package dev.corn.cornbackend.api.backlog.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentService;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentMapper;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentRepository;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.backlog.item.comment.BacklogItemCommentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BacklogItemCommentServiceImpl implements BacklogItemCommentService {

    private final BacklogItemCommentRepository backlogItemCommentRepository;

    private final BacklogItemRepository backlogItemRepository;

    private final BacklogItemCommentMapper backLogItemCommentMapper;

    private static final String BACKLOG_ITEM_NOT_FOUND_MESSAGE = "Backlog item not found";

    private static final String RETURNING_RESPONSE_FOR = "Returning response for: {}";

    private static final String GETTING_BACKLOG_ITEM_COMMENT_OF_ID = "Getting backlogItemComment of id: {}";

    @Override
    public final BacklogItemCommentResponse addNewComment(BacklogItemCommentRequest request, User user) {
        log.info("Getting backlogItem of id: {}", request.backlogItemId());

        BacklogItem item = backlogItemRepository.findById(request.backlogItemId())
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info("Building and saving new backlogItemComment for request: {} and user: {}", request, user);

        BacklogItemComment comment = BacklogItemComment
                .builder()
                .comment(request.comment())
                .backlogItem(item)
                .user(user)
                .build();

        BacklogItemComment savedComment = backlogItemCommentRepository.save(comment);

        log.info(RETURNING_RESPONSE_FOR, savedComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(savedComment);
    }

    @Override
    public final BacklogItemCommentResponse updateComment(long commentId, String comment) {
        log.info(GETTING_BACKLOG_ITEM_COMMENT_OF_ID, commentId);

        BacklogItemComment backlogItemComment = backlogItemCommentRepository.findById(commentId)
                .orElseThrow(() -> new BacklogItemCommentNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        backlogItemComment.setComment(comment);

        BacklogItemComment savedComment = backlogItemCommentRepository.save(backlogItemComment);

        log.info(RETURNING_RESPONSE_FOR, savedComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(savedComment);
    }

    @Override
    public final BacklogItemCommentResponse deleteComment(long commentId) {
        log.info(GETTING_BACKLOG_ITEM_COMMENT_OF_ID, commentId);

        BacklogItemComment backlogItemComment = backlogItemCommentRepository.findById(commentId)
                .orElseThrow(() -> new BacklogItemCommentNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info("Deleting backlogItemComment of id: {}", commentId);

        backlogItemCommentRepository.delete(backlogItemComment);

        log.info(RETURNING_RESPONSE_FOR, backlogItemComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(backlogItemComment);
    }

    @Override
    public final BacklogItemCommentResponse getComment(long commentId) {
        log.info(GETTING_BACKLOG_ITEM_COMMENT_OF_ID, commentId);

        BacklogItemComment backlogItemComment = backlogItemCommentRepository.findById(commentId)
                .orElseThrow(() -> new BacklogItemCommentNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_FOR, backlogItemComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(backlogItemComment);
    }
}
