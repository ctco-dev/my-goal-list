package lv.ctco.javaschool.goal.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TagParserTest {
    @Test
    @DisplayName("Test parseStringToTags(): returns parsed string as list of tags")
    void parseStringToTagsTest() {
        TagParser tagParser = new TagParser();
        String tagString = "asd|qtw|zsx";
        assertThat(tagParser.parseStringToTags(tagString).get(0).getTagMessage(), is("asd"));
        assertThat(tagParser.parseStringToTags(tagString).get(1).getTagMessage(), is("qtw"));
        assertThat(tagParser.parseStringToTags(tagString).get(2).getTagMessage(), is("zsx"));
    }

    @Test
    @DisplayName("Test parseStringToTags(): returns empty List")
    void parseStringToTagsTestReturnsEmptyList() {
        TagParser tagParser = new TagParser();
        String tagString = "";
        assertThat(tagParser.parseStringToTags(tagString).isEmpty(), is(true));
    }
}