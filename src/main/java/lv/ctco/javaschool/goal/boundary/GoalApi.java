package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserSearchDto;
import lv.ctco.javaschool.goal.control.DateTimeConverter;
import lv.ctco.javaschool.goal.control.DtoConverter;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.control.TagParser;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.*;
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;
import lv.ctco.javaschool.goal.entity.dto.UserDto;
import lv.ctco.javaschool.goal.entity.exception.InvalidUserException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/goal")
@RolesAllowed({"ADMIN", "USER"})
@Stateless
public class GoalApi {
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;
    @Inject
    private TagParser tagParser;

    @GET
    @Path("/mygoals")
    public List<GoalDto> getMyGoals() {
        User currentUser = userStore.getCurrentUser();
        List<Goal> goalsList = goalStore.getGoalsListFor(currentUser);
        return goalsList.stream()
                .map(DtoConverter::convertGoalToGoalDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/mygoals/{id}")
    public GoalDto getGoalDtoByGoalId(@PathParam("id") Long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            Goal g = goal.get();
            return DtoConverter.convertGoalToGoalDto(g);
        } else {
            throw new InvalidGoalException();
        }
    }

    @POST
    @Path("/newgoal")
    public void createNewGoal(GoalFormDto goalDto) {
        User user = userStore.getCurrentUser();
        Goal goal = new Goal();
        if (goalDto.getGoalMessage() != null && goalDto.getDeadline() != null) {
            goal.setGoalMessage(goalDto.getGoalMessage());
            List<Tag> tags = tagParser.parseStringToTags(goalDto.getTags());
            Set<Tag> tagSet = goalStore.checkIfTagExistsOrPersist(tags);
            goal.setTags(tagSet);

            LocalDate localDate = LocalDate.parse(goalDto.getDeadline(), DateTimeConverter.FORMATTER_DATE);
            goal.setDeadlineDate(localDate);

            goal.setUser(user);
            goal.setRegisteredDate(LocalDateTime.now());
            goalStore.addGoal(goal);
        } else {
            throw new InvalidGoalException();
        }
    }

    @GET
    @Path("{id}/comments")
    public List<CommentDto> returnAllCommentsForGoalById(@PathParam("id") Long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            List<Comment> comments = goalStore.getCommentsForGoal(goal.get());
            return comments.stream()
                    .map(DtoConverter::convertCommentToCommentDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @POST
    @Path("{id}/comments")
    public void saveNewCommentsForGoalById(@PathParam("id") Long goalId, MessageDto msg) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            Comment comment = new Comment();
            comment.setUser(userStore.getCurrentUser());
            comment.setGoal(goal.get());
            comment.setRegisteredDate(LocalDateTime.now());
            comment.setCommentMessage(msg.getMessage());
            goalStore.addComment(comment);
        } else {
            throw new InvalidGoalException();
        }
    }

    @GET
    @Path("/tags")
    public List<TagDto> returnAllTags() {
        List<Tag> tagList = goalStore.getAllTagList();
        return tagList.stream()
                .map(DtoConverter::convertTagToTagDto)
                .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-goals")
    public List<GoalDto> getGoalsByTag(JsonObject searchDto) {
        List<GoalDto> goalDtoList = new ArrayList<>();
        for (Map.Entry<String, JsonValue> pair : searchDto.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr.equals("goalsearch")) {
                List<Tag> tagList = goalStore.getTagsByMessage(value);
                for (Tag tag : tagList) {
                    List<Goal> goalList = goalStore.getGoalsByTag(tag);
                    if (goalList.size() != 0) {
                        goalDtoList = goalList.stream()
                                .map(DtoConverter::convertGoalToGoalDto)
                                .collect(Collectors.toList());
                    } else {
                        goalDtoList = Collections.emptyList();
                    }
                }
            }
        }
        return goalDtoList;
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-user")
    public List<UserSearchDto> getUsersByUsername(JsonObject searchDto) {
        List<UserSearchDto> userDtoList = new ArrayList<>();
        for (Map.Entry<String, JsonValue> pair : searchDto.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr.equals("usersearch")) {
                List<User> userList = userStore.getUserByUsername(value);
                if (userList.size() != 0) {
                    userDtoList = userList.stream()
                            .map(DtoConverter::convertUserToUserSearchDto)
                            .collect(Collectors.toList());
                } else {
                    userDtoList = Collections.emptyList();
                }
            }
        }
        return userDtoList;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/user/{id}")
    public UserDto getUserById(@PathParam("id") Long id) {
        Optional<User> user = userStore.findUserById(id);
        if (user.isPresent()) {
            User u = user.get();
            List<Goal> goalList = goalStore.getGoalsListFor(u);
            UserDto userDto = DtoConverter.convertToUserDto(u, goalList);
            return userDto;
        } else {
            throw new InvalidUserException();
        }
    }
}

