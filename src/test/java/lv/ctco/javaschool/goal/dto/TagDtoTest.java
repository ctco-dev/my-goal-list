package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.entity.dto.TagDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagDtoTest {
    @Test
    void testGetAndSetTagMessage() {
        String newMsg = "test tag";
        TagDto dto = new TagDto();
        dto.setTagMessage(newMsg);
        assertEquals(newMsg, dto.getTagMessage());
    }

    @Test
    void testGetAndSetCnt() {
        int newCnt = 345;
        TagDto dto = new TagDto();
        dto.setCnt(newCnt);
        assertEquals(newCnt, dto.getCnt());
    }
}