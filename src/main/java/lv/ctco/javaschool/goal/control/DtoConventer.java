package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;

public class DtoConventer {
    public static GoalDto convertGoalToGoalDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setUsername(goal.getUser().getUsername());
        dto.setGoalMessage(goal.getGoalMessage());
        dto.setDeadlineDate(DateTimeConverter.convertDate(goal.getDeadlineDate()));
        dto.setRegisteredDate(DateTimeConverter.convertDateTime(goal.getRegisteredDate()));
        dto.setDaysLeft(DateTimeConverter.countDaysLeft(goal.getDeadlineDate()));
        dto.setId(goal.getId());
        return dto;
    }

    public static UserLoginDto convertUserToUserLoginDto(User user) {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        return dto;
    }

}
