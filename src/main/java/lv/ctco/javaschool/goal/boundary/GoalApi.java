package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.TagDto;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("/goal")
@Stateless
public class GoalApi {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;

    /*      for start.jsp
     * Add player to existing game or creates a new one     */
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public void startPage() {
//        User currentUser = userStore.getCurrentUser();
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
