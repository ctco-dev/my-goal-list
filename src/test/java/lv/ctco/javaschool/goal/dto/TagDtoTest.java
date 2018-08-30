package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.entity.dto.TagDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TagDtoTest {
    @Test
    void testGetAndSetTagMessage() {
        String newMsg = "test tag";
        TagDto dto = new TagDto();
        dto.setTagMessage(newMsg);
        assertThat(dto.getTagMessage(), is(newMsg));
    }

    @Test
    void testGetAndSetCnt() {
        int newCnt = 345;
        TagDto dto = new TagDto();
        dto.setCnt(newCnt);
        assertThat(dto.getCnt(), is(newCnt));
    }
}