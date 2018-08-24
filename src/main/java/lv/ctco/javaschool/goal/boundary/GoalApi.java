package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.Goal;
import lv.ctco.javaschool.goal.entity.GoalDto;
import lv.ctco.javaschool.goal.entity.Tag;
import lv.ctco.javaschool.goal.entity.TagDto;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/goal")
@Stateless
public class GoalApi {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;

    //   may be deleted
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public void startPage() {
//        User currentUser = userStore.getCurrentUser();
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

    private GoalDto convertToDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setUsername(goal.getUser().getUsername());
        dto.setGoalMessage(goal.getGoalMessage());
        dto.setDeadlineDate(convertDate(goal.getDeadlineDate()));
        dto.setRegisteredDate(convertDateTime(goal.getRegisteredDate()));
        return dto;
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

        ///--- TODO: temporaty for test purpusses!!! ----------------
        Set<Tag> tagSet = new HashSet<>();

        Tag tag = goalStore.addTag("tag1");
        tagSet.add(tag);

        tag = goalStore.addTag("tag2");
        tagSet.add(tag);

        tag = goalStore.addTag("tag3");
        tagSet.add(tag);

        tag = goalStore.addTag("tAg1");
        tagSet.add(tag);

        User user = userStore.getCurrentUser();
        Goal goal = new Goal();
        goal.setUser(user);
        goal.setGoalMessage("MyGoal!");
        goal.setTags(tagSet);
        goalStore.addGoal(goal);
        ///--- endTODO ----------------------------------------------

        Set<Tag> tagListFromDB = goalStore.getTagList();
        if (tagSet.size() != 0) {
            return tagSet.stream()
                    .map(this::convertToTagDto)
                    .sorted(Comparator.comparing(t -> t.getTagMessage().toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
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
        List<String> tagList = new ArrayList<>(Arrays.asList(clean.split(" ")));
        return tagList;
    }
}
