package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.goal.entity.domain.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagParser {
    public List<Tag> parseStringToTags(String value) {
        String[] tagList = value.split("\\|");
        List<Tag> tags = new ArrayList<>();
        for (String item : tagList) {
            if (item != null && !Objects.equals(item, "")) {
                Tag tag = new Tag(item);
                tags.add(tag);
            }
        }
        return tags;
    }
}
