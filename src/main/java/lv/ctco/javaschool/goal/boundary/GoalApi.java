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
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import lv.ctco.javaschool.goal.entity.dto.MessageDto;
import lv.ctco.javaschool.goal.entity.dto.TagDto;
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;
import lv.ctco.javaschool.goal.entity.exception.ValidationException;

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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/goal")
@Stateless
public class GoalApi {
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;

    @Inject
    private TagParser tagParser;

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/mygoals")
    public List<GoalDto> getMyGoals() {
        User currentUser = userStore.getCurrentUser();
        List<Goal> goalsList = goalStore.getGoalsListFor(currentUser);
        return goalsList.stream()
                .map(DtoConverter::convertGoalToGoalDto)
                .collect(Collectors.toList());
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
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
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/newgoal")
    public void createNewGoal(GoalFormDto goalDto) {
        User user = userStore.getCurrentUser();
        Goal goal = new Goal();
        if (!goalDto.getGoalMessage().isEmpty() && !goalDto.getDeadline().isEmpty()) {
            goal.setGoalMessage(goalDto.getGoalMessage());
            goal.setTags(tagParser.parseStringToTagsAndPersist(goalDto.getTags()));

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
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{id}/comments")
    public List<CommentDto> returnAllCommentsForGoalById(@PathParam("id") Long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            List<Comment> comments = goalStore.getCommentsForGoal(goal.get());
            return comments.stream()
                    .map(DtoConverter::convertCommentToCommentDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<CommentDto>();
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
    @Path("/tags")
    public List<TagDto> returnAllTags() {
        List<Tag> tagList = goalStore.getAllTagList();
        return tagList.stream()
                .map(DtoConverter::convertTagToTagDto)
                .collect(Collectors.toList());
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/findgoals")
    public List<UserSearchDto> getSimilarUserList() {
        User currentUser = userStore.getCurrentUser();
        List<UserSearchDto> userDtoList = new ArrayList<>();
        List<Goal> goalsList = goalStore.getGoalsListFor(currentUser);
        List<Tag> userTagListTemp = new ArrayList<>();
        for (Goal item : goalsList) {
            for (int i = 0; i < item.getTags().size(); i++) {
                userTagListTemp.add(goalStore.getAllTagsForGoal(item).get(i));
            }
        }
        //List of all current users goal tags without duplicates.
        List<Tag> userTagList = new ArrayList<>(new HashSet<>(userTagListTemp));

        return userDtoList;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/tag/{tag}")
    public List<GoalDto> getGoalListByTag(@PathParam("tag") String tag) {
       List<GoalDto> goalDtos = new ArrayList<>();
        Optional<Tag> optionalTag = goalStore.getTagByMessage(tag);
        if (optionalTag.isPresent()){
            return goalStore.getGoalsByTag(optionalTag.get())
                    .stream()
                    .sorted(Comparator.comparing(Goal::getRegisteredDate))
                    .map(DtoConverter::convertGoalToGoalDto)
                    .collect(Collectors.toList());
        } else {
            throw new ValidationException("There is no such tag");
        }
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
                List<User> userList = userStore.getUserByUsername(value);
                if (userList.size() != 0) {
                    userDtoList = userList.stream()
                            .map(userStore::convertToSearchDto)
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
    @Path("/taglist")
    public List<TagDto> returnTagList() {
        return goalStore.getTagList();
    }

}
