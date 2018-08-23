package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
    void getAndSetGoal() {
        Goal goal = new Goal();
        goal.setId(123456789L);
        goal.setUser(new User());
        goal.setGoalMessage("test");
        goal.setRegisteredDate( LocalDateTime.now());
        goal.setDeadlineDate( LocalDate.now());
        Comment comment = new Comment();
        comment.setGoal(goal);
        assertEquals(goal, comment.getGoal());
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