package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    @Test
    @DisplayName("Ids should be equal")
    void getAndSetId() {
        Long l=123456789L;
        Tag tag = new Tag();
        tag.setId(l);
        assertEquals(l, tag.getId());
    }

    @Test
    @DisplayName("TagMessages should be equal")
    void getAndSetTagMessage() {
        String newMsg="test tag";
        Tag tag = new Tag();
        tag.setTagMessage(newMsg);
        assertEquals(newMsg, tag.getTagMessage());
    }

}