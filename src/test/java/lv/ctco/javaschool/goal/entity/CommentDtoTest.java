package lv.ctco.javaschool.goal.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by olga.polinkina01 on 8/22/2018.
 */
class CommentDtoTest {
    @Test
    @DisplayName("UserName should be equal")
    void getAndSetUsername() {
        String userName = "test";
        CommentDto dto = new CommentDto();
        dto.setUsername(userName);
        assertEquals(userName, dto.getUsername());
    }

    @Test
    @DisplayName("TagMessages should be equal")
    void getAndSetRegisteredDate() {
        String newMsg="test tag";
        CommentDto dto = new CommentDto();
        dto.setCommentMessage(newMsg);
        assertEquals(newMsg, dto.getCommentMessage());
    }

    @Test
    @DisplayName("RegisteredDate should be equal")
    void getAndSetCommentMessage() {
        LocalDateTime newDt = LocalDateTime.now();
        CommentDto dto = new CommentDto();
        dto.setRegisteredDate(newDt);
        assertEquals(newDt, dto.getRegisteredDate() );
    }

}