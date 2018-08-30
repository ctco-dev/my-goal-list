package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.goal.entity.dto.TagDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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