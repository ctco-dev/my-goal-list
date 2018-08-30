package lv.ctco.javaschool.goal.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class TagParserTest {

    @Test
    @DisplayName("Test generateTagsList: Checks work of tag list generation from goal message")
    void testGenerationOfTagsList() {
        String testLine1 = "I will become a programmer this year!";
        String expResult1 = "programmer";
        String testLine2 = "I WILL BECOME A PROGRAMMER THIS YEAR!";
        String expResult2 = "PROGRAMMER";
        String testLine3 = "I WILL be two years old!";
        String expResult3 = "";
        String testLine4 = "I will start to learn Java!";
        String expResult4 = "learn Java";

        assertThat(String.join(" ", TagParser.generateTagsList(testLine1)), is(expResult1));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine2)), is(expResult2));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine3)), is(expResult3));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine4)), is(expResult4));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine1)), not(expResult2));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine3)), not(expResult4));
    }
}