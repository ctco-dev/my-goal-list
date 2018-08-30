package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.goal.entity.domain.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TagTest {
    @Test
    void testGetAndSetId() {
        Long newId = 123456789L;
        Tag tag = new Tag();
        tag.setId(newId);
        assertThat(tag.getId(), is(newId));
    }

    @Test
    void testGetAndSetTagMessage() {
        String newMsg = "test tag";
        Tag tag = new Tag();
        tag.setTagMessage(newMsg);
        assertThat(tag.getTagMessage(), is(newMsg));
    }
}