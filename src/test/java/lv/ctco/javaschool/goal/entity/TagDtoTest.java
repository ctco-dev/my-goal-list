package lv.ctco.javaschool.goal.entity;

import org.junit.jupiter.api.DisplayName;
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
        long newCnt = 123456L;
        TagDto dto = new TagDto();
        dto.setCnt(newCnt);
        assertEquals(newCnt, dto.getCnt());
    }
}