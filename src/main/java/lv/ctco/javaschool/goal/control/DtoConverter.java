package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.TagDto;
import lv.ctco.javaschool.goal.entity.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {
    public static GoalDto convertGoalToGoalDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setUsername(goal.getUser().getUsername());
        dto.setGoalMessage(goal.getGoalMessage());
        dto.setDeadlineDate(DateTimeConverter.convertDate(goal.getDeadlineDate()));
        dto.setRegisteredDate(DateTimeConverter.convertDateTime(goal.getRegisteredDate()));
        dto.setDaysLeft(DateTimeConverter.countDaysLeft(goal.getDeadlineDate()));
        dto.setId(goal.getId());
        dto.setTags(goal.getTags());
        return dto;
    }

    public static UserLoginDto convertUserToUserLoginDto(User user) {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static CommentDto convertCommentToCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setUsername(comment.getUser().getUsername());
        dto.setRegisteredDate(comment.getRegisteredDate().format(DateTimeConverter.FORMATTER_DATE_TIME));
        dto.setCommentMessage(comment.getCommentMessage());
        return dto;
    }

    public static TagDto convertTagToTagDto(Tag tag) {
        return new TagDto(tag.getTagMessage());
    }

    public static UserDto convertToUserDto(User user, List<Goal> goalList) {
        List<GoalDto> goalDtoList = goalList.stream()
                .map(DtoConverter::convertGoalToGoalDto)
                .collect(Collectors.toList());
        return new UserDto(user.getId(), user.getEmail(), user.getPhone(), user.getUsername(), goalDtoList);
    }
}
