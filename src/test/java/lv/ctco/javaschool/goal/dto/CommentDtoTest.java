package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.control.DateTimeConverter;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CommentDtoTest {
    @Test
    @DisplayName("UserName should be equal")
    void getAndSetUsername() {
        String userName = "test";
        CommentDto dto = new CommentDto();
        dto.setUsername(userName);
        assertThat(dto.getUsername(), is(userName));
    }

    @Test
    @DisplayName("TagMessages should be equal")
    void getAndSetRegisteredDate() {
        String newMsg="test tag";
        CommentDto dto = new CommentDto();
        dto.setCommentMessage(newMsg);
        assertThat(dto.getCommentMessage(), is(newMsg));
    }

    @Test
    @DisplayName("RegisteredDate should be equal")
    void getAndSetCommentMessage() {
        LocalDateTime newDt = LocalDateTime.now();
        CommentDto dto = new CommentDto();
        dto.setRegisteredDate(newDt.format(DateTimeConverter.formatterDateTime));
        assertThat(dto.getRegisteredDate(), is(newDt.format(DateTimeConverter.formatterDateTime)));
    }
}