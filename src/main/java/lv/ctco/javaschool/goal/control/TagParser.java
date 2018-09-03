package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.goal.entity.domain.Tag;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class TagParser {
    @Inject
    private GoalStore goalStore;

    public Set<Tag> parseStringToTagsAndPersist(String value) {
        String[] tagList = value.split("\\|");
        Set<Tag> tagSet = new HashSet<>();
        goalStore.checkIfTagExistsOrPersist(tagList, tagSet);
        return tagSet;
    }


}
