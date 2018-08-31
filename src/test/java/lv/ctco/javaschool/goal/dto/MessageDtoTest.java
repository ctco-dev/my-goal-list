package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.entity.dto.MessageDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MessageDtoTest {
    @Test
    void testGetAndSetMessage() {
        String newMsg = "new comment";
        MessageDto dto = new MessageDto();
        dto.setMessage(newMsg);
        assertThat(dto.getMessage(), is(newMsg));
    }
}