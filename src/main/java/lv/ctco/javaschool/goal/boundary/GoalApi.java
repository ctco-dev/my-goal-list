package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.boundary.AuthenticationApi;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public void startPage() {
        /// place for methods on page reload
        /// currently - none
    }
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-user")
    public void getSearchParameters(JsonObject searchDto) {
        for(Map.Entry<String,JsonValue> pair : searchDto.entrySet()){
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr.equals("usersearch")) {
                getSearchedUser(value);
            }
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/search-user")
    public List<UserLoginDto> getSearchedUser(String searchedUserName) {
        List<User> userList = userStore.getUserByUsername(searchedUserName);
        if (userList.size() != 0) {
            return userList.stream()
                    .map(userStore::convertToDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
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
    @Path("/comments/{id}")
    public List<Comment> getCommentsGoalById(@PathParam("id") long goalId) {
        Optional<Goal> goal = goalStore.getGoalById(goalId);
        if (goal.isPresent()) {
            return goalStore.getCommentsForGoal(goalId);
        } else {
            return new ArrayList<>();
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

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/taglist")
    public List<TagDto> returnAllTags() {
        return goalStore.getAllTagList();
    }

    private TagDto convertToTagDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.setTagMessage(tag.getTagMessage());
        return dto;
    }

    private List<String> generateTagsList(String goal){
        Pattern stopWords = Pattern.compile("\\b(?:change|become|language|field|apply|app|application|start|end|more|this|that|maybe|year|one|two|three|four|five|six|seven|eight|nine|ten|from|i|a|and|about|an|are|if|of|off|on|by|next|last|use|using|used|do|doing|what|determined|am|want|wanted|goal|goals|achieve|me|my|in|out|above|wish|will|was|is|not|new|old|get|got|going|to|for|have|has|the|can)\\b\\s*", Pattern.CASE_INSENSITIVE);
        String noSymbols = goal.replaceAll("[$,.:;_#@!?&*()+1234567890-]", "");
        Matcher matcher = stopWords.matcher(noSymbols);
        String clean = matcher.replaceAll("");
        return Arrays.asList(clean.split(" "));
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/newgoal")
    public void createNewGoal(JsonObject goalDto) {
        User user = userStore.getCurrentUser();
        Goal goal = new Goal();
        for(Map.Entry<String,JsonValue> pair : goalDto.entrySet()){
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            goal = setFieldsToGoal(goal, adr, value);
        }
        goal.setUser(user);
        goal.setRegisteredDate(LocalDateTime.now());
        goalStore.addGoal(goal);
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
}
