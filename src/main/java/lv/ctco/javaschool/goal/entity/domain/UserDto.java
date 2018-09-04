package lv.ctco.javaschool.goal.entity.domain;

import lv.ctco.javaschool.goal.entity.dto.GoalDto;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private List<GoalDto> goalDtoList;
}
