package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.control.DtoConventer;
import lv.ctco.javaschool.auth.entity.dto.UserSearchDto;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.control.TagParser;
import lv.ctco.javaschool.goal.control.DateTimeConverter;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import lv.ctco.javaschool.goal.entity.dto.MessageDto;
import lv.ctco.javaschool.goal.entity.dto.TagDto;
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

@Path("/goal")
@Stateless
public class GoalApi {
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/mygoals")
    public List<GoalDto> getMyGoals() {
        User currentUser = userStore.getCurrentUser();
        List<Goal> goalsList = goalStore.getGoalsListFor(currentUser);
        return goalsList.stream()
                .map(DtoConventer::convertGoalToGoalDto)
                .collect(Collectors.toList());
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/mygoals/{id}")
    public GoalDto getGoalDtoByGoalId(@PathParam("id") Long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            Goal g = goal.get();
            return DtoConventer.convertGoalToGoalDto(g);
        } else {
            throw new InvalidGoalException();
        }
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/newgoal")
    public void createNewGoal(GoalFormDto goalDto) {
        User user = userStore.getCurrentUser();
        Goal goal = new Goal();
        if (!goalDto.getGoalMessage().isEmpty() && !goalDto.getDeadline().isEmpty()) {
            goal.setGoalMessage(goalDto.getGoalMessage());
            goal.setTags(parseStringToTags(goalDto.getGoalMessage()));

            LocalDate localDate = LocalDate.parse(goalDto.getDeadline(), DateTimeConverter.FORMATTER_DATE);
            goal.setDeadlineDate(localDate);

            goal.setUser(user);
            goal.setRegisteredDate(LocalDateTime.now());
            goalStore.addGoal(goal);
        } else {
            throw new InvalidGoalException();
        }
    }

    Set<Tag> parseStringToTags(String value) {
        List<String> tagList = TagParser.generateTagsList(value);
        Set<Tag> tagSet = new HashSet<>();
        for (String item : tagList) {
            Tag tag;
            tag = goalStore.addTag(item);
            if (tag != null) {
                tagSet.add(tag);
            }
        }
        return tagSet;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{id}/comments")
    public List<CommentDto> returnAllCommentsForGoalById(@PathParam("id") Long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            List<Comment> comments = goalStore.getCommentsForGoal(goal.get());
            return comments.stream()
                    .map(DtoConventer::convertCommentToCommentDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
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
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/taglist")
    public List<TagDto> returnAllTags() {
        return goalStore.getAllTagList();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-user")
    public List<UserSearchDto> getSearchedUser(String searchedUserName) {
        return userStore.getUserByUsername(searchedUserName)
                .stream()
                .map(DtoConventer::convertUserToUserSearchDto)
                .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-user")
    public List<UserSearchDto> getSearchParameters(JsonObject searchDto) {
        List<UserSearchDto> userDtoList = new ArrayList<>();
        for (Map.Entry<String, JsonValue> pair : searchDto.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr.equals("usersearch")) {
                userDtoList = userStore.getUserByUsername(value)
                        .stream()
                        .map(DtoConventer::convertUserToUserSearchDto)
                        .collect(Collectors.toList());
            }
        }
        return userDtoList;
    }
}
