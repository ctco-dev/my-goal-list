package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.*;

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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Path("/goal")
@Stateless
public class GoalApi {
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;


    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-user")
    public List<UserLoginDto> getSearchParameters(JsonObject searchDto) {
        List<UserLoginDto> userDtoList= new ArrayList<>();
        for (Map.Entry<String, JsonValue> pair : searchDto.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr.equals("usersearch")) {
                 List<User> userList = userStore.getUserByUsername(value);
                if (userList.size() != 0) {
                    userDtoList = userList.stream()
                            .map(userStore::convertToDto)
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
    @Path("/mygoals")
    public List<GoalDto> getMyGoals() {
        User currentUser = userStore.getCurrentUser();
        List<Goal> goalsList = goalStore.getGoalsListFor(currentUser);
        if (goalsList.size() != 0) {
            return goalsList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/mygoals/{id}")
    public GoalDto getGoalById(@PathParam("id") long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            Goal g = goal.get();
            return convertToDto(g);
        } else {
            return new GoalDto();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{id}/comments")
    public List<CommentDto> getCommentsGoalById(@PathParam("id") long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        List<CommentDto> commentDtos = new ArrayList<>();
        if (goal.isPresent()) {
            List<Comment> comments = goalStore.getCommentsForGoal(goal.get());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
            for (Comment c : comments) {
                CommentDto commentDto = new CommentDto(c.getUser().getUsername(), c.getRegisteredDate().format(formatter), c.getCommentMessage());
                commentDtos.add(commentDto);
            }
        }
        return commentDtos;
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{id}/comments")
    public void setCommentsGoalById(@PathParam("id") long goalId, MessageDto msg) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            Comment comment = new Comment();
            comment.setUser(userStore.getCurrentUser());
            comment.setGoal(goal.get());
            comment.setRegisteredDate(LocalDateTime.now());
            comment.setCommentMessage(msg.getMessage());
            goalStore.addComment(comment);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/newgoal")
    public void createNewGoal(JsonObject goalDto) {
        User user = userStore.getCurrentUser();
        Goal goal = new Goal();
        for (Map.Entry<String, JsonValue> pair : goalDto.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            goal = setFieldsToGoal(goal, adr, value);
        }
        goal.setUser(user);
        goal.setRegisteredDate(LocalDateTime.now());
        goalStore.addGoal(goal);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/taglist")
    public List<TagDto> returnAllTags() {
        return goalStore.getAllTagList();
    }

    private GoalDto convertToDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setUsername(goal.getUser().getUsername());
        dto.setGoalMessage(goal.getGoalMessage());
        dto.setDeadlineDate(convertDate(goal.getDeadlineDate()));
        dto.setRegisteredDate(convertDateTime(goal.getRegisteredDate()));
        dto.setDaysLeft(countDaysLeft(goal.getDeadlineDate()));
        dto.setId(goal.getId());
        return dto;
    }

    private long countDaysLeft(LocalDate deadlineDate) {
        LocalDate localDate = LocalDate.now();
        return DAYS.between(localDate, deadlineDate);
    }

    private String convertDate (LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    private String convertDateTime (LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
        return date.format(formatter);
    }

    private TagDto convertToTagDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.setTagMessage(tag.getTagMessage());
        return dto;
    }



    private Goal setFieldsToGoal(Goal goal, String adr, String value) throws IllegalArgumentException {
        switch (adr){
            case ("goal"):
                goal.setGoalMessage(value);
                goal.setTags(parseStringToTags(value));
                break;
            case ("deadline"):
                DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate localDate = LocalDate.parse(value, formatterD);
                goal.setDeadlineDate(localDate);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return goal;
    }

    private Set<Tag> parseStringToTags(String value) {
        List<String> tagList = generateTagsList(value);
        Set<Tag> tagSet = new HashSet<>();

        for (String item : tagList) {
            Tag tag;
            tag = goalStore.addTag(item);
            tagSet.add(tag);
        }
        return tagSet;
    }

    private String[] patternList = new String[] {
            "change|become|i|language|field|apply|app|application|start|end|more|this|that",
            "maybe|year|years|one|two|three|four|five|six|seven|eight|nine|ten|from|i|a|and",
            "are|if|of|off|on|by|next|last|use|using|used|do|doing|what|determined|am|want",
            "an|wanted|goal|goals|achieve|me|my|in|out|above|wish|will|was|is|not|new|old",
            "get|got|going|to|for|have|has|the|can|will|be|about"
    };

    List<String> generateTagsList(String goal){
        String noSymbols = goal.replaceAll("[$,.:;#@!?&*()1234567890]", "");
        for( String s:patternList){
            Pattern stopWords = Pattern.compile("\\b(?:"+s+")\\b\\s*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = stopWords.matcher(noSymbols);
            noSymbols = matcher.replaceAll("");
        }
        return Arrays.asList(noSymbols.split(" "));
    }
}
