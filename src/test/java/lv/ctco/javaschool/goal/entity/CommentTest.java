package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {
    @Test
    @DisplayName("Ids should be equal")
    void getAndSetId() {
        Long l=123456789L;
        Comment comment = new Comment();
        comment.setId(l);
        assertEquals(l, comment.getId());
    }

    @Test
    @DisplayName("Users should be equal")
    void getAndSetUser() {
        User user = new User();
        user.setId(123456789L);
        user.setPassword("12346");
        user.setEmail("qqq@qqq.qqq");
        user.setPhone("+37123456789");
        Comment comment = new Comment();
        comment.setUser(user);
        assertEquals(user, comment.getUser());
    }


    @Test
    @DisplayName("RegisteredDate should be equal")
    void getAndSetRegisteredDate() {
        LocalDateTime newDt = LocalDateTime.now();
        Comment comment = new Comment();
        comment.setRegisteredDate(newDt);
        assertEquals(newDt, comment.getRegisteredDate() );
    }

    @Test
    @DisplayName("CommentMessage should be equal")
    void getAndSetCommentMessage() {
        String newMsg="test comment";
        Comment comment = new Comment();
        comment.setCommentMessage(newMsg);
        assertEquals(newMsg, comment.getCommentMessage());
    }

}