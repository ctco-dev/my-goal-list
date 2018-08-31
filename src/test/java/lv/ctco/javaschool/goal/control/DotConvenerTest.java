package lv.ctco.javaschool.goal.control;


import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class DotConvenerTest {
    Goal goal = new Goal();
    User user = new User();
    Comment comment = new Comment();
    Tag tag;

    @BeforeEach
    void init() {
        user.setUsername("user");
        user.setEmail("user@user.com");
        user.setId(1L);
        user.setPassword("12345");
        user.setPhone("12345678");

        goal.setUser(user);
        goal.setGoalMessage("abc");
        goal.setRegisteredDate(LocalDateTime.now());
        goal.setDeadlineDate(LocalDate.now().plusDays(1));
        goal.setId(1L);
        goal.setTags(null);

        comment.setId(1L);
        comment.setGoal(goal);
        comment.setUser(user);
        comment.setCommentMessage("hi");
        comment.setRegisteredDate(LocalDateTime.now());

        tag = new Tag("new tag");
    }

    @Test
    @DisplayName("Test ConvertUserToUserLoginDto(User user): Checks that User and UserDto contains same data")
    void testConvertUserToUserLoginDto() {
        UserLoginDto dto = DtoConvener.convertUserToUserLoginDto(user);
        assertThat( dto.getUsername(), is(user.getUsername()));
        assertThat( dto.getEmail(), is(user.getEmail()));
        assertThat( dto.getPhone(), is(user.getPhone()));
    }


    @Test
    @DisplayName("Test convertGoalToGoalDto(Goal goal): Checks that goal and goalDto contains same data (List<Tag> excluded)")
    void testConvertGoalToGoalDto() {
        GoalDto dto = DtoConvener.convertGoalToGoalDto(goal);
        assertThat( dto.getUsername(), is(goal.getUser().getUsername()));
        assertThat( dto.getGoalMessage(), is(goal.getGoalMessage()));
        assertThat( dto.getDeadlineDate(), is(DateTimeConverter.convertDate(goal.getDeadlineDate())));
        assertThat( dto.getId(), is(goal.getId()));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat(dto.getTags(), nullValue());
    }


    @Test
    @DisplayName("Test ConvertCommentToCommentDto(Comment comment): Checks that Comment and CommentDto contains same data")
    void testConvertCommentToCommentDto() {
        CommentDto dto = DtoConvener.convertCommentToCommentDto(comment);

        assertThat( dto.getUsername(), is(comment.getUser().getUsername()));
        assertThat( dto.getCommentMessage(), is(comment.getCommentMessage()));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(comment.getRegisteredDate())));
    }

    @Test
    @DisplayName("Test convertTagToTagDtoWithoutCnt(Tag tag): Checks that Tag and Tag contains same data (except count)")
    void testConvertTagToTagDtoWithoutCnt() {
        TagDto dto = DtoConvener.convertTagToTagDtoWithoutCnt(tag);
        assertThat(dto.getTagMessage(), is(tag.getTagMessage()));
        assertThat(dto.getCnt(), is(0));
    }

}