package lv.ctco.javaschool.goal.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Test
    @DisplayName("Test isEqualSets(Set<?> set1, Set<?> set2): Checks that sets compared correctly")
    void testIfSetsAreEquals() {
        Set<String> set1 = new HashSet<>();
        set1.add("fdfsdfsdf");
        set1.add("dfsdf");
        Set<String> set2 = new HashSet<>();
        set2.add("2222 222");
        Set<String> setEmpty = new HashSet<>();

        assertThat( TagParser.isEqualSets(set1, set1), is(true));
        assertThat( TagParser.isEqualSets(set2, set2), is(true));
        assertThat( TagParser.isEqualSets(setEmpty, setEmpty), is(true));
        assertThat( TagParser.isEqualSets(null, null), is(true));
        assertThat( TagParser.isEqualSets(set1, set2), is(false));
        assertThat( TagParser.isEqualSets(set1, setEmpty), is(false));
        assertThat( TagParser.isEqualSets(set1, null), is(false));
    }

    @Test
    @DisplayName("Test isEqualLists(List<?> list1, List<?> list2): Checks that sets compared correctly")
    void testIflistsAreEquals() {
        List<String> list1 = new ArrayList<>();
        list1.add("fdfsdfsdf");
        list1.add("dfsdf");
        List<String> list2 = new ArrayList<>();
        list2.add("2222 222");
        List<String> listEmpty = new ArrayList<>();

        assertThat( TagParser.isEqualLists(list1, list1), is(true));
        assertThat( TagParser.isEqualLists(list2, list2), is(true));
        assertThat( TagParser.isEqualLists(listEmpty, listEmpty), is(true));
        assertThat( TagParser.isEqualLists(null, null), is(true));
        assertThat( TagParser.isEqualLists(list1, list2), is(false));
        assertThat( TagParser.isEqualLists(list1, listEmpty), is(false));
        assertThat( TagParser.isEqualLists(list1, null), is(false));
    }
}