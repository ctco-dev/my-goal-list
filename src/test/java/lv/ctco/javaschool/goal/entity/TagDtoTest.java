package lv.ctco.javaschool.goal.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagDtoTest {
    @Test
    @DisplayName("TagMessages should be equal")
    void getAndSetTagMessage() {
        String newMsg="test tag";
        TagDto dto = new TagDto();
        dto.setTagMessage(newMsg);
        assertEquals(newMsg, dto.getTagMessage());
    }

}