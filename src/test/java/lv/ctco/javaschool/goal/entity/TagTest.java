package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.goal.entity.domain.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagTest {
    @Test
    void testGetAndSetId() {
        Long l = 123456789L;
        Tag tag = new Tag();
        tag.setId(l);
        assertEquals(l, tag.getId());
    }

    @Test
    void testGetAndSetTagMessage() {
        String newMsg = "test tag";
        Tag tag = new Tag();
        tag.setTagMessage(newMsg);
        assertEquals(newMsg, tag.getTagMessage());
    }
}