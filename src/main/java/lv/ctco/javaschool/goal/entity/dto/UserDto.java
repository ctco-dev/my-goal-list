package lv.ctco.javaschool.goal.entity.dto;


import java.util.List;

public class UserDto {
    private Long id;
    private String email = "undefined";
    private String phone = "undefined";
    private String username = "undefined";
    private List<GoalDto> goalList;

    public UserDto() {
    }

    public UserDto(Long id, String email, String phone, String username, List<GoalDto> goalList) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.goalList = goalList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GoalDto> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<GoalDto> goalList) {
        this.goalList = goalList;
    }
}
