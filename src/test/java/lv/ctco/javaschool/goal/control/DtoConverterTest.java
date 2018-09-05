package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.auth.entity.dto.UserSearchDto;
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

class DtoConverterTest {

    private Goal goal;
    private User user;
    private Comment comment;
    private Tag tag;

    @BeforeEach
    void init() {
        goal = new Goal();
        user = new User();
        comment = new Comment();
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
        UserLoginDto dto = DtoConverter.convertUserToUserLoginDto(user);
        assertThat(dto.getUsername(), is(user.getUsername()));
        assertThat(dto.getEmail(), is(user.getEmail()));
        assertThat(dto.getPhone(), is(user.getPhone()));
    }

    @Test
    @DisplayName("Test convertGoalToGoalDto(Goal goal): Checks that goal and goalDto contains same data (List<Tag> excluded)")
    void testConvertGoalToGoalDto() {
        GoalDto dto = DtoConverter.convertGoalToGoalDto(goal);
        assertThat(dto.getUsername(), is(goal.getUser().getUsername()));
        assertThat(dto.getGoalMessage(), is(goal.getGoalMessage()));
        assertThat(dto.getDeadlineDate(), is(goal.getDeadlineDate()));
        assertThat(dto.getId(), is(goal.getId()));
        assertThat(dto.getRegisteredDate(), is(goal.getRegisteredDate()));
        assertThat(dto.getRegisteredDate(), is(goal.getRegisteredDate()));
        assertThat(dto.getRegisteredDate(), is(goal.getRegisteredDate()));
        assertThat(dto.getTags(), nullValue());
    }

    @Test
    @DisplayName("Test ConvertCommentToCommentDto(Comment comment): Checks that Comment and CommentDto contains same data")
    void testConvertCommentToCommentDto() {
        CommentDto dto = DtoConverter.convertCommentToCommentDto(comment);
        assertThat(dto.getUsername(), is(comment.getUser().getUsername()));
        assertThat(dto.getCommentMessage(), is(comment.getCommentMessage()));
        assertThat(dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(comment.getRegisteredDate())));
    }

    @Test
    @DisplayName("Test ConvertTagToTagDto(Tag tag): Checks that Tag and TagDto contain the same data")
    void testConvertTagToTagDto() {
        TagDto tagDto = DtoConverter.convertTagToTagDto(tag);
        assertThat(tagDto.getTagMessage(), is(tag.getTagMessage()));
    }

    @Test
    @DisplayName("Test ConvertUserToUserSearchDto(User user): Checks that User and UserSearchDto contain the same data")
    void testConvertUserToUserSearchDto() {
        UserSearchDto userSearchDto = DtoConverter.convertUserToUserSearchDto(user);
        assertThat(userSearchDto.getId(), is(user.getId()));
        assertThat(userSearchDto.getUsername(), is(user.getUsername()));
        assertThat(userSearchDto.getEmail(), is(user.getEmail()));
        assertThat(userSearchDto.getPhone(), is(user.getPhone()));
    }
}