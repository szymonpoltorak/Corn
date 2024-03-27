package dev.corn.cornbackend.api.backlog.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponseList;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentService;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentMapper;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentRepository;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.backlog.item.comment.BacklogItemCommentNotFoundException;
import dev.corn.cornbackend.utils.exceptions.utils.WrongPageNumberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public static final int COMMENTS_PAGE_SIZE = 10;

    @Override
    public final BacklogItemCommentResponse addNewComment(BacklogItemCommentRequest request, User user) {
        log.info("Getting backlogItem of id: {}", request.backlogItemId());

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(request.backlogItemId(), user)
                .orElseThrow(() -> new BacklogItemNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info("Building and saving new backlogItemComment for request: {} and user: {}", request, user);

        LocalDateTime currentTime = LocalDateTime.now();

        BacklogItemComment comment = BacklogItemComment
                .builder()
                .comment(request.comment())
                .backlogItem(item)
                .commentDate(currentTime)
                .lastEditTime(currentTime)
                .user(user)
                .build();

        BacklogItemComment savedComment = backlogItemCommentRepository.save(comment);

        log.info(RETURNING_RESPONSE_FOR, savedComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(savedComment);
    }

    @Override
    public final BacklogItemCommentResponse updateComment(long commentId, String comment, User user) {
        log.info(GETTING_BACKLOG_ITEM_COMMENT_OF_ID, commentId);

        BacklogItemComment backlogItemComment = backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(commentId, user)
                .orElseThrow(() -> new BacklogItemCommentNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        backlogItemComment.setComment(comment);
        backlogItemComment.setLastEditTime(LocalDateTime.now());

        BacklogItemComment savedComment = backlogItemCommentRepository.save(backlogItemComment);

        log.info(RETURNING_RESPONSE_FOR, savedComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(savedComment);
    }

    @Override
    public final BacklogItemCommentResponse deleteComment(long commentId, User user) {
        log.info(GETTING_BACKLOG_ITEM_COMMENT_OF_ID, commentId);

        BacklogItemComment backlogItemComment = backlogItemCommentRepository.findByIdWithUserOrOwner(commentId, user)
                .orElseThrow(() -> new BacklogItemCommentNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info("Deleting backlogItemComment of id: {}", commentId);

        backlogItemCommentRepository.delete(backlogItemComment);

        log.info(RETURNING_RESPONSE_FOR, backlogItemComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(backlogItemComment);
    }

    @Override
    public final BacklogItemCommentResponse getComment(long commentId, User user) {
        log.info(GETTING_BACKLOG_ITEM_COMMENT_OF_ID, commentId);

        BacklogItemComment backlogItemComment = backlogItemCommentRepository.findByIdWithProjectMember(commentId, user)
                .orElseThrow(() -> new BacklogItemCommentNotFoundException(BACKLOG_ITEM_NOT_FOUND_MESSAGE));

        log.info(RETURNING_RESPONSE_FOR, backlogItemComment);

        return backLogItemCommentMapper.toBacklogItemCommentResponse(backlogItemComment);
    }

    @Override
    public BacklogItemCommentResponseList getCommentsForBacklogItem(long backlogItemId, int pageNumber, User user) {
        if(pageNumber < 0) {
            throw new WrongPageNumberException(pageNumber);
        }
        log.info("Getting comments for itemId: {}, pageNumber: {}", backlogItemId, pageNumber);

        log.info("Getting backlogItem for id: {}", backlogItemId);

        BacklogItem item = backlogItemRepository.findByIdWithProjectMember(backlogItemId, user)
                .orElseThrow(() -> new BacklogItemNotFoundException(
                        String.format("BacklogItem of id: %d has not been found", backlogItemId)));

        log.info("Getting backlogItemComments for page: {}", pageNumber);

        Pageable pageable = PageRequest.of(pageNumber, COMMENTS_PAGE_SIZE);

        Page<BacklogItemComment> comments = backlogItemCommentRepository.getAllByBacklogItemOrderByCommentDateDesc(
                item, pageable);

        log.info("Returning BacklogItemCommentList of size: {}", comments.getNumberOfElements());

        return BacklogItemCommentResponseList.builder()
                .comments(comments.stream()
                        .map(backLogItemCommentMapper::toBacklogItemCommentResponse)
                        .toList())
                .totalNumber(comments.getTotalElements())
                .build();
    }
}
