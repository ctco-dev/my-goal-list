package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.Goal;
import lv.ctco.javaschool.goal.entity.GoalDto;
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
    public List<TagDto> drawRecordTable() {
        List<TagDto> dto = new ArrayList<>();
        int tagCount=new Random().nextInt(5)+3;
        for(int i=0; i<tagCount; i++) {
            dto.add(new TagDto(generateRandomWord()));
        }
        return dto;
//        return goalStore.getTagList();
//        List<TagDto> dto = goalStore.getTagList();
//        return new Gson().toJson(dto);
    }

    private static String generateRandomWord() {
        String randomStrings = new String("");
        Random random = new Random();
        char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        randomStrings = new String(word);
        return randomStrings;
    }


}
